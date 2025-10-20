package edu.pe.vallegrande.AuthenticationService.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Filtro de Rate Limiting para proteger contra ataques DDoS
 * Limita el número de peticiones por IP (100 peticiones por minuto)
 */
@Slf4j
@Component
public class RateLimitFilter implements WebFilter {

    private final Map<String, RateLimitInfo> rateLimitMap = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 100;
    private static final Duration WINDOW_DURATION = Duration.ofMinutes(1);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String ip = getClientIP(exchange);

        RateLimitInfo info = rateLimitMap.computeIfAbsent(ip, k -> new RateLimitInfo());

        synchronized (info) {
            Instant now = Instant.now();

            // Limpiar ventana si ha pasado el tiempo
            if (Duration.between(info.windowStart, now).compareTo(WINDOW_DURATION) > 0) {
                info.requestCount = 0;
                info.windowStart = now;
            }

            // Verificar límite
            if (info.requestCount >= MAX_REQUESTS) {
                log.warn("Rate limit excedido para IP: {} ({} peticiones)", ip, info.requestCount);
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            }

            info.requestCount++;
        }

        return chain.filter(exchange);
    }

    private String getClientIP(ServerWebExchange exchange) {
        String ip = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = exchange.getRequest().getRemoteAddress() != null
                    ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                    : "unknown";
        }
        return ip;
    }

    private static class RateLimitInfo {
        int requestCount = 0;
        Instant windowStart = Instant.now();
    }
}

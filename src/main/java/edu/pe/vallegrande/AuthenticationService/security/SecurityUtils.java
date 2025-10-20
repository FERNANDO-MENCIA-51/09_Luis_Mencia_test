package edu.pe.vallegrande.AuthenticationService.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import reactor.core.publisher.Mono;

/**
 * Utilidades para operaciones de seguridad
 */
public class SecurityUtils {

    /**
     * Obtiene el username del usuario autenticado actual
     */
    public static Mono<String> getCurrentUsername() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
    }

    /**
     * Obtiene el ID del usuario autenticado actual
     * Nota: Requiere que el ID esté almacenado en el contexto de seguridad
     */
    public static Mono<UUID> getCurrentUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .flatMap(auth -> {
                    // extrayendo el userId del token JWT
                    return Mono.empty();
                });
    }

    /**
     * Verifica si el usuario actual tiene un rol específico
     */
    public static Mono<Boolean> hasRole(String role) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(auth -> auth.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role)))
                .defaultIfEmpty(false);
    }

    /**
     * Verifica si el usuario actual tiene alguno de los roles especificados
     */
    public static Mono<Boolean> hasAnyRole(String... roles) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(auth -> {
                    for (String role : roles) {
                        if (auth.getAuthorities().stream()
                                .anyMatch(ga -> ga.getAuthority().equals("ROLE_" + role))) {
                            return true;
                        }
                    }
                    return false;
                })
                .defaultIfEmpty(false);
    }
}

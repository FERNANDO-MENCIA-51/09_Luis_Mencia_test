package edu.pe.vallegrande.AuthenticationService.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Rate Limiting para proteger contra ataques
 * Limita el número de peticiones por IP (100 peticiones por minuto)
 * 
 * Implementación simple sin dependencias externas
 * En producción se puede usar Redis para distribuir entre instancias
 */
@Configuration
public class RateLimitConfig {
    // Configuración manejada por RateLimitFilter
}

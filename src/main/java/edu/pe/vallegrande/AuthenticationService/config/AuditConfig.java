package edu.pe.vallegrande.AuthenticationService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

/**
 * Configuración de auditoría para R2DBC
 * Habilita el seguimiento automático de creación y modificación de entidades
 */
@Configuration
@EnableR2dbcAuditing
public class AuditConfig {
    // La anotación @EnableR2dbcAuditing habilita automáticamente:
    // - @CreatedDate
    // - @LastModifiedDate
    // - @CreatedBy
    // - @LastModifiedBy
}

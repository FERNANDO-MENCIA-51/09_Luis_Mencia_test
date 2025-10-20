package edu.pe.vallegrande.AuthenticationService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad Permission para los permisos del sistema
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("permissions")
public class Permission {
    
    @Id
    private UUID id;
    
    @Column("module")
    private String module;
    
    @Column("action")
    private String action;
    
    @Column("resource")
    private String resource;
    
    @Column("description")
    private String description;
    
    @Column("created_by")
    private UUID createdBy;
    
    @Column("created_at")
    private LocalDateTime createdAt;
    
    @Column("status")
    private Boolean status;
}
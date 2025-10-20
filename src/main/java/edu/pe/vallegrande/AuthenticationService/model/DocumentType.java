package edu.pe.vallegrande.AuthenticationService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidad DocumentType para los tipos de documento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("document_types")
public class DocumentType {
    
    @Id
    @Column("id")
    private Integer id;
    
    @Column("code")
    private String code;
    
    @Column("description")
    private String description;
    
    @Column("length")
    private Integer length;
    
    @Column("active")
    private Boolean active;
}

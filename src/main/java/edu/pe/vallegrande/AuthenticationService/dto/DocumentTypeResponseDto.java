package edu.pe.vallegrande.AuthenticationService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para las respuestas de tipos de documento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeResponseDto {
    
    private Integer id;
    private String code;
    private String description;
    private Integer length;
    private Boolean active;
}

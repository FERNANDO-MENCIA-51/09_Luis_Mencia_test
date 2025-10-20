package edu.pe.vallegrande.AuthenticationService.service;

import edu.pe.vallegrande.AuthenticationService.dto.DocumentTypeResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio para la gestión de tipos de documento
 */
public interface DocumentTypeService {
    
    /**
     * Obtener todos los tipos de documento
     */
    Flux<DocumentTypeResponseDto> getAllDocumentTypes();
    
    /**
     * Obtener tipo de documento por ID
     */
    Mono<DocumentTypeResponseDto> getDocumentTypeById(Integer id);
}

package edu.pe.vallegrande.AuthenticationService.controller;

import edu.pe.vallegrande.AuthenticationService.dto.DocumentTypeResponseDto;
import edu.pe.vallegrande.AuthenticationService.service.DocumentTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para la gestión de tipos de documento
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/document-types")
@RequiredArgsConstructor
@Tag(name = "Document Types", description = "API para la gestión de tipos de documento")
public class DocumentTypeController {
    
    private final DocumentTypeService documentTypeService;
    
    /**
     * Obtener todos los tipos de documento
     */
    @Operation(summary = "Obtener todos los tipos de documento", description = "Recupera la lista completa de tipos de documento disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de documento obtenida exitosamente")
    })
    @GetMapping
    public Flux<DocumentTypeResponseDto> getAllDocumentTypes() {
        log.info("Solicitud para obtener todos los tipos de documento");
        return documentTypeService.getAllDocumentTypes();
    }
    
    /**
     * Obtener tipo de documento por ID
     */
    @Operation(summary = "Obtener tipo de documento por ID", description = "Recupera un tipo de documento específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de documento encontrado"),
            @ApiResponse(responseCode = "404", description = "Tipo de documento no encontrado")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DocumentTypeResponseDto>> getDocumentTypeById(
            @Parameter(description = "ID del tipo de documento", required = true) @PathVariable Integer id) {
        log.info("Solicitud para obtener tipo de documento con ID: {}", id);
        return documentTypeService.getDocumentTypeById(id)
                .map(ResponseEntity::ok);
    }
}

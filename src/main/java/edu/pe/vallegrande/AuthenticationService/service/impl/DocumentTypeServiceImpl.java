package edu.pe.vallegrande.AuthenticationService.service.impl;

import edu.pe.vallegrande.AuthenticationService.dto.DocumentTypeResponseDto;
import edu.pe.vallegrande.AuthenticationService.exception.ResourceNotFoundException;
import edu.pe.vallegrande.AuthenticationService.model.DocumentType;
import edu.pe.vallegrande.AuthenticationService.repository.DocumentTypeRepository;
import edu.pe.vallegrande.AuthenticationService.service.DocumentTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del servicio para la gestión de tipos de documento
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    @Override
    public Flux<DocumentTypeResponseDto> getAllDocumentTypes() {
        log.info("Obteniendo todos los tipos de documento");
        return documentTypeRepository.findAll()
                .map(this::mapToResponseDto)
                .doOnComplete(() -> log.info("Tipos de documento obtenidos exitosamente"));
    }

    @Override
    public Mono<DocumentTypeResponseDto> getDocumentTypeById(Integer id) {
        log.info("Obteniendo tipo de documento por ID: {}", id);
        return documentTypeRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new ResourceNotFoundException("Tipo de documento no encontrado con ID: " + id)))
                .map(this::mapToResponseDto)
                .doOnSuccess(docType -> log.info("Tipo de documento encontrado: {}", docType.getCode()));
    }

    /**
     * Mapea una entidad DocumentType a DocumentTypeResponseDto
     */
    private DocumentTypeResponseDto mapToResponseDto(DocumentType documentType) {
        return DocumentTypeResponseDto.builder()
                .id(documentType.getId())
                .code(documentType.getCode())
                .description(documentType.getDescription())
                .length(documentType.getLength())
                .active(documentType.getActive())
                .build();
    }
}

package edu.pe.vallegrande.AuthenticationService.repository;

import edu.pe.vallegrande.AuthenticationService.model.DocumentType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repositorio para la gestión de tipos de documento
 */
@Repository
public interface DocumentTypeRepository extends R2dbcRepository<DocumentType, Integer> {
    
    /**
     * Buscar tipo de documento por código
     */
    Mono<DocumentType> findByCode(String code);
    
    /**
     * Buscar tipos de documento activos
     */
    Flux<DocumentType> findByActiveTrue();
    
    /**
     * Verificar si existe un tipo de documento por código
     */
    Mono<Boolean> existsByCode(String code);
}

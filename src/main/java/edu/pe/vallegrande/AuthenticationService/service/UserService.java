package edu.pe.vallegrande.AuthenticationService.service;

import edu.pe.vallegrande.AuthenticationService.dto.UserRequestDto;
import edu.pe.vallegrande.AuthenticationService.dto.UserResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Interfaz del servicio para la gestión de usuarios
 */
public interface UserService {
    
    /**
     * Crear un nuevo usuario
     */
    Mono<UserResponseDto> createUser(UserRequestDto userRequestDto);
    
    /**
     * Obtener todos los usuarios con filtros opcionales
     */
    Flux<UserResponseDto> getAllUsers(String status, UUID areaId, UUID positionId, UUID managerId);
    
    /**
     * Obtener usuario por ID
     */
    Mono<UserResponseDto> getUserById(UUID id);
    
    /**
     * Obtener usuario por username
     */
    Mono<UserResponseDto> getUserByUsername(String username);
    
    /**
     * Actualizar un usuario
     */
    Mono<UserResponseDto> updateUser(UUID id, UserRequestDto userRequestDto);
    
    /**
     * Eliminar un usuario (cambio de status a INACTIVE)
     */
    Mono<Void> deleteUser(UUID id, UUID updatedBy);
    
    /**
     * Restaurar un usuario eliminado
     */
    Mono<UserResponseDto> restoreUser(UUID id, UUID updatedBy);
    
    /**
     * Suspender usuario
     */
    Mono<UserResponseDto> suspendUser(UUID id, UUID updatedBy);
    
    /**
     * Bloquear usuario temporalmente
     */
    Mono<UserResponseDto> blockUser(UUID id);
    
    /**
     * Desbloquear usuario
     */
    Mono<UserResponseDto> unblockUser(UUID id);
    
    /**
     * Verificar si existe un usuario por username
     */
    Mono<Boolean> existsByUsername(String username);
    
    /**
     * Actualizar último login
     */
    Mono<Void> updateLastLogin(UUID id);
    
    /**
     * Incrementar intentos de login fallidos
     */
    Mono<Void> incrementLoginAttempts(UUID id);
}
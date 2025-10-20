package edu.pe.vallegrande.AuthenticationService.controller;

import edu.pe.vallegrande.AuthenticationService.dto.UserRequestDto;
import edu.pe.vallegrande.AuthenticationService.dto.UserResponseDto;
import edu.pe.vallegrande.AuthenticationService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Controlador REST para la gestión de usuarios
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API para la gestión de usuarios del sistema")
public class UserController {
    
    private final UserService userService;
    
    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "Ya existe un usuario con ese username")
    })
    @PostMapping
    public Mono<ResponseEntity<UserResponseDto>> createUser(@jakarta.validation.Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Solicitud para crear usuario: {}", userRequestDto.getUsername());
        return userService.createUser(userRequestDto)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user));
    }
    
    @Operation(summary = "Obtener todos los usuarios con filtros opcionales", 
               description = "Permite filtrar por status (ACTIVE/INACTIVE/SUSPENDED), área, posición o manager")
    @GetMapping
    public Flux<UserResponseDto> getAllUsers(
            @Parameter(description = "Filtrar por status (ACTIVE, INACTIVE, SUSPENDED)") @RequestParam(required = false) String status,
            @Parameter(description = "Filtrar por área") @RequestParam(required = false) UUID areaId,
            @Parameter(description = "Filtrar por posición") @RequestParam(required = false) UUID positionId,
            @Parameter(description = "Filtrar por manager") @RequestParam(required = false) UUID managerId,
            @Parameter(description = "Número de página (opcional)") @RequestParam(required = false) Integer page,
            @Parameter(description = "Tamaño de página (opcional)") @RequestParam(required = false) Integer size) {
        log.info("Solicitud para obtener usuarios - status: {}, area: {}, position: {}, manager: {}", 
                status, areaId, positionId, managerId);
        
        Flux<UserResponseDto> users = userService.getAllUsers(status, areaId, positionId, managerId);
        
        // Aplicar paginación si se especifica
        if (page != null && size != null) {
            users = users.skip((long) page * size).take(size);
        }
        
        return users;
    }
    
    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDto>> getUserById(
            @Parameter(description = "ID del usuario") @PathVariable UUID id) {
        log.info("Solicitud para obtener usuario por ID: {}", id);
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user));
    }
    
    @Operation(summary = "Obtener usuario por username")
    @GetMapping("/username/{username}")
    public Mono<ResponseEntity<UserResponseDto>> getUserByUsername(
            @Parameter(description = "Username del usuario") @PathVariable String username) {
        log.info("Solicitud para obtener usuario por username: {}", username);
        return userService.getUserByUsername(username)
                .map(user -> ResponseEntity.ok(user));
    }
    
    @Operation(summary = "Actualizar un usuario")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponseDto>> updateUser(
            @Parameter(description = "ID del usuario") @PathVariable UUID id,
            @jakarta.validation.Valid @RequestBody UserRequestDto userRequestDto) {
        log.info("Solicitud para actualizar usuario con ID: {}", id);
        return userService.updateUser(id, userRequestDto)
                .map(user -> ResponseEntity.ok(user));
    }
    
    @Operation(summary = "Eliminar un usuario (eliminación lógica)")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(
            @Parameter(description = "ID del usuario") @PathVariable UUID id,
            @Parameter(description = "ID del usuario que hace la eliminación") @RequestParam UUID updatedBy) {
        log.info("Solicitud para eliminar usuario con ID: {}", id);
        return userService.deleteUser(id, updatedBy)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
    
    @Operation(summary = "Restaurar un usuario eliminado")
    @PatchMapping("/{id}/restore")
    public Mono<ResponseEntity<UserResponseDto>> restoreUser(
            @Parameter(description = "ID del usuario") @PathVariable UUID id,
            @Parameter(description = "ID del usuario que hace la restauración") @RequestParam UUID updatedBy) {
        log.info("Solicitud para restaurar usuario con ID: {}", id);
        return userService.restoreUser(id, updatedBy)
                .map(user -> ResponseEntity.ok(user));
    }
    
    @Operation(summary = "Suspender un usuario")
    @PatchMapping("/{id}/suspend")
    public Mono<ResponseEntity<UserResponseDto>> suspendUser(
            @Parameter(description = "ID del usuario") @PathVariable UUID id,
            @Parameter(description = "ID del usuario que hace la suspensión") @RequestParam UUID updatedBy) {
        log.info("Solicitud para suspender usuario con ID: {}", id);
        return userService.suspendUser(id, updatedBy)
                .map(user -> ResponseEntity.ok(user));
    }
    
    @Operation(summary = "Bloquear usuario temporalmente")
    @PatchMapping("/{id}/block")
    public Mono<ResponseEntity<UserResponseDto>> blockUser(
            @Parameter(description = "ID del usuario") @PathVariable UUID id) {
        log.info("Solicitud para bloquear usuario con ID: {}", id);
        return userService.blockUser(id)
                .map(user -> ResponseEntity.ok(user));
    }
    
    @Operation(summary = "Desbloquear usuario")
    @PatchMapping("/{id}/unblock")
    public Mono<ResponseEntity<UserResponseDto>> unblockUser(
            @Parameter(description = "ID del usuario") @PathVariable UUID id) {
        log.info("Solicitud para desbloquear usuario con ID: {}", id);
        return userService.unblockUser(id)
                .map(user -> ResponseEntity.ok(user));
    }
    
    @Operation(summary = "Verificar si existe un usuario por username")
    @GetMapping("/exists/{username}")
    public Mono<ResponseEntity<Boolean>> existsByUsername(
            @Parameter(description = "Username del usuario") @PathVariable String username) {
        log.info("Verificando existencia de usuario con username: {}", username);
        return userService.existsByUsername(username)
                .map(exists -> ResponseEntity.ok(exists));
    }
}
package edu.pe.vallegrande.AuthenticationService.service;

import edu.pe.vallegrande.AuthenticationService.dto.AssignRoleRequestDto;
import edu.pe.vallegrande.AuthenticationService.dto.RolePermissionAssignmentDto;
import edu.pe.vallegrande.AuthenticationService.dto.UserRoleAssignmentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Servicio para la gestión de asignaciones usuario-rol y rol-permiso
 */
public interface AssignmentService {
    
    // === GESTIÓN USUARIO-ROL ===
    
    /**
     * Obtener roles asignados a un usuario
     */
    Flux<UserRoleAssignmentDto> getUserRoles(UUID userId);
    
    /**
     * Asignar rol a usuario
     */
    Mono<UserRoleAssignmentDto> assignRoleToUser(UUID userId, UUID roleId, AssignRoleRequestDto request);
    
    /**
     * Quitar rol de usuario
     */
    Mono<Void> removeRoleFromUser(UUID userId, UUID roleId);
    
    // === GESTIÓN ROL-PERMISO ===
    
    /**
     * Obtener permisos asignados a un rol
     */
    Flux<RolePermissionAssignmentDto> getRolePermissions(UUID roleId);
    
    /**
     * Asignar permiso a rol
     */
    Mono<RolePermissionAssignmentDto> assignPermissionToRole(UUID roleId, UUID permissionId);
    
    /**
     * Quitar permiso de rol (borrado lógico)
     */
    Mono<Void> removePermissionFromRole(UUID roleId, UUID permissionId);
    
    /**
     * Restaurar permiso de rol
     */
    Mono<RolePermissionAssignmentDto> restorePermissionToRole(UUID roleId, UUID permissionId);
    
    // === CONSULTAS AVANZADAS ===
    
    /**
     * Obtener todos los permisos efectivos de un usuario
     */
    Flux<RolePermissionAssignmentDto> getUserEffectivePermissions(UUID userId);
}
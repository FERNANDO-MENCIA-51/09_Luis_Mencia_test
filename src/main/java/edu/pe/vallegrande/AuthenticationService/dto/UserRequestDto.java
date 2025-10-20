package edu.pe.vallegrande.AuthenticationService.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

/**
 * DTO para las solicitudes de creación y actualización de usuarios
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;
    
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    private String password; // Solo para creación, se hashea internamente
    
    @NotNull(message = "El personId es obligatorio")
    private UUID personId;
    
    // Campos opcionales hasta que los microservicios de areas y positions estén disponibles
    private UUID areaId;
    
    private UUID positionId;
    
    private UUID directManagerId;
    
    @Pattern(regexp = "^(ACTIVE|INACTIVE|SUSPENDED)?$", message = "El status debe ser ACTIVE, INACTIVE o SUSPENDED")
    private String status; // ACTIVE, INACTIVE, SUSPENDED
    
    private Map<String, Object> preferences;
    private UUID createdBy;
    private UUID updatedBy;
}
package edu.pe.vallegrande.AuthenticationService.dto;

import edu.pe.vallegrande.AuthenticationService.dto.validation.MinimumAge;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para las solicitudes de creación y actualización de personas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDto {

    @NotNull(message = "El tipo de documento es obligatorio")
    private Integer documentTypeId;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 8, max = 20, message = "El número de documento debe tener entre 8 y 20 caracteres")
    private String documentNumber;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String lastName;

    @Size(max = 100, message = "El segundo nombre no puede exceder 100 caracteres")
    private String middleName;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @MinimumAge(value = 18, message = "Debe ser mayor de edad (mínimo 18 años)")
    private LocalDate birthDate;

    @NotBlank(message = "El género es obligatorio")
    @Pattern(regexp = "^[MF]$", message = "El género debe ser M o F")
    private String gender; // M, F

    @NotBlank(message = "El teléfono personal es obligatorio")
    @Size(min = 7, max = 20, message = "El teléfono personal debe tener entre 7 y 20 caracteres")
    private String personalPhone;

    @Size(max = 20, message = "El teléfono de trabajo no puede exceder 20 caracteres")
    private String workPhone;

    @Email(message = "El email debe ser válido")
    @Size(max = 200, message = "El email no puede exceder 200 caracteres")
    private String personalEmail;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 500, message = "La dirección debe tener entre 5 y 500 caracteres")
    private String address;
}
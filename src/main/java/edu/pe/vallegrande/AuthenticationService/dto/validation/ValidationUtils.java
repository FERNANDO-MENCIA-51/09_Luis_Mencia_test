package edu.pe.vallegrande.AuthenticationService.dto.validation;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Utilidades para validaciones comunes
 */
public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]{3,50}$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9+\\-\\s()]{7,20}$");

    /**
     * Valida formato de email
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Valida formato de username
     */
    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Valida formato de teléfono
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Valida que un UUID sea válido
     */
    public static boolean isValidUUID(String uuid) {
        if (uuid == null) {
            return false;
        }
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Valida que una contraseña sea segura
     * Mínimo 8 caracteres, al menos una mayúscula, una minúscula, un número y un
     * carácter especial
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpper = true;
            else if (Character.isLowerCase(c))
                hasLower = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if ("@$!%*?&".indexOf(c) >= 0)
                hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    /**
     * Sanitiza una cadena para prevenir inyección SQL
     */
    public static String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        // Eliminar caracteres peligrosos
        return input.replaceAll("[';\"\\\\]", "");
    }
}

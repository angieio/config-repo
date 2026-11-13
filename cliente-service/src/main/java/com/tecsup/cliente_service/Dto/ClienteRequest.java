package com.tecsup.cliente_service.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
public record ClienteRequest(
    @NotBlank String nombre,
    @NotBlank String apellidos,
    @NotBlank String direccion,
    @NotBlank String estado,
    @NotBlank @Pattern(regexp = "[0-9]{8}", message = "Debe contener exactamente 8 digitos") String nro_dni
) {}

package com.tecsup.producto_service.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
public record ProductoRequest(
    @NotBlank String nombre,
    @NotNull @Positive Double precio,
    @PositiveOrZero Integer stock,
    @Positive Long categoriaId
) {
    public ProductoRequest(String nombre, Double precio, Integer stock) {
        this(nombre, precio, stock, null);
    }
}

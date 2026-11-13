package com.tecsup.pedido_service.Dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record PedidoRequest(
    @NotNull @Positive Long clienteId,
    @NotNull @Positive Long productoId,
    @NotNull @Positive Integer cantidad
) {}

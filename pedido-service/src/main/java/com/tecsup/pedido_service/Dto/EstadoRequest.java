package com.tecsup.pedido_service.Dto;
import com.tecsup.pedido_service.Model.EstadoPedido;
import jakarta.validation.constraints.NotNull;
public record EstadoRequest(@NotNull EstadoPedido estado) {}

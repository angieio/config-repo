package com.tecsup.pedido_service.Dto;
import com.tecsup.pedido_service.Model.EstadoPedido;
public record PedidoResponse(Long id, Integer cantidad, EstadoPedido estado,
                             ClienteDTO cliente, ProductoDTO producto) {}

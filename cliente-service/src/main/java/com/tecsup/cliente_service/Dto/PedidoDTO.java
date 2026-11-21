package com.tecsup.cliente_service.Dto;

import com.fasterxml.jackson.databind.JsonNode;

public record PedidoDTO(
        Long id,
        Integer cantidad,
        String estado,
        JsonNode cliente,
        JsonNode producto,
        String mensaje
) {}

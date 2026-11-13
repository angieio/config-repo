package com.tecsup.pedido_service.Dto;
import com.fasterxml.jackson.annotation.JsonAlias;

// Cliente devuelve apellidos; la respuesta de Pedido usa apellido como en el PDF.
public record ClienteDTO(Long id, String nombre, @JsonAlias("apellidos") String apellido) {}

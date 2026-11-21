package com.tecsup.pedido_service.Services;

import com.tecsup.pedido_service.Dto.ProductoDTO;
import com.tecsup.pedido_service.client.ProductoClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductoCircuitService {

    @Autowired
    private ProductoClient productoClient;

    @CircuitBreaker(name = "productoService", fallbackMethod = "productoFallback")
    public ProductoDTO obtenerProducto(Long id) {
        System.out.println("Llamando a producto-service...");
        return productoClient.obtenerProducto(id);
    }

    public ProductoDTO productoFallback(Long id, Exception ex) {
        if (ex instanceof FeignException.NotFound) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Producto no encontrado", ex);
        }

        System.out.println("Circuit Breaker activado: " + ex.getMessage());
        return new ProductoDTO(id, "Producto temporalmente no disponible", null);
    }
}
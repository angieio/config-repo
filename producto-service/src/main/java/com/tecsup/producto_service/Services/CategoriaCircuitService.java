package com.tecsup.producto_service.Services;

import com.tecsup.producto_service.Dto.CategoriaDTO;
import com.tecsup.producto_service.client.CategoriaClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaCircuitService {

    @Autowired
    private CategoriaClient categoriaClient;

    @Bulkhead(
            name = "categoriaBulkhead",
            fallbackMethod = "categoriaFallback"
    )

    @Retry(name = "categoriaRetry", fallbackMethod = "categoriaFallback")

    @CircuitBreaker(name = "categoriaService")
    public CategoriaDTO obtenerCategoria(Long id) {
        System.out.println("Llamando a categoria-service...");
        return categoriaClient.obtenerCategoria(id);
    }

    public CategoriaDTO categoriaFallback(
            Long id,
            Exception ex
    ) {

        System.out.println(
                "Circuit Breaker activado: "
                        + ex.getMessage()
        );

        CategoriaDTO dto = new CategoriaDTO();

        dto.setId(id);
        dto.setNombre(
                "Categoría temporal no disponible"
        );

        return dto;
    }
}


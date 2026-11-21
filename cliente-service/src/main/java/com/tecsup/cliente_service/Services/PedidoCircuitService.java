package com.tecsup.cliente_service.Services;

import com.tecsup.cliente_service.Dto.PedidoDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoCircuitService {

    @Autowired
    private PedidoClient pedidoClient;

    @CircuitBreaker(name = "pedidoService", fallbackMethod = "pedidoFallback")
    public List<PedidoDTO> listarPedidos() {
        System.out.println("Llamando a pedido-service...");
        return pedidoClient.listarPedidos();
    }

    public List<PedidoDTO> pedidoFallback(Exception ex) {
        System.out.println("Circuit Breaker activado: " + ex.getMessage());

        PedidoDTO respaldo = new PedidoDTO(
                null, null, null, null, null,
                "Pedido temporalmente no disponible");

        return List.of(respaldo);
    }
}
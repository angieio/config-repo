package com.tecsup.pedido_service.Services;

import com.tecsup.pedido_service.Dto.ClienteDTO;
import com.tecsup.pedido_service.client.ClienteClient;
import feign.FeignException;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteRetryService {

    @Autowired
    private ClienteClient clienteClient;

    @Retry(name = "clienteRetry", fallbackMethod = "clienteFallback")
    public ClienteDTO obtenerCliente(Long id) {
        System.out.println("Retry: llamando a cliente-service para el cliente " + id);
        return clienteClient.obtenerCliente(id);
    }

    public ClienteDTO clienteFallback(Long id, Exception ex) {
        if (ex instanceof FeignException.NotFound) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cliente no encontrado", ex);
        }

        System.out.println("Fallback de Cliente: " + ex.getMessage());
        return new ClienteDTO(id, "Cliente temporalmente no disponible", null);
    }
}

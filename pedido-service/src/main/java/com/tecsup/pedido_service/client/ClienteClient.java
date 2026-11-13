package com.tecsup.pedido_service.client;
import com.tecsup.pedido_service.Dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service")
public interface ClienteClient {
    @GetMapping("/api/clientes/{id}")
    ClienteDTO obtenerCliente(@PathVariable("id") Long id);
}

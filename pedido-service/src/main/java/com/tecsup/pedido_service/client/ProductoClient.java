package com.tecsup.pedido_service.client;
import com.tecsup.pedido_service.Dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-service")
public interface ProductoClient {
    @GetMapping("/api/productos/{id}")
    ProductoDTO obtenerProducto(@PathVariable("id") Long id);
}

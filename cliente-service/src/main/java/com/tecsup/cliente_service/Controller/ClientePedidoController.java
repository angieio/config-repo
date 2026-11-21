package com.tecsup.cliente_service.Controller;

import com.tecsup.cliente_service.Dto.PedidoDTO;
import com.tecsup.cliente_service.Services.PedidoCircuitService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes/pedidos")
public class ClientePedidoController {

    private final PedidoCircuitService pedidoCircuitService;

    public ClientePedidoController(PedidoCircuitService pedidoCircuitService) {
        this.pedidoCircuitService = pedidoCircuitService;
    }

    @GetMapping
    public List<PedidoDTO> listarPedidos() {
        return pedidoCircuitService.listarPedidos();
    }
}
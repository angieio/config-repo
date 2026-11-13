package com.tecsup.pedido_service.Controller;
import com.tecsup.pedido_service.Dto.PedidoRequest;
import com.tecsup.pedido_service.Dto.EstadoRequest;
import com.tecsup.pedido_service.Dto.PedidoResponse;
import com.tecsup.pedido_service.Model.Pedido;
import com.tecsup.pedido_service.Services.PedidoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService service;
    public PedidoController(PedidoService service) { this.service = service; }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse registrar(@Valid @RequestBody PedidoRequest datos) { return service.guardar(datos); }
    @GetMapping("/{id}")
    public PedidoResponse obtener(@PathVariable("id") Long id) { return service.obtener(id); }
    @GetMapping
    public List<PedidoResponse> listar() { return service.listar(); }
    @PatchMapping("/{id}/estado")
    public Pedido cambiarEstado(@PathVariable("id") Long id, @Valid @RequestBody EstadoRequest datos) {
        return service.cambiarEstado(id, datos);
    }
    // Los pedidos se anulan mediante su estado; no existe un endpoint DELETE.
}

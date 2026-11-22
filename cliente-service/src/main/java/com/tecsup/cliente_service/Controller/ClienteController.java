package com.tecsup.cliente_service.Controller;
import com.tecsup.cliente_service.Model.Cliente;
import com.tecsup.cliente_service.Dto.ClienteRequest;
import com.tecsup.cliente_service.Services.ClienteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }
    @GetMapping
    public List<Cliente> listar() { return service.listar(); }
    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable("id") Long id) throws InterruptedException {
        Thread.sleep(5_000);
        return service.obtener(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente registrar(@Valid @RequestBody ClienteRequest datos) { return service.guardar(datos); }
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable("id") Long id, @Valid @RequestBody ClienteRequest datos) { return service.actualizar(id, datos); }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long id) { service.eliminar(id); }
}

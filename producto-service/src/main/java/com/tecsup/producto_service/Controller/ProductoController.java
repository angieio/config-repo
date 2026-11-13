package com.tecsup.producto_service.Controller;
import com.tecsup.producto_service.Model.Producto;
import com.tecsup.producto_service.Dto.ProductoRequest;
import com.tecsup.producto_service.Services.ProductoService;
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
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService service;
    public ProductoController(ProductoService service) { this.service = service; }
    @GetMapping
    public List<Producto> listar() { return service.listar(); }
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable("id") Long id) { return service.obtener(id); }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto registrar(@Valid @RequestBody ProductoRequest datos) { return service.guardar(datos); }
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable("id") Long id, @Valid @RequestBody ProductoRequest datos) { return service.actualizar(id, datos); }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long id) { service.eliminar(id); }
}

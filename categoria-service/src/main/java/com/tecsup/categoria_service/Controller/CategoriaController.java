package com.tecsup.categoria_service.Controller;

import com.tecsup.categoria_service.Dto.CategoriaDTO;
import com.tecsup.categoria_service.Model.Categoria;
import com.tecsup.categoria_service.Services.CategoriaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/categorias", "/api/categorías"})
public class CategoriaController {
    private final CategoriaService service;
    public CategoriaController(CategoriaService service) { this.service = service; }
    @GetMapping
    public List<Categoria> listar() { return service.listar(); }
    @GetMapping("/{id}")
    public Categoria obtener(@PathVariable("id") Long id) { return service.obtener(id); }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria registrar(@Valid @RequestBody CategoriaDTO datos) {
        return service.guardar(new Categoria(null, datos.getNombre(), datos.getEstado()));
    }
    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable("id") Long id, @Valid @RequestBody CategoriaDTO datos) {
        Categoria categoria = service.obtener(id);
        categoria.setNombre(datos.getNombre());
        categoria.setEstado(datos.getEstado());
        return service.guardar(categoria);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long id) { service.eliminar(id); }
}

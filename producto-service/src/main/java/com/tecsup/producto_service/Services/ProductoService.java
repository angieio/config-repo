package com.tecsup.producto_service.Services;
import com.tecsup.producto_service.Model.Producto;
import com.tecsup.producto_service.Dto.CategoriaDTO;
import com.tecsup.producto_service.client.CategoriaClient;
import feign.FeignException;
import com.tecsup.producto_service.Dto.ProductoRequest;
import com.tecsup.producto_service.Repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductoService {
    private final ProductoRepository repo;
    private final CategoriaClient categoriaClient;
    public ProductoService(ProductoRepository repo, CategoriaClient categoriaClient) {
        this.repo = repo;
        this.categoriaClient = categoriaClient;
    }
    public List<Producto> listar() { return repo.findAll().stream().map(this::completar).toList(); }
    private Producto buscar(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }
    public Producto obtener(Long id) { return completar(buscar(id)); }
    public Producto guardar(ProductoRequest datos) { return aplicar(new Producto(), datos); }
    public Producto actualizar(Long id, ProductoRequest datos) { return aplicar(buscar(id), datos); }
    private Producto aplicar(Producto entidad, ProductoRequest datos) {
        CategoriaDTO categoria = consultarCategoria(datos.categoriaId());
        entidad.setNombre(datos.nombre());
        entidad.setPrecio(datos.precio());
        entidad.setStock(datos.stock() == null ? 0 : datos.stock());
        entidad.setCategoriaId(datos.categoriaId());
        Producto guardado = repo.save(entidad);
        guardado.setCategoria(categoria);
        return guardado;
    }
    private Producto completar(Producto producto) {
        producto.setCategoria(consultarCategoria(producto.getCategoriaId()));
        return producto;
    }
    private CategoriaDTO consultarCategoria(Long id) {
        // Categoria es opcional en la actividad final; si se envia, se consulta por Feign.
        if (id == null) { return null; }
        try { return categoriaClient.obtenerCategoria(id); }
        catch (FeignException ex) {
            if (ex.status() == 404) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada", ex);
            }
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "No se pudo consultar categoria-service", ex);
        }
    }
    public void eliminar(Long id) { repo.delete(buscar(id)); }
}

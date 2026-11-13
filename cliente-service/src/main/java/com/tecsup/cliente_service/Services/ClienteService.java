package com.tecsup.cliente_service.Services;
import com.tecsup.cliente_service.Model.Cliente;
import com.tecsup.cliente_service.Dto.ClienteRequest;
import com.tecsup.cliente_service.Repository.ClienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {
    private final ClienteRepository repo;
    public ClienteService(ClienteRepository repo) { this.repo = repo; }
    public List<Cliente> listar() { return repo.findAll(); }
    public Cliente obtener(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }
    public Cliente guardar(ClienteRequest datos) { return aplicar(new Cliente(), datos); }
    public Cliente actualizar(Long id, ClienteRequest datos) { return aplicar(obtener(id), datos); }
    private Cliente aplicar(Cliente entidad, ClienteRequest datos) {
        entidad.setNombre(datos.nombre());
        entidad.setApellidos(datos.apellidos());
        entidad.setDireccion(datos.direccion());
        entidad.setEstado(datos.estado());
        entidad.setNro_dni(datos.nro_dni());
        return repo.save(entidad);
    }
    public void eliminar(Long id) { repo.delete(obtener(id)); }
}

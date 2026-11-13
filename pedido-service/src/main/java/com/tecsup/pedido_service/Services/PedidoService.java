package com.tecsup.pedido_service.Services;

import com.tecsup.pedido_service.client.ClienteClient;
import com.tecsup.pedido_service.client.ProductoClient;
import com.tecsup.pedido_service.Dto.ClienteDTO;
import com.tecsup.pedido_service.Dto.ProductoDTO;
import com.tecsup.pedido_service.Dto.PedidoRequest;
import com.tecsup.pedido_service.Dto.EstadoRequest;
import com.tecsup.pedido_service.Dto.PedidoResponse;
import com.tecsup.pedido_service.Model.Pedido;
import com.tecsup.pedido_service.Model.EstadoPedido;
import com.tecsup.pedido_service.Repository.PedidoRepository;
import feign.FeignException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoService {
    private final PedidoRepository repo;
    private final ClienteClient clienteClient;
    private final ProductoClient productoClient;

    public PedidoService(PedidoRepository repo, ClienteClient clienteClient, ProductoClient productoClient) {
        this.repo = repo;
        this.clienteClient = clienteClient;
        this.productoClient = productoClient;
    }

    public PedidoResponse guardar(PedidoRequest datos) {
        // Consultar las APIs antes de guardar evita pedidos con referencias inexistentes.
        ClienteDTO cliente = consultarCliente(datos.clienteId());
        ProductoDTO producto = consultarProducto(datos.productoId());
        Pedido pedido = new Pedido();
        pedido.setClienteId(datos.clienteId());
        pedido.setProductoId(datos.productoId());
        pedido.setCantidad(datos.cantidad());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        Pedido guardado = repo.save(pedido);
        return respuesta(guardado, cliente, producto);
    }

    public PedidoResponse obtener(Long id) {
        Pedido pedido = buscar(id);
        return completar(pedido);
    }

    public List<PedidoResponse> listar() {
        return repo.findAll().stream().map(this::completar).toList();
    }

    public Pedido cambiarEstado(Long id, EstadoRequest datos) {
        Pedido pedido = buscar(id);
        pedido.setEstado(datos.estado());
        return repo.save(pedido);
    }

    private Pedido buscar(Long id) {
        return repo.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
    }

    private PedidoResponse completar(Pedido pedido) {
        return respuesta(pedido, consultarCliente(pedido.getClienteId()), consultarProducto(pedido.getProductoId()));
    }

    private PedidoResponse respuesta(Pedido pedido, ClienteDTO cliente, ProductoDTO producto) {
        return new PedidoResponse(pedido.getId(), pedido.getCantidad(), pedido.getEstado(), cliente, producto);
    }

    private com.tecsup.pedido_service.Dto.ClienteDTO consultarCliente(Long id) {
        try { return clienteClient.obtenerCliente(id); }
        catch (FeignException ex) { throw errorRemoto("Cliente", ex); }
    }

    private com.tecsup.pedido_service.Dto.ProductoDTO consultarProducto(Long id) {
        try { return productoClient.obtenerProducto(id); }
        catch (FeignException ex) { throw errorRemoto("Producto", ex); }
    }

    private ResponseStatusException errorRemoto(String recurso, FeignException ex) {
        if (ex.status() == 404) {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, recurso + " no encontrado", ex);
        }
        return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
            "No se pudo consultar " + recurso + ". Comprueba el servicio y su registro en Eureka", ex);
    }
}

package com.tecsup.pedido_service;

import com.tecsup.pedido_service.client.*;
import com.tecsup.pedido_service.Dto.*;
import com.tecsup.pedido_service.Model.*;
import com.tecsup.pedido_service.Repository.PedidoRepository;
import com.tecsup.pedido_service.Services.PedidoService;
import feign.FeignException;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {
    private final PedidoRepository repo = mock(PedidoRepository.class);
    private final ClienteClient clientes = mock(ClienteClient.class);
    private final ProductoClient productos = mock(ProductoClient.class);
    private final PedidoService service = new PedidoService(repo, clientes, productos);

    @Test
    void consultaCompletaPedidoConAmbasApis() {
        Pedido pedido = new Pedido();
        pedido.setId(7L);
        pedido.setClienteId(2L);
        pedido.setProductoId(3L);
        pedido.setCantidad(2);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        when(repo.findById(7L)).thenReturn(Optional.of(pedido));
        when(clientes.obtenerCliente(2L)).thenReturn(new ClienteDTO(2L, "Carlos", "Ramirez"));
        when(productos.obtenerProducto(3L)).thenReturn(new ProductoDTO(3L, "Laptop", 2500.0));

        PedidoResponse respuesta = service.obtener(7L);

        assertEquals("Carlos", respuesta.cliente().nombre());
        assertEquals("Laptop", respuesta.producto().nombre());
        assertEquals(2, respuesta.cantidad());
        verify(clientes).obtenerCliente(2L);
        verify(productos).obtenerProducto(3L);
        verify(repo, never()).save(any());
    }

    @Test
    void registraSoloIdsCantidadYEstadoPendiente() {
        when(clientes.obtenerCliente(2L)).thenReturn(new ClienteDTO(2L, "Carlos", "Ramirez"));
        when(productos.obtenerProducto(3L)).thenReturn(new ProductoDTO(3L, "Laptop", 2500.0));
        when(repo.save(any())).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            assertEquals(2L, pedido.getClienteId());
            assertEquals(3L, pedido.getProductoId());
            assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
            pedido.setId(7L);
            return pedido;
        });
        assertEquals(7L, service.guardar(new PedidoRequest(2L, 3L, 2)).id());
    }

    @Test
    void noGuardaSiElClienteNoExiste() {
        FeignException error = mock(FeignException.class);
        when(error.status()).thenReturn(404);
        when(clientes.obtenerCliente(2L)).thenThrow(error);
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
            () -> service.guardar(new PedidoRequest(2L, 3L, 2)));
        assertEquals(404, ex.getStatusCode().value());
        verify(repo, never()).save(any());
    }

    @Test
    void responde503SiProductoNoEstaDisponible() {
        Pedido pedido = new Pedido();
        pedido.setClienteId(2L);
        pedido.setProductoId(3L);
        when(repo.findById(7L)).thenReturn(Optional.of(pedido));
        FeignException error = mock(FeignException.class);
        when(error.status()).thenReturn(503);
        when(productos.obtenerProducto(3L)).thenThrow(error);
        assertEquals(503, assertThrows(ResponseStatusException.class,
            () -> service.obtener(7L)).getStatusCode().value());
    }

    @Test
    void anulaSinBorrarElPedido() {
        Pedido pedido = new Pedido();
        when(repo.findById(7L)).thenReturn(Optional.of(pedido));
        when(repo.save(pedido)).thenReturn(pedido);
        assertEquals(EstadoPedido.ANULADO,
            service.cambiarEstado(7L, new EstadoRequest(EstadoPedido.ANULADO)).getEstado());
        verify(repo, never()).delete(any());
    }

    @Test
    void exigeIdsYCantidadPositiva() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertEquals(3, validator.validate(new PedidoRequest(null, null, 0)).size());
            assertTrue(validator.validate(new PedidoRequest(1L, 1L, 2)).isEmpty());
        }
    }
}

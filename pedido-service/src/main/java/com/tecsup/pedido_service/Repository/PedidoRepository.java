package com.tecsup.pedido_service.Repository;
import com.tecsup.pedido_service.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PedidoRepository extends JpaRepository<Pedido, Long> {}

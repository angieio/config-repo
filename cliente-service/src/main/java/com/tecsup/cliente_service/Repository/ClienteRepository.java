package com.tecsup.cliente_service.Repository;
import com.tecsup.cliente_service.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClienteRepository extends JpaRepository<Cliente, Long> {}

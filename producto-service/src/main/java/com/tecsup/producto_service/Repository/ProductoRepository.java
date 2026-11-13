package com.tecsup.producto_service.Repository;
import com.tecsup.producto_service.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoRepository extends JpaRepository<Producto, Long> {}

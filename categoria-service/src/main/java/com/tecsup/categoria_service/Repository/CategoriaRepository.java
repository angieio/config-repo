package com.tecsup.categoria_service.Repository;

import com.tecsup.categoria_service.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
}

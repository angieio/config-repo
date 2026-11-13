package com.tecsup.categoria_service.Services;

import com.tecsup.categoria_service.Exception.ResourceNotFoundException;
import com.tecsup.categoria_service.Model.Categoria;
import com.tecsup.categoria_service.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar() {
        return repo.findAll();
    }

    public Categoria guardar(Categoria p) {
        return repo.save(p);
    }

    public Categoria obtener(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Categoria no encontrada con id: " + id
                ));
    }

    public void eliminar(Long id) {
        Categoria categoria =
                repo.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Categoria no encontrada con id: " + id)
                        );
        repo.delete(categoria);
    }
}


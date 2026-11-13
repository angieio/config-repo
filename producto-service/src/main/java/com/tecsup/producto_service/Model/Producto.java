package com.tecsup.producto_service.Model;
import com.tecsup.producto_service.Dto.CategoriaDTO;
import jakarta.persistence.Transient;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "productos")
public class Producto {
    private Long categoriaId;
    @Transient
    private CategoriaDTO categoria;

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    public CategoriaDTO getCategoria() { return categoria; }
    public void setCategoria(CategoriaDTO categoria) { this.categoria = categoria; }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    private String nombre;
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    private Double precio;
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    private Integer stock;
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}

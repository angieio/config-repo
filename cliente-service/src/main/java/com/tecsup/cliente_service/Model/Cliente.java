package com.tecsup.cliente_service.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    private String nombre;
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    private String apellidos;
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    private String direccion;
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    private String estado;
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    private String nro_dni;
    public String getNro_dni() { return nro_dni; }
    public void setNro_dni(String nro_dni) { this.nro_dni = nro_dni; }
}

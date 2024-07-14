package com.flypass.financiera.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TipoTransaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // Constructor vacío (necesario para JPA)
    public TipoTransaccion() {
    }

    // Método toString() para imprimir los objetos de esta clase
    @Override
    public String toString() {
        return "TipoProducto [id=" + id + ", nombre=" + nombre + "]";
    }
}

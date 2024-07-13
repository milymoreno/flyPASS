package com.flypass.financiera.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal monto;
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_producto_origen")
    private Producto productoOrigen;

    @ManyToOne
    @JoinColumn(name = "id_producto_destino")
    private Producto productoDestino;

    @ManyToOne
    @JoinColumn(name = "id_tipo_transaccion")
    private TipoTransaccion tipoTransaccion;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }

    public Producto getProductoOrigen() {
        return productoOrigen;
    }

    public Producto getProductoDestino() {
        return productoDestino;
    }
}


package com.flypass.financiera.service;

import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoTransaccion;
import com.flypass.financiera.model.Transaccion;
import com.flypass.financiera.repository.ProductoRepository;
import com.flypass.financiera.repository.TipoTransaccionRepository;
import com.flypass.financiera.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TipoTransaccionRepository tipoTransaccionRepository;

    @Override
    public Transaccion crearTransaccion(Transaccion transaccion) {
        // Obtener el producto de origen y validar su existencia
        Producto productoOrigen = productoRepository.findById(transaccion.getProductoOrigen().getId())
                .orElseThrow(() -> new IllegalArgumentException("Producto de origen no encontrado."));
    
        // Obtener el producto de destino y validar su existencia
        Producto productoDestino = productoRepository.findById(transaccion.getProductoDestino().getId())
                .orElseThrow(() -> new IllegalArgumentException("Producto de destino no encontrado."));
    
        // Obtener y asignar el tipo de transacción
        TipoTransaccion tipoTransaccion = tipoTransaccionRepository.findById(transaccion.getTipoTransaccion().getId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de transacción no encontrado."));
    
        transaccion.setTipoTransaccion(tipoTransaccion);
    
        // Validar y ajustar el saldo del producto de origen
        BigDecimal nuevoSaldoOrigen = productoOrigen.getSaldo().subtract(transaccion.getMonto());
        if (nuevoSaldoOrigen.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente en el producto de origen.");
        }
        productoOrigen.setSaldo(nuevoSaldoOrigen);
        productoRepository.save(productoOrigen);
    
        // Ajustar el saldo del producto de destino
        BigDecimal nuevoSaldoDestino = productoDestino.getSaldo().add(transaccion.getMonto());
        productoDestino.setSaldo(nuevoSaldoDestino);
        productoRepository.save(productoDestino);
    
        // Guardar la transacción
        return transaccionRepository.save(transaccion);
    }
    


    @Override
    public List<Transaccion> obtenerTodasLasTransacciones() {
        return transaccionRepository.findAll();
    }
}


package com.flypass.financiera.service;

import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoTransaccion;
import com.flypass.financiera.model.Transaccion;
import com.flypass.financiera.repository.ProductoRepository;
import com.flypass.financiera.repository.TipoTransaccionRepository;
import com.flypass.financiera.repository.TransaccionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    /*@Autowired
    private ClienteRepository clienteRepository;
    */

    @Autowired
    private TipoTransaccionRepository tipoTransaccionRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public Transaccion crearTransaccion(Transaccion transaccion) {
        validarTipoTransaccion(transaccion);
        validarProductos(transaccion);        
        return transaccionRepository.save(transaccion);
    }

    private void validarProductos(Transaccion transaccion) {        
        //Validar producto origen
        Producto productoOrigen = productoRepository.findById(transaccion.getProductoOrigen().getId())
            .orElseThrow(() -> new IllegalArgumentException("El número del producto origen no fue encontrado"));
        transaccion.setProductoOrigen(productoOrigen);

         // Validar producto destino solo cuando es transferencia
         if (transaccion.getTipoTransaccion().getNombre().equalsIgnoreCase("Transferencia")) {
            Producto productoDestino = productoRepository.findById(transaccion.getProductoDestino().getId())
                .orElseThrow(() -> new IllegalArgumentException("El número del producto destino no fue encontrado"));
            transaccion.setProductoDestino(productoDestino);
        }
                    

    }

    private void validarTipoTransaccion(Transaccion transaccion) {
        if (transaccion.getTipoTransaccion() == null ) {
            throw new IllegalArgumentException("El tipo de transacción no es válido");
        }
        // Validar que el tipo de transacción sea uno de los permitidos
        TipoTransaccion tipoTransaccion = tipoTransaccionRepository.findByNombre(transaccion.getTipoTransaccion().getNombre())
            .orElseThrow(() -> new IllegalArgumentException("Tipo de transacción no permitido."));
        
        transaccion.setTipoTransaccion(tipoTransaccion);
    }

    
    @Override
    public List<Transaccion> obtenerTodasLasTransacciones() {
        return transaccionRepository.findAll();
    }

    


}


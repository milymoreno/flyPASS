package com.flypass.financiera.service;

//import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoTransaccion;
import com.flypass.financiera.model.Transaccion;
//import com.flypass.financiera.repository.ClienteRepository;
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
        validarProductos(transaccion);
        validarTipoTransaccion(transaccion);
        return transaccionRepository.save(transaccion);
    }

    private void validarProductos(Transaccion transaccion) {
        Producto productoOrigen = productoRepository.findById(transaccion.getProductoOrigen().getId())
                .orElseThrow(() -> new IllegalArgumentException("Producto de origen no encontrado."));
        transaccion.setProductoOrigen(productoOrigen);

        // Validar si es una transferencia entre cuentas, validar el producto destino si existe
        if (transaccion.getTipoTransaccion() != null && 
            transaccion.getTipoTransaccion().getNombre().equalsIgnoreCase("Transferencia")) {
            Producto productoDestino = productoRepository.findById(transaccion.getProductoDestino().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto de destino no encontrado."));
            transaccion.setProductoDestino(productoDestino);
        }
    }

    private void validarTipoTransaccion(Transaccion transaccion) {
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


package com.flypass.financiera.service;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.model.Estado;
import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoProducto;
import com.flypass.financiera.repository.EstadoRepository;
import com.flypass.financiera.repository.ProductoRepository;
import com.flypass.financiera.repository.TipoProductoRepository;
import com.flypass.financiera.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Override
    public Producto crearProducto(Producto producto) {

        if (producto.getCliente() == null || producto.getCliente().getId() == null) {
            throw new IllegalArgumentException("El producto debe estar vinculado a un cliente.");
        }

        TipoProducto tipoProducto = tipoProductoRepository.findById(producto.getTipoProducto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de producto no encontrado."));

        if (!tipoProducto.getNombre().equals("Corriente") && !tipoProducto.getNombre().equals("Ahorro")) {
            throw new IllegalArgumentException("Solo se permiten productos de tipo 'cuenta corriente' o 'cuenta de ahorros'.");
        }

        if (producto.getEstado() == null || producto.getEstado().getId() == null) {
            throw new IllegalArgumentException("El producto debe tener un Estado");
        }

        Estado estado = estadoRepository.findById(producto.getEstado().getId())
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado."));
        producto.setEstado(estado);

        if (!estado.getNombre().equals("Activo") && !estado.getNombre().equals("Inactiva") && !estado.getNombre().equals("Cancelada")) {
            throw new IllegalArgumentException("Solo se permiten estados 'Activo', 'Inactivo' o 'Cancelado'.");
        }

        //validarTipoProducto(producto);
        validarClienteAsociado(producto);

        return productoRepository.save(producto);
    }

    /*private void validarTipoProducto(Producto producto) {
        TipoProducto tipoProducto = producto.getTipoProducto();
        // Verificar que el tipo de transacción sea cuenta corriente o cuenta de ahorros
        if (!tipoTransaccion.getNombre().equalsIgnoreCase("cuenta corriente") &&
            !tipoTransaccion.getNombre().equalsIgnoreCase("cuenta de ahorros")) {
            throw new IllegalArgumentException("El tipo de producto debe ser 'cuenta corriente' o 'cuenta de ahorros'.");
        }
    }*/

    private void validarClienteAsociado(Producto producto) {
        Cliente cliente = producto.getCliente();
        if (cliente == null || cliente.getId() == null) {
            throw new IllegalArgumentException("El producto debe estar asociado a un cliente.");
        }
        // Verificar si el cliente existe en la base de datos
        Cliente clienteExistente = clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));
        // Asignar el cliente existente al producto
        producto.setCliente(clienteExistente);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        Producto producto = obtenerProductoPorId(id);

        TipoProducto tipoProducto = tipoProductoRepository.findById(productoDetalles.getTipoProducto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de producto no encontrado."));

        if (!tipoProducto.getNombre().equals("cuenta corriente") && !tipoProducto.getNombre().equals("cuenta de ahorros")) {
            throw new IllegalArgumentException("Solo se permiten productos de tipo 'cuenta corriente' o 'cuenta de ahorros'.");
        }

        producto.setTipoProducto(tipoProducto);
        producto.setSaldo(productoDetalles.getSaldo());
        producto.setEstado(productoDetalles.getEstado());
        //validarTipoProducto(productoDetalles);
        validarClienteAsociado(productoDetalles);
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto producto = obtenerProductoPorId(id);
        productoRepository.delete(producto);
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        return optionalProducto.get();}

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    @Override
    public String consultarEstadoProducto(Long idProducto) {
        Optional<Producto> optionalProducto = productoRepository.findById(idProducto);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            Estado estado = producto.getEstado();
            if (estado != null) {
                return estado.getNombre(); 
            } else {
                return "Estado no definido";
            }
        } else {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
    }
}

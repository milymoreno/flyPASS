package com.flypass.financiera.service;

import com.flypass.financiera.model.Estado;
import com.flypass.financiera.model.Producto;
import com.flypass.financiera.repository.EstadoRepository;
import com.flypass.financiera.repository.ProductoRepository;
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

    @Override
    public Producto crearProducto(Producto producto) {
        Estado estado = estadoRepository.findById(producto.getEstado().getId())
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado."));
        producto.setEstado(estado);

        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        Producto producto = obtenerProductoPorId(id);
        producto.setTipoCuenta(productoDetalles.getTipoCuenta());
        producto.setSaldo(productoDetalles.getSaldo());
        producto.setEstado(productoDetalles.getEstado());
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
        return optionalProducto.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
}

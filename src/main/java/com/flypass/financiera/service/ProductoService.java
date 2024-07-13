package com.flypass.financiera.service;

import com.flypass.financiera.model.Producto;

import java.util.List;

public interface ProductoService {

    Producto crearProducto(Producto producto);

    Producto actualizarProducto(Long id, Producto productoDetalles);

    void eliminarProducto(Long id);

    Producto obtenerProductoPorId(Long id);

    List<Producto> obtenerTodosLosProductos();
    
    //para consultar el estado de los productos o cuentas
    String consultarEstadoProducto(Long idProducto);
}

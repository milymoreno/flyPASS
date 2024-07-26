package com.flypass.financiera.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoProducto;
import com.flypass.financiera.service.ProductoService;

public class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearProducto() {
        // Mock data
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setSaldo(BigDecimal.ZERO);

        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setId(1L);
        tipoProducto.setNombre("Ahorros");

        producto.setTipoProducto(tipoProducto);
        producto.setNumeroCuenta("5312345678"); // Inicializa numeroCuenta con un valor válido

        when(productoService.crearProducto(any(Producto.class))).thenReturn(producto);

        // Test
        ResponseEntity<Producto> responseEntity = productoController.crearProducto(producto);

        // Verification
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(producto, responseEntity.getBody());
        // Verificar que se generó correctamente el número de cuenta
        assertNotNull(producto.getNumeroCuenta()); // Asegúrate de que no sea null
        assertEquals(10, producto.getNumeroCuenta().length()); // Verificar longitud del número de cuenta
        assertTrue(producto.getNumeroCuenta().startsWith("53")); // Verificar prefijo para tipo "Ahorros"
    }

    @Test
    public void testActualizarProducto() {
        // Mock data
        Long productoId = 1L;
        Producto producto = new Producto();
        producto.setId(productoId);
        producto.setSaldo(BigDecimal.ZERO);

        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setId(2L);
        tipoProducto.setNombre("Corriente");

        producto.setTipoProducto(tipoProducto);
        producto.setNumeroCuenta("3312345678"); // Inicializa numeroCuenta con un valor válido

        when(productoService.actualizarProducto(anyLong(), any(Producto.class))).thenReturn(producto);

        // Test
        ResponseEntity<Producto> responseEntity = productoController.actualizarProducto(productoId, producto);

        // Verification
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(producto, responseEntity.getBody());
        // Verificar que se generó correctamente el número de cuenta
        assertNotNull(responseEntity.getBody().getNumeroCuenta()); // Asegúrate de que no sea null
        assertEquals(10, responseEntity.getBody().getNumeroCuenta().length()); // Verificar longitud del número de cuenta
        // Verificar prefijo para tipo "Corriente"
        assertTrue(responseEntity.getBody().getNumeroCuenta().startsWith("33")); 
    }

    @Test
    public void testEliminarProducto() {
        // Mock data
        Long productoId = 1L;

        // Test
        ResponseEntity<Void> responseEntity = productoController.eliminarProducto(productoId);

        // Verification
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(productoService, times(1)).eliminarProducto(productoId);
    }

    @Test
    public void testObtenerProductoPorIdExistente() {
        // Mock data
        Long productoId = 1L;
        Producto producto = new Producto();
        producto.setId(productoId);

        when(productoService.obtenerProductoPorId(productoId)).thenReturn(producto);

        // Test
        ResponseEntity<Producto> responseEntity = productoController.obtenerProductoPorId(productoId);

        // Verification
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(producto, responseEntity.getBody());
    }

    @Test
    public void testObtenerProductoPorIdNoExistente() {
        // Mock data
        Long productoId = 1L;

        when(productoService.obtenerProductoPorId(productoId)).thenReturn(null);

        // Test
        ResponseEntity<Producto> responseEntity = productoController.obtenerProductoPorId(productoId);

        // Verification
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    public void testObtenerTodosLosProductos() {
        // Mock data
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto());
        productos.add(new Producto());

        when(productoService.obtenerTodosLosProductos()).thenReturn(productos);

        // Test
        ResponseEntity<List<Producto>> responseEntity = productoController.obtenerTodosLosProductos();

        // Verification
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productos, responseEntity.getBody());
    }
}

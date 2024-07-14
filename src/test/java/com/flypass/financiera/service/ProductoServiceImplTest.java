package com.flypass.financiera.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoProducto;
import com.flypass.financiera.repository.ProductoRepository;
import com.flypass.financiera.repository.TipoProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private TipoProductoRepository tipoProductoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearProductoConTipo() {
        // Datos de prueba
        String nombreTipoProducto = "Ahorros";
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setId(1L);
        tipoProducto.setNombre(nombreTipoProducto);

        Producto producto = new Producto();
        producto.setNumeroCuenta("33123456");
        producto.setSaldo(new BigDecimal("1000.00"));

        // Configurar mocks
        //when(tipoProductoRepository.findByNombre(nombreTipoProducto)).thenReturn(Optional.of(tipoProducto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // Ejecutar el servicio
        Producto result = productoService.crearProducto(producto);

        // Verificaciones
        assertNotNull(result);
        assertEquals(tipoProducto, result.getTipoProducto());
        //verify(tipoProductoRepository, times(1)).findByNombre(nombreTipoProducto);
        verify(productoRepository, times(1)).save(producto);
    }
}

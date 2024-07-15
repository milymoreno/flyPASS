package com.flypass.financiera.service;

import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoTransaccion;
import com.flypass.financiera.model.Transaccion;
import com.flypass.financiera.repository.ProductoRepository;
import com.flypass.financiera.repository.TipoTransaccionRepository;
import com.flypass.financiera.repository.TransaccionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceImplTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private TipoTransaccionRepository tipoTransaccionRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;

    private Transaccion transaccion;
    private Producto productoOrigen;
    private Producto productoDestino;
    private TipoTransaccion tipoTransaccion;

    @BeforeEach
    public void setUp() {
        tipoTransaccion = new TipoTransaccion();
        tipoTransaccion.setNombre("Transferencia");

        productoOrigen = new Producto();
        productoOrigen.setId(1L);
        productoOrigen.setSaldo(new BigDecimal("1000"));

        productoDestino = new Producto();
        productoDestino.setId(2L);
        productoDestino.setSaldo(new BigDecimal("500"));

        transaccion = new Transaccion();
        transaccion.setProductoOrigen(productoOrigen);
        transaccion.setProductoDestino(productoDestino);
        transaccion.setTipoTransaccion(tipoTransaccion);
        transaccion.setMonto(new BigDecimal("200"));
    }

    @Test
    public void testCrearTransaccion() {
        when(tipoTransaccionRepository.findByNombre(anyString())).thenReturn(Optional.of(tipoTransaccion));
        when(productoRepository.findById(productoOrigen.getId())).thenReturn(Optional.of(productoOrigen));
        when(productoRepository.findById(productoDestino.getId())).thenReturn(Optional.of(productoDestino));
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccion);

        Transaccion creada = transaccionService.crearTransaccion(transaccion);

        assertNotNull(creada);
        assertEquals(tipoTransaccion.getNombre(), creada.getTipoTransaccion().getNombre());
        assertEquals(new BigDecimal("800"), productoOrigen.getSaldo());
        assertEquals(new BigDecimal("700"), productoDestino.getSaldo());

        verify(tipoTransaccionRepository, times(1)).findByNombre(anyString());
        verify(productoRepository, times(2)).findById(anyLong());
        verify(productoRepository, times(2)).save(any(Producto.class));
        verify(transaccionRepository, times(1)).save(any(Transaccion.class));
    }

    @Test
    public void testCrearTransaccionTipoTransaccionNoPermitido() {
        when(tipoTransaccionRepository.findByNombre(anyString())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.crearTransaccion(transaccion);
        });

        assertEquals("Tipo de transacción no permitido.", exception.getMessage());
        verify(tipoTransaccionRepository, times(1)).findByNombre(anyString());
        verify(productoRepository, times(0)).findById(anyLong());
        verify(transaccionRepository, times(0)).save(any(Transaccion.class));
    }

    @Test
    public void testCrearTransaccionSaldoInsuficiente() {
        transaccion.setMonto(new BigDecimal("2000")); // Monto mayor al saldo del producto origen

        when(tipoTransaccionRepository.findByNombre(anyString())).thenReturn(Optional.of(tipoTransaccion));
        when(productoRepository.findById(productoOrigen.getId())).thenReturn(Optional.of(productoOrigen));
        when(productoRepository.findById(productoDestino.getId())).thenReturn(Optional.of(productoDestino));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.crearTransaccion(transaccion);
        });

        assertEquals("Saldo insuficiente para realizar la transferencia", exception.getMessage());
        verify(tipoTransaccionRepository, times(1)).findByNombre(anyString());
        verify(productoRepository, times(2)).findById(anyLong());
        verify(productoRepository, times(0)).save(any(Producto.class));
        verify(transaccionRepository, times(0)).save(any(Transaccion.class));
    }

    @Test
    public void testCrearTransaccionProductoOrigenNoEncontrado() {
        when(tipoTransaccionRepository.findByNombre(anyString())).thenReturn(Optional.of(tipoTransaccion));
        when(productoRepository.findById(productoOrigen.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.crearTransaccion(transaccion);
        });

        assertEquals("El número del producto origen no fue encontrado", exception.getMessage());
        verify(tipoTransaccionRepository, times(1)).findByNombre(anyString());
        verify(productoRepository, times(1)).findById(productoOrigen.getId());
        verify(productoRepository, times(0)).save(any(Producto.class));
        verify(transaccionRepository, times(0)).save(any(Transaccion.class));
    }

    @Test
    public void testCrearTransaccionProductoDestinoNoEncontrado() {
        when(tipoTransaccionRepository.findByNombre(anyString())).thenReturn(Optional.of(tipoTransaccion));
        when(productoRepository.findById(productoOrigen.getId())).thenReturn(Optional.of(productoOrigen));
        when(productoRepository.findById(productoDestino.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transaccionService.crearTransaccion(transaccion);
        });

        assertEquals("El número del producto destino no fue encontrado", exception.getMessage());
        verify(tipoTransaccionRepository, times(1)).findByNombre(anyString());
        verify(productoRepository, times(2)).findById(anyLong());
        verify(productoRepository, times(0)).save(any(Producto.class));
        verify(transaccionRepository, times(0)).save(any(Transaccion.class));
    }

    @Test
    public void testObtenerTodasLasTransacciones() {
        List<Transaccion> transacciones = List.of(transaccion);

        when(transaccionRepository.findAll()).thenReturn(transacciones);

        List<Transaccion> result = transaccionService.obtenerTodasLasTransacciones();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transaccion.getMonto(), result.get(0).getMonto());

        verify(transaccionRepository, times(1)).findAll();
    }
}

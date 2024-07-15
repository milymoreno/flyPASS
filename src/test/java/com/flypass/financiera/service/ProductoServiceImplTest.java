package com.flypass.financiera.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.model.Estado;
import com.flypass.financiera.model.Producto;
import com.flypass.financiera.model.TipoProducto;
import com.flypass.financiera.repository.ClienteRepository;
import com.flypass.financiera.repository.EstadoRepository;
import com.flypass.financiera.repository.ProductoRepository;
import com.flypass.financiera.repository.TipoProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private EstadoRepository estadoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TipoProductoRepository tipoProductoRepository;

    @Mock
    private EstadoService estadoService;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;
    private Cliente cliente;
    private Estado estado;
    private TipoProducto tipoProducto;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);

        tipoProducto = new TipoProducto();
        tipoProducto.setId(1L);
        tipoProducto.setNombre("Ahorros");

        estado = new Estado();
        estado.setId(1L);
        estado.setNombre("Activo");

        producto = new Producto();
        producto.setCliente(cliente);
        producto.setTipoProducto(tipoProducto);
        producto.setEstado(estado);
        producto.setSaldo(BigDecimal.ZERO);
    }

    @Test
    public void testCrearProducto() {
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(tipoProductoRepository.findById(tipoProducto.getId())).thenReturn(Optional.of(tipoProducto));
        when(estadoRepository.findById(estado.getId())).thenReturn(Optional.of(estado));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(estadoService.getEstadoActivo()).thenReturn(estado);

        Producto creado = productoService.crearProducto(producto);

        assertNotNull(creado);
        assertEquals(producto.getCliente(), creado.getCliente());
        assertEquals(producto.getTipoProducto(), creado.getTipoProducto());
        assertEquals(producto.getEstado(), creado.getEstado());

        verify(clienteRepository, times(1)).findById(cliente.getId());
        verify(tipoProductoRepository, times(1)).findById(tipoProducto.getId());
        verify(estadoRepository, times(1)).findById(estado.getId());
        verify(productoRepository, times(1)).save(any(Producto.class));
        verify(estadoService, times(1)).getEstadoActivo();
    }

    @Test
    public void testActualizarProducto() {
        Long productoId = 1L;
        Producto productoActualizado = new Producto();
        productoActualizado.setCliente(cliente);
        productoActualizado.setTipoProducto(tipoProducto);
        productoActualizado.setEstado(estado);
        productoActualizado.setSaldo(BigDecimal.TEN);

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(tipoProductoRepository.findById(tipoProducto.getId())).thenReturn(Optional.of(tipoProducto));
        when(estadoRepository.findById(estado.getId())).thenReturn(Optional.of(estado));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);

        Producto actualizado = productoService.actualizarProducto(productoId, productoActualizado);

        assertNotNull(actualizado);
        assertEquals(BigDecimal.TEN, actualizado.getSaldo());

        verify(productoRepository, times(1)).findById(productoId);
        verify(clienteRepository, times(1)).findById(cliente.getId());
        verify(tipoProductoRepository, times(1)).findById(tipoProducto.getId());
        verify(estadoRepository, times(1)).findById(estado.getId());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testEliminarProducto() {
        Long productoId = 1L;

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        productoService.eliminarProducto(productoId);

        verify(productoRepository, times(1)).findById(productoId);
        verify(productoRepository, times(1)).delete(producto);
    }

    @Test
    public void testConsultarEstadoProducto() {
        Long productoId = 1L;

        when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));

        String estadoProducto = productoService.consultarEstadoProducto(productoId);

        assertEquals("Activo", estadoProducto);

        verify(productoRepository, times(1)).findById(productoId);
    }
}

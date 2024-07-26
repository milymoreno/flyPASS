package com.flypass.financiera.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setTipoIdentificacion("CC");
        cliente.setNumeroIdentificacion("123456789");
        cliente.setNombres("Juan");
        cliente.setApellidos("Pérez");
        cliente.setEmail("juan.perez@example.com");
    }

    @Test
    public void testCrearCliente() {
        when(clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion()))
                .thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente creado = clienteService.crearCliente(cliente);

        assertNotNull(creado);
        assertEquals(cliente.getNombres(), creado.getNombres());
        assertEquals(cliente.getApellidos(), creado.getApellidos());
        assertEquals(cliente.getEmail(), creado.getEmail());

        verify(clienteRepository, times(1)).findByTipoIdentificacionAndNumeroIdentificacion(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testCrearClienteExistente() {
        when(clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion()))
                .thenReturn(Optional.of(cliente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.crearCliente(cliente);
        });

        assertEquals("Ya existe un cliente con el mismo tipo y número de identificación.", exception.getMessage());
        verify(clienteRepository, times(1)).findByTipoIdentificacionAndNumeroIdentificacion(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());
        verify(clienteRepository, times(0)).save(any(Cliente.class));
    }

    @Test
    public void testActualizarCliente() {
        Long clienteId = 1L;
        Cliente clienteDetalles = new Cliente();
        clienteDetalles.setNombres("Carlos");
        clienteDetalles.setApellidos("Ramírez");
        clienteDetalles.setEmail("carlos.ramirez@example.com");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente actualizado = clienteService.actualizarCliente(clienteId, clienteDetalles);

        assertNotNull(actualizado);
        assertEquals("Carlos", actualizado.getNombres());
        assertEquals("Ramírez", actualizado.getApellidos());
        assertEquals("carlos.ramirez@example.com", actualizado.getEmail());

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testEliminarCliente() {
        Long clienteId = 1L;

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        clienteService.eliminarCliente(clienteId);

        verify(clienteRepository, times(1)).findById(clienteId);
        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    public void testObtenerClientePorId() {
        Long clienteId = 1L;

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        Cliente encontrado = clienteService.obtenerClientePorId(clienteId);

        assertNotNull(encontrado);
        assertEquals(cliente.getNombres(), encontrado.getNombres());

        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    public void testObtenerClientePorIdNoExistente() {
        Long clienteId = 1L;

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.obtenerClientePorId(clienteId);
        });

        assertEquals("Cliente no encontrado.", exception.getMessage());
        verify(clienteRepository, times(1)).findById(clienteId);
    }

    @Test
    public void testObtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> encontrados = clienteService.obtenerTodosLosClientes();

        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
        assertEquals(cliente.getNombres(), encontrados.get(0).getNombres());

        verify(clienteRepository, times(1)).findAll();
    }
}

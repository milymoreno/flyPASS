package com.flypass.financiera.controller;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.service.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
//import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombres("John");
        cliente.setApellidos("Doe");
        cliente.setEmail("johndoe@example.com");
    }

    @Test
    public void testCrearCliente() {
        when(clienteService.crearCliente(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.crearCliente(cliente);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());

        verify(clienteService, times(1)).crearCliente(any(Cliente.class));
    }

    @Test
    public void testActualizarCliente() {
        when(clienteService.actualizarCliente(anyLong(), any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.actualizarCliente(1L, cliente);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());

        verify(clienteService, times(1)).actualizarCliente(anyLong(), any(Cliente.class));
    }

    @Test
    public void testEliminarCliente() {
        doNothing().when(clienteService).eliminarCliente(anyLong());

        ResponseEntity<Void> response = clienteController.eliminarCliente(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(clienteService, times(1)).eliminarCliente(anyLong());
    }

    @Test
    public void testObtenerClientePorId() {
        when(clienteService.obtenerClientePorId(anyLong())).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.obtenerClientePorId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());

        verify(clienteService, times(1)).obtenerClientePorId(anyLong());
    }

    @Test
    public void testObtenerTodosLosClientes() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.obtenerTodosLosClientes()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clienteController.obtenerTodosLosClientes();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientes, response.getBody());

        verify(clienteService, times(1)).obtenerTodosLosClientes();
    }
}

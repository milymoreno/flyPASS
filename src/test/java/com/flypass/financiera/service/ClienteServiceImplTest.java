package com.flypass.financiera.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.repository.ClienteRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    public void testCrearCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombres("John");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente creado = clienteService.crearCliente(cliente);
        assertNotNull(creado);
    }
}

package com.flypass.financiera.service;

import com.flypass.financiera.model.Cliente;

import java.util.List;

public interface ClienteService {

    Cliente crearCliente(Cliente cliente);

    Cliente actualizarCliente(Long id, Cliente clienteDetalles);

    void eliminarCliente(Long id);

    Cliente obtenerClientePorId(Long id);

    List<Cliente> obtenerTodosLosClientes();
}

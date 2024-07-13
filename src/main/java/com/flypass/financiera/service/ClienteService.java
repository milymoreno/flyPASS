package com.flypass.financiera.service;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente crearCliente(Cliente cliente) {
        if (!cliente.esMayorDeEdad()) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad.");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente clienteDetalles) {
        Cliente cliente = obtenerClientePorId(id);
        cliente.setNombres(clienteDetalles.getNombres());
        cliente.setApellidos(clienteDetalles.getApellidos());
        cliente.setEmail(clienteDetalles.getEmail());
        cliente.setFechaModificacion(clienteDetalles.getFechaModificacion());
        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {
        Cliente cliente = obtenerClientePorId(id);
        if (!cliente.getProductos().isEmpty()) {
            throw new IllegalArgumentException("El cliente tiene productos vinculados y no puede ser eliminado.");
        }
        clienteRepository.delete(cliente);
    }

    public Cliente obtenerClientePorId(Long id) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (optionalCliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
        return optionalCliente.get();
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }
}


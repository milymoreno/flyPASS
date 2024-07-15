package com.flypass.financiera.service;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente crearCliente(Cliente cliente) {
        // Implementación del método crearCliente
        // Validación: Verificar si ya existe un cliente con el mismo tipo y número de identificación
        Optional<Cliente> clienteExistente = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());
        if (clienteExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente con el mismo tipo y número de identificación.");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente clienteDetalles) {
        // Implementación del método actualizarCliente
        Cliente cliente = obtenerClientePorId(id);
        cliente.setNombres(clienteDetalles.getNombres());
        cliente.setApellidos(clienteDetalles.getApellidos());
        cliente.setEmail(clienteDetalles.getEmail());
        cliente.setFechaModificacion(clienteDetalles.getFechaModificacion());
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        // Implementación del método eliminarCliente
        Cliente cliente = obtenerClientePorId(id);
        clienteRepository.delete(cliente);
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        // Implementación del método obtenerClientePorId
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        return optionalCliente.orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        // Implementación del método obtenerTodosLosClientes
        return clienteRepository.findAll();
    }
}

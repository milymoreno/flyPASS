package com.flypass.financiera.validation;

import com.flypass.financiera.model.Cliente;
import com.flypass.financiera.repository.ClienteRepository;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueClienteValidator implements ConstraintValidator<UniqueCliente, Cliente> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(UniqueCliente constraintAnnotation) {
    }

    @Override
    public boolean isValid(Cliente cliente, ConstraintValidatorContext context) {
        // Implementa la lógica para validar la unicidad de tipo y número de identificación
        if (cliente == null) {
            return true; 
        }
        // Verifica si ya existe un cliente con el mismo tipo y número de identificación
        Optional<Cliente> existingCliente = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(
                cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());

        if (existingCliente.isEmpty()) {
            return true;
        }    
        return existingCliente.get().getId().equals(cliente.getId());
    }

}


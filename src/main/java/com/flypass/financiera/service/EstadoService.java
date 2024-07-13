package com.flypass.financiera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.flypass.financiera.model.Estado;
import com.flypass.financiera.repository.EstadoRepository;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado getEstadoActivo() {
        return estadoRepository.findByNombre("Activo")
                .orElseThrow(() -> new IllegalArgumentException("Estado 'Activo' no encontrado."));
    }
}

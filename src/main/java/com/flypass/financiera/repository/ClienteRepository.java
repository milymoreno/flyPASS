package com.flypass.financiera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flypass.financiera.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}


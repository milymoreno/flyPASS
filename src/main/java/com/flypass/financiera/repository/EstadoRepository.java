package com.flypass.financiera.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flypass.financiera.model.Estado;
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Optional<Estado> findByNombre(String nombre);
}


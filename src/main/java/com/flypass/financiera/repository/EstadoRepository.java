package com.flypass.financiera.repository;

import com.flypass.financiera.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    Optional<Estado> findByNombre(String nombre);
}


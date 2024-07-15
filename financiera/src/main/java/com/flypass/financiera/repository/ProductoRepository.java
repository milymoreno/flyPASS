package com.flypass.financiera.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.flypass.financiera.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNumeroCuenta(String numeroCuenta);
}


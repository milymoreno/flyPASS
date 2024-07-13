package com.flypass.financiera.repository;

import com.flypass.financiera.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}


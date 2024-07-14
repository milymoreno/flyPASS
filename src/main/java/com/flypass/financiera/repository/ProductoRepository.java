package com.flypass.financiera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flypass.financiera.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}


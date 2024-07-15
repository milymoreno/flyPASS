package com.flypass.financiera.repository;

import com.flypass.financiera.model.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Long> {
    // Método para buscar un TipoTransaccion por su nombre
    Optional<TipoTransaccion> findByNombre(String nombre);
}

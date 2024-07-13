package com.flypass.financiera.repository;

import com.flypass.financiera.model.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTransaccionRepository extends JpaRepository<TipoTransaccion, Long> {
}

package com.flypass.financiera.service;

import com.flypass.financiera.model.Transaccion;

import java.util.List;

public interface TransaccionService {

    Transaccion crearTransaccion(Transaccion transaccion);

    List<Transaccion> obtenerTodasLasTransacciones();
}

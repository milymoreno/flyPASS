package com.flypass.financiera.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flypass.financiera.model.Transaccion;
import com.flypass.financiera.service.TransaccionService;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping
    public ResponseEntity<Transaccion> crearTransaccion(@RequestBody Transaccion transaccion) {
        Transaccion nuevaTransaccion = transaccionService.crearTransaccion(transaccion);
        return ResponseEntity.ok(nuevaTransaccion);
    }

    @GetMapping
    public ResponseEntity<List<Transaccion>> obtenerTodasLasTransacciones() {
        List<Transaccion> transacciones = transaccionService.obtenerTodasLasTransacciones();
        return ResponseEntity.ok(transacciones);
    }
}

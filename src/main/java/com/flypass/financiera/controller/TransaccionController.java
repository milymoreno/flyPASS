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

//Para los logs
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    private static final Logger logger = LoggerFactory.getLogger(TransaccionController.class);

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping
    public ResponseEntity<Transaccion> crearTransaccion(@RequestBody Transaccion transaccion) {
        logger.info("Se ha Creado una Transaccion con el metodo POST al endpoint /transacciones");
        Transaccion nuevaTransaccion = transaccionService.crearTransaccion(transaccion);
        return ResponseEntity.ok(nuevaTransaccion);
    }

    @GetMapping
    public ResponseEntity<List<Transaccion>> obtenerTodasLasTransacciones() {
        logger.info("Se han Listado Todas las Transacciones con el metodo GET al endpoint /transacciones");
        List<Transaccion> transacciones = transaccionService.obtenerTodasLasTransacciones();
        return ResponseEntity.ok(transacciones);
    }
}

package com.flypass.financiera.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.flypass.financiera.model.Transaccion;
import com.flypass.financiera.repository.TransaccionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class TransaccionServiceImplTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;

    @Test
    public void testCrearTransaccion() {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(new BigDecimal("1000"));
        when(transaccionRepository.save(transaccion)).thenReturn(transaccion);

        Transaccion creada = transaccionService.crearTransaccion(transaccion);
        assertNotNull(creada);
    }
}


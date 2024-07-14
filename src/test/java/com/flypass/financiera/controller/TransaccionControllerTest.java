package com.flypass.financiera.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.flypass.financiera.model.Transaccion;
import com.flypass.financiera.service.TransaccionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(TransaccionController.class)
public class TransaccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransaccionService transaccionService;

    @Test
    public void testCrearTransaccion() throws Exception {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(new BigDecimal("1000"));

        when(transaccionService.crearTransaccion(transaccion)).thenReturn(transaccion);

        mockMvc.perform(post("/transacciones")
                .contentType("application/json")
                .content("{\"monto\":1000}"))
                .andExpect(status().isOk());
    }
}


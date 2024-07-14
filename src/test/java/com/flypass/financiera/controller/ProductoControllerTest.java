package com.flypass.financiera.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.flypass.financiera.model.Producto;
import com.flypass.financiera.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    public void testCrearProducto() throws Exception {
        // Datos de prueba
        String tipoProducto = "Ahorros";
        Producto producto = new Producto();
        producto.setNumeroCuenta("33123456");
        producto.setSaldo(new BigDecimal("1000.00"));

        Producto createdProducto = new Producto();
        createdProducto.setNumeroCuenta("33123456");
        createdProducto.setSaldo(new BigDecimal("1000.00"));

        when(productoService.crearProducto(createdProducto)).thenReturn(createdProducto);

        // Ejecutar la solicitud y verificar los resultados
        mockMvc.perform(post("/productos")
                .param("tipoProducto", tipoProducto)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"numeroCuenta\": \"33123456\", \"saldo\": 1000.00 }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"numeroCuenta\": \"123456\", \"saldo\": 1000.00 }"));

        //verify(productoService, times(1)).crearProducto(eq(Producto.class));
    }
}



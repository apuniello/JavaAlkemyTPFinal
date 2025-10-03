package com.bbva.clase07.controllers;

import com.bbva.clase07.Producto;
import com.bbva.clase07.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProductos() throws Exception {
        Producto producto = new Producto();
        producto.setNombre("Test");
        productoRepository.save(producto);
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value("Test"));
    }

    @Test
    void testCreateProducto() throws Exception {
        Producto producto = new Producto();
        producto.setNombre("Nuevo");
        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Nuevo"));
    }
}
package com.bbva.clase07;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

class ProductoAsyncServiceTest {
    @Test
    void testGetAllProductosAsync() throws ExecutionException, InterruptedException {
        ProductoRepository repo = Mockito.mock(ProductoRepository.class);
        Producto p1 = new Producto(); p1.setNombre("A");
        Producto p2 = new Producto(); p2.setNombre("B");
        Mockito.when(repo.findAll()).thenReturn(Arrays.asList(p1, p2));
        ProductoAsyncService service = new ProductoAsyncService();
        List<Producto> result = service.getAllProductosAsync(repo).get();
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getNombre());
    }

    @Test
    void testProcesarProductosAsync() throws ExecutionException, InterruptedException {
        Producto p1 = new Producto(); p1.setNombre("a");
        Producto p2 = new Producto(); p2.setNombre("b");
        ProductoAsyncService service = new ProductoAsyncService();
        List<Producto> result = service.procesarProductosAsync(Arrays.asList(p1, p2)).get();
        assertEquals("A", result.get(0).getNombre());
        assertEquals("B", result.get(1).getNombre());
    }
}


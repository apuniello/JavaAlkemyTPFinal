package com.bbva.clase07.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bbva.clase07.Producto;
import com.bbva.clase07.ProductoRepository;
import com.bbva.clase07.ProductoAsyncService;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoAsyncService productoAsyncService;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @GetMapping("/{id}")
    public Producto getProductoById(@PathVariable String id) {
        return productoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable String id, @RequestBody Producto producto) {
        producto.setId(id);
        return productoRepository.save(producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable String id) {
        productoRepository.deleteById(id);
    }

    @GetMapping("/async")
    public CompletableFuture<List<Producto>> getAllProductosAsync() {
        return productoAsyncService.getAllProductosAsync(productoRepository);
    }

    @GetMapping("/procesar-async")
    public CompletableFuture<List<Producto>> procesarProductosAsync() {
        List<Producto> productos = productoRepository.findAll();
        return productoAsyncService.procesarProductosAsync(productos);
    }

    @PostMapping("/update-async")
    public CompletableFuture<List<Producto>> updateProductosAsync(@RequestBody List<Producto> productos) {
        return productoAsyncService.updateProductosAsync(productos, productoRepository);
    }
}

package com.bbva.clase07;

import org.springframework.stereotype.Service;
import java.util.concurrent.*;
import java.util.List;

@Service
public class ProductoAsyncService {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public CompletableFuture<List<Producto>> getAllProductosAsync(ProductoRepository productoRepository) {
        return CompletableFuture.supplyAsync(productoRepository::findAll, executor);
    }

    // Ejemplo de procesamiento concurrente de productos
    public CompletableFuture<List<Producto>> procesarProductosAsync(List<Producto> productos) {
        return CompletableFuture.supplyAsync(() -> {
            productos.forEach(p -> {
                // Simulación de procesamiento en paralelo usando Thread
                Thread thread = new Thread(() -> p.setNombre(p.getNombre().toUpperCase()));
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            return productos;
        }, executor);
    }

    public CompletableFuture<List<Producto>> updateProductosAsync(List<Producto> productos, ProductoRepository productoRepository) {
        return CompletableFuture.supplyAsync(() -> {
            productos.parallelStream().forEach(productoRepository::save);
            return productos;
        }, executor);
    }

    public void shutdown() {
        executor.shutdown();
    }
}

package com.bbva.clase07;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto, String> {
    // Puedes agregar métodos personalizados aquí si lo necesitas
}


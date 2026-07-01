package com.ecomarket.demo.services;

import com.ecomarket.demo.entities.Product;
import com.ecomarket.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CREATE / UPDATE (Guardar o actualizar)
    public Product saveProduct(Product product) {
        // .save() sirve tanto para insertar uno nuevo como para actualizar si el ID ya
        // existe
        return productRepository.save(product);
    }

    // READ (Buscar todos)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // READ (Buscar por ID)
    public Product getProductById(Long id) {
        // findById devuelve un Optional por si el id no existe en la BD
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findByStockGreaterThan(Integer stock) {
        return productRepository.findByStockGreaterThan(stock);
    }

    public List<Product> findByStockLessThan(Integer stock) {
        return productRepository.findByStockLessThan(stock);
    }

    // DELETE (Eliminar por ID)
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
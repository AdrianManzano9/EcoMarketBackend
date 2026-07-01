package com.ecomarket.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.demo.entities.Product;
import com.ecomarket.demo.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) Integer maxStock) {

        // 1. Si mandan el parámetro 'name', filtra por nombre
        if (name != null && !name.trim().isEmpty()) {
            return new ResponseEntity<>(productService.findByName(name), HttpStatus.OK);
        }

        // 2. Si mandan 'minStock', filtra los que tienen más stock que ese número
        if (minStock != null) {
            return new ResponseEntity<>(productService.findByStockGreaterThan(minStock), HttpStatus.OK);
        }

        // 3. Si mandan 'maxStock', filtra los que tienen más stock que ese número
        if (maxStock != null) {
            return new ResponseEntity<>(productService.findByStockLessThan(maxStock), HttpStatus.OK);
        }

        // Si no viene ningún filtro en la URL, devuelve todos los productos sin más
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // ENDPOINT PARA ACTUALIZAR (PUT)
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        Product updatedProduct = productService.saveProduct(product);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK); // Devuelve HTTP 200
    }

    // ENDPOINT PARA ELIMINAR UN PRODUCTO (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Devuelve HTTP 204
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 si no existía el ID
        }
    }
}
package com.ecomarket.demo.controllers;

import com.ecomarket.demo.entities.Order;
import com.ecomarket.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders") // Ruta base para todas las operaciones de Órdenes
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ENDPOINT PARA CREAR UNA ÓRDEN (POST)

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    // ENDPOINT PARA ACTUALIZAR UNA ÓRDEN (PUT)

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        Order updatedOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // ENDPOINT PARA TRAER TODAS LAS ÓRDENES (GET)
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK); // Devuelve HTTP 200
    }

    // ENDPOINT PARA BUSCAR UNA ÓRDEN POR ID
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK); // Devuelve HTTP 200
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 si el ID no existe
        }
    }

    // ENDPOINT PARA ELIMINAR UNA ÓRDEN (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Devuelve HTTP 204
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 si no existía el ID
        }
    }

}

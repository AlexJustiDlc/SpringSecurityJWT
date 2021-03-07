package com.jwtcrud.api.controller;

import com.jwtcrud.api.model.Producto;
import com.jwtcrud.api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Producto> lista = service.getAll();
        if (lista.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay Productos");
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Producto producto = service.findById(id);
        if (producto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria "+id+" no encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(producto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Producto producto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(producto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(producto, id));
    }
}

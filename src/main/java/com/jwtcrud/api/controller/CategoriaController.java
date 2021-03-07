package com.jwtcrud.api.controller;

import com.jwtcrud.api.model.Categoria;
import com.jwtcrud.api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Categoria> lista = service.getAll();
        if (lista.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay Categorias");
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Categoria categoria = service.findById(id);
        if (categoria == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Categoria "+id+" no encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Categoria categoria){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(categoria));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Categoria categoria, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(categoria, id));
    }
}

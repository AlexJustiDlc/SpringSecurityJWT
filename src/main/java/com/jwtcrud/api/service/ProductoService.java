package com.jwtcrud.api.service;

import com.jwtcrud.api.model.Producto;
import com.jwtcrud.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public List<Producto> getAll(){
        return repository.findAll();
    }

    public Producto findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public String create(Producto producto){
        repository.save(producto);
        return "Producto \""+producto.getDescripcion()+"\" Creado.";
    }

    public String update(Producto producto, Long id){
        Producto updProducto = findById(id);
        updProducto.setDescripcion(producto.getDescripcion());
        updProducto.setPrecio(producto.getPrecio());
        updProducto.setStock(producto.getStock());
        updProducto.setCategoria(producto.getCategoria());
        repository.save(updProducto);
        return "Producto \""+updProducto.getDescripcion()+"\" Actualizado a \""+producto.getDescripcion()+"\".";
    }
}

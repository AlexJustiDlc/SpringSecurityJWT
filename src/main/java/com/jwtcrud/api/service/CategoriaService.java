package com.jwtcrud.api.service;

import com.jwtcrud.api.model.Categoria;
import com.jwtcrud.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> getAll(){
        return repository.findAll();
    }

    public Categoria findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public String create(Categoria categoria){
        repository.save(categoria);
        return "Categoria \""+categoria.getDescripcion()+"\" Creado.";
    }

    public String update (Categoria categoria, Long id){
        Categoria updCategoria = findById(id);
        updCategoria.setDescripcion(categoria.getDescripcion());
        repository.save(updCategoria);
        return "Categoria \""+updCategoria.getDescripcion()+"\" Actualizado a \""+categoria.getDescripcion()+"\".";
    }
}

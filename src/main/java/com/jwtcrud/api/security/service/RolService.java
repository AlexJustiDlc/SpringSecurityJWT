package com.jwtcrud.api.security.service;

import com.jwtcrud.api.security.model.Rol;
import com.jwtcrud.api.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository repository;

    public Rol getByName(String name){
        return repository.findByName(name);
    }

    public void create(Rol rol){
        repository.save(rol);
    }
}

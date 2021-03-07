package com.jwtcrud.api.security.util;

import com.jwtcrud.api.security.model.Rol;
import com.jwtcrud.api.security.model.Usuario;
import com.jwtcrud.api.security.service.RolService;
import com.jwtcrud.api.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Data {

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;

    Set<Rol> roles = new HashSet<>();

    List<Usuario> usuarios = new ArrayList<>();

    @PostConstruct
    public void data(){
        if (roles.isEmpty()){
            Rol rol1 = new Rol();
            rol1.setName("ADMIN");
            rolService.create(rol1);

            Rol rol2 = new Rol();
            rol2.setName("USER");
            rolService.create(rol2);

            roles.add(rol1);
            roles.add(rol2);
        }
        else System.out.println("Ya existen Roles");

        if (usuarios.isEmpty()){
            Usuario usuario = new Usuario();
            usuario.setNombre("Alex Fernando");
            usuario.setCorreo("alex@gmail.com");
            usuario.setUsername("ALX99");
            usuario.setPassword("alex123");
            usuario.setRoles(roles);
            usuarioService.create(usuario);
        }
        else System.out.println("Ya existen Usuarios en el Sistema");
    }
}

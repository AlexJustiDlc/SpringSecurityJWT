package com.jwtcrud.api.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class UsuarioPrincipal implements UserDetails {
    private String nombre;
    private String correo;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UsuarioPrincipal build(Usuario usuario){
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Rol rol : usuario.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+rol.getName()));
        }

        return new UsuarioPrincipal(
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getUsername(),
                usuario.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

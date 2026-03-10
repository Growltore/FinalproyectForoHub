package com.aluracursos.forohub.domain.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacionService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = usuarioRepository.findByLogin(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return usuario;
    }
}
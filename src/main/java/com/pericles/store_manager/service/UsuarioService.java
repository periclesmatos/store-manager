package com.pericles.store_manager.service;

import com.pericles.store_manager.domain.Usuario;
import com.pericles.store_manager.dto.usuario.UsuarioRequest;
import com.pericles.store_manager.exception.UsuarioJaExisteException;
import com.pericles.store_manager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario cadastrarUsuario(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.findByUsername(usuarioRequest.username()) != null) {
            throw new UsuarioJaExisteException(usuarioRequest.username());
        }

        var usuario = new Usuario(usuarioRequest);
        usuario.deifinirSenhaCodificada(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return usuario;
    }

}

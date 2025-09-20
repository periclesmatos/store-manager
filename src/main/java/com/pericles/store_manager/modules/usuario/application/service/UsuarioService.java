package com.pericles.store_manager.modules.usuario.application.service;

import com.pericles.store_manager.modules.usuario.interfaces.dto.UsuarioRequest;
import com.pericles.store_manager.modules.usuario.interfaces.dto.UsuarioResponse;
import com.pericles.store_manager.modules.usuario.interfaces.mapper.UsuarioMapper;
import com.pericles.store_manager.modules.usuario.domain.model.Usuario;
import com.pericles.store_manager.modules.shared.exception.UsuarioJaExisteException;
import com.pericles.store_manager.modules.usuario.domain.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository,  UsuarioMapper usuarioMapper) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public UsuarioResponse cadastrarUsuario(UsuarioRequest request) {
        if (usuarioRepository.findByUsername(request.username()) != null) {
            throw new UsuarioJaExisteException(request.username());
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.deifinirSenhaCodificada(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return usuarioMapper.toReponse(usuario);
    }

}

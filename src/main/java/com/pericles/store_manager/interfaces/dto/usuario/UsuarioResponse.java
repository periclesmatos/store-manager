package com.pericles.store_manager.interfaces.dto.usuario;

import com.pericles.store_manager.domain.model.Usuario;

public record UsuarioResponse(
        Long id,
        String username
) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername());
    }
}

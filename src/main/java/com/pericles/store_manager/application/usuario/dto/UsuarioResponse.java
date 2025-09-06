package com.pericles.store_manager.application.usuario.dto;

import com.pericles.store_manager.domain.usuario.model.Usuario;

public record UsuarioResponse(
        Long id,
        String username
) {
    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername());
    }
}

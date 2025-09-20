package com.pericles.store_manager.modules.usuario.interfaces.mapper;

import com.pericles.store_manager.modules.usuario.interfaces.dto.UsuarioRequest;
import com.pericles.store_manager.modules.usuario.interfaces.dto.UsuarioResponse;
import com.pericles.store_manager.modules.usuario.domain.model.Usuario;

public interface UsuarioMapper {
    Usuario toEntity(UsuarioRequest usuarioRequest);
    UsuarioResponse toReponse(Usuario usuario);
}

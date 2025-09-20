package com.pericles.store_manager.modules.usuario.interfaces.mapper;

import com.pericles.store_manager.modules.usuario.interfaces.dto.UsuarioRequest;
import com.pericles.store_manager.modules.usuario.interfaces.dto.UsuarioResponse;
import com.pericles.store_manager.modules.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioRequest request) {
        return new Usuario(
                request.username(),
                request.password()
        );
    }

    @Override
    public UsuarioResponse toReponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername()
        );
    }

}

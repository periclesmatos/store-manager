package com.pericles.store_manager.modules.cliente.interfaces.mapper;

import com.pericles.store_manager.modules.cliente.domain.model.Endereco;
import com.pericles.store_manager.modules.cliente.interfaces.dto.request.EnderecoRequest;
import com.pericles.store_manager.modules.cliente.interfaces.dto.response.EnderecoResponse;

public interface EnderecoMapper {
    Endereco toEntity(EnderecoRequest dto);
    EnderecoResponse toResponse(Endereco endereco);
    void updateToEntity(Endereco endereco, EnderecoRequest dto);
}

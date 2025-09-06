package com.pericles.store_manager.application.cliente.mapper;

import com.pericles.store_manager.domain.cliente.model.Endereco;
import com.pericles.store_manager.application.cliente.dto.request.EnderecoRequest;
import com.pericles.store_manager.application.cliente.dto.response.EnderecoResponse;

public interface EnderecoMapper {
    Endereco toEntity(EnderecoRequest dto);
    EnderecoResponse toResponse(Endereco endereco);
    void updateToEntity(Endereco endereco, EnderecoRequest dto);
}

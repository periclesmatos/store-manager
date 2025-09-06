package com.pericles.store_manager.application.cliente.mapper;

import com.pericles.store_manager.application.cliente.dto.request.ClienteUpdate;
import com.pericles.store_manager.domain.cliente.model.Cliente;
import com.pericles.store_manager.application.cliente.dto.request.ClienteRequest;
import com.pericles.store_manager.application.cliente.dto.response.ClienteResponse;

public interface ClienteMapper {
    Cliente toEntity(ClienteRequest dto);
    ClienteResponse toResponse(Cliente cliente);
    void updateToEntity(Cliente cliente, ClienteUpdate dto);
}

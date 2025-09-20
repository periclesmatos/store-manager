package com.pericles.store_manager.modules.cliente.interfaces.mapper;

import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteUpdate;
import com.pericles.store_manager.modules.cliente.domain.model.Cliente;
import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteRequest;
import com.pericles.store_manager.modules.cliente.interfaces.dto.response.ClienteResponse;

public interface ClienteMapper {
    Cliente toEntity(ClienteRequest dto);
    ClienteResponse toResponse(Cliente cliente);
    void updateToEntity(Cliente cliente, ClienteUpdate dto);
}

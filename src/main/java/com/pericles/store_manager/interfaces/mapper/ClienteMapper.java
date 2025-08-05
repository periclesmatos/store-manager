package com.pericles.store_manager.interfaces.mapper;

import com.pericles.store_manager.domain.model.Cliente;
import com.pericles.store_manager.domain.model.Endereco;
import com.pericles.store_manager.interfaces.dto.cliente.ClienteRequest;
import com.pericles.store_manager.interfaces.dto.cliente.ClienteResponse;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequest dto) {
        Endereco endereco = EnderecoMapper.toEntity(dto.endereco());
        return new Cliente(
                dto.nome(),
                dto.email(),
                dto.cpf(),
                endereco
        );
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                EnderecoMapper.toResponse(cliente.getEndereco())
        );
    }

}

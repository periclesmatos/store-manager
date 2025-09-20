package com.pericles.store_manager.modules.cliente.interfaces.mapper;

import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteRequest;
import com.pericles.store_manager.modules.cliente.interfaces.dto.response.ClienteResponse;
import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteUpdate;
import com.pericles.store_manager.modules.cliente.domain.model.Cliente;
import com.pericles.store_manager.modules.cliente.domain.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapperImpl implements ClienteMapper {

    private final EnderecoMapper enderecoMapper;

    public ClienteMapperImpl(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    @Override
    public Cliente toEntity(ClienteRequest dto) {
        Endereco endereco = enderecoMapper.toEntity(dto.endereco());
        return new Cliente(
                dto.nome(),
                dto.email(),
                dto.cpf(),
                endereco
        );
    }

    @Override
    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                enderecoMapper.toResponse(cliente.getEndereco())
        );
    }

    @Override
    public void updateToEntity(Cliente cliente, ClienteUpdate dto) {
        if (dto.nome() != null) {
            cliente.atualizarNome(dto.nome());
        }

        if (dto.email() != null) {
            cliente.atualizarEmail(dto.email());
        }

        if (dto.cpf() != null) {
            cliente.atualizarCpf(dto.cpf());
        }

        if (dto.endereco() != null) {
            enderecoMapper.updateToEntity(cliente.getEndereco(), dto.endereco());
        }
    }

}

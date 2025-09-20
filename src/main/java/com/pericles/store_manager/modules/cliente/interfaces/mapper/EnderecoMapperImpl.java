package com.pericles.store_manager.modules.cliente.interfaces.mapper;

import com.pericles.store_manager.modules.cliente.interfaces.dto.request.EnderecoRequest;
import com.pericles.store_manager.modules.cliente.interfaces.dto.response.EnderecoResponse;
import com.pericles.store_manager.modules.cliente.domain.model.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapperImpl implements EnderecoMapper {

    public Endereco toEntity(EnderecoRequest dto) {
        return new Endereco(
                dto.rua() != null ? dto.rua() : "",
                dto.numero() != null ? dto.numero() : "",
                dto.bairro() != null ? dto.bairro() : "",
                dto.cidade() != null ? dto.cidade() : "",
                dto.uf() != null ? dto.uf() : "",
                dto.complemento() != null ? dto.complemento() : "",
                dto.cep() != null ? dto.cep() : ""
        );
    }

    public EnderecoResponse toResponse(Endereco endereco) {
        return new EnderecoResponse(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getComplemento(),
                endereco.getCep()
        );
    }

    public void updateToEntity(Endereco endereco, EnderecoRequest dto) {
        if (dto.rua() != null) {
            endereco.atualizarRua(dto.rua());
        }

        if (dto.numero() != null) {
            endereco.atualizarNumero(dto.numero());
        }

        if (dto.bairro() != null) {
            endereco.atualizarBairro(dto.bairro());
        }

        if (dto.cidade() != null) {
            endereco.atualizarCidade(dto.cidade());
        }

        if (dto.uf() != null) {
            endereco.atualizarUf(dto.uf());
        }

        if (dto.complemento() != null) {
            endereco.atualizarComplemento(dto.complemento());
        }

        if (dto.cep() != null) {
            endereco.atualizarCep(dto.cep());
        }
    }

}

package com.pericles.store_manager.interfaces.mapper;

import com.pericles.store_manager.domain.model.Endereco;
import com.pericles.store_manager.interfaces.dto.cliente.EnderecoRequest;
import com.pericles.store_manager.interfaces.dto.cliente.EnderecoResponse;

public class EnderecoMapper {

    public static Endereco toEntity(EnderecoRequest dto) {
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

    public static EnderecoResponse toResponse(Endereco endereco) {
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

}

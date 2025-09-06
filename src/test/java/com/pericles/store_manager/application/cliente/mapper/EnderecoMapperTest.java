package com.pericles.store_manager.application.cliente.mapper;

import com.pericles.store_manager.application.cliente.mapper.EnderecoMapperImpl;
import com.pericles.store_manager.domain.cliente.model.Endereco;
import com.pericles.store_manager.application.cliente.dto.request.EnderecoRequest;
import com.pericles.store_manager.application.cliente.dto.response.EnderecoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EnderecoMapperTest {

    @InjectMocks
    private EnderecoMapperImpl enderecoMapper;

    @Test
    void toEntity_deveConverterDtoParaEntityCorretamente() {
        EnderecoRequest dto = new EnderecoRequest(
                "Rua A",
                "100",
                "Bairro B",
                "Cidade C",
                "CE",
                "",
                "60000-000"
        );
        Endereco endereco = enderecoMapper.toEntity(dto);

        assertEquals("Rua A", endereco.getRua());
        assertEquals("100", endereco.getNumero());
        assertEquals("Bairro B", endereco.getBairro());
        assertEquals("Cidade C", endereco.getCidade());
        assertEquals("CE", endereco.getUf());
        assertEquals("", endereco.getComplemento());
        assertEquals("60000-000", endereco.getCep());
    }

    @Test
    void toResponse_deveConverterEntityParaDtoCorretamente() {
        Endereco endereco = new Endereco(
                "Rua A",
                "100",
                "Bairro B",
                "Cidade C",
                "CE",
                "",
                "60000-000"
        );

        EnderecoResponse response = enderecoMapper.toResponse(endereco);

        assertEquals("Rua A", response.rua());
        assertEquals("100", response.numero());
        assertEquals("Bairro B", response.bairro());
        assertEquals("Cidade C", response.cidade());
        assertEquals("CE", response.uf());
        assertEquals("", response.complemento());
        assertEquals("60000-000", response.cep());
    }
}

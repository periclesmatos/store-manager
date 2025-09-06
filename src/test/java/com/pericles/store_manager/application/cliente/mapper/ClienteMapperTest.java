package com.pericles.store_manager.application.cliente.mapper;

import com.pericles.store_manager.application.cliente.dto.response.EnderecoResponse;
import com.pericles.store_manager.application.cliente.mapper.EnderecoMapper;
import com.pericles.store_manager.application.cliente.mapper.ClienteMapperImpl;
import com.pericles.store_manager.domain.cliente.model.Cliente;
import com.pericles.store_manager.domain.cliente.model.Endereco;
import com.pericles.store_manager.application.cliente.dto.request.ClienteRequest;
import com.pericles.store_manager.application.cliente.dto.response.ClienteResponse;
import com.pericles.store_manager.application.cliente.dto.request.EnderecoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ClienteMapperTest {

    @Mock
    private EnderecoMapper enderecoMapper;

    @InjectMocks
    private ClienteMapperImpl clienteMapper;

    private Endereco enderecoMock;
    private EnderecoResponse enderecoResponseMock;

    private void setId(Cliente cliente, Long id){
        try {
            Field idField = Cliente.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(cliente, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        enderecoMock = new Endereco(
                "Rua A",
                "100",
                "Bairro B",
                "Cidade C",
                "CE",
                "",
                "60000-000"
        );

        enderecoResponseMock = new EnderecoResponse(
                "Rua A",
                "100",
                "Bairro B",
                "Cidade C",
                "CE",
                "",
                "60000-000"
        );
    }

    @Test
    void toEntity_deveConverterDtoParaEntityCorretamente() {
        EnderecoRequest enderecoRequest = new EnderecoRequest(
                "Rua A",
                "100",
                "Bairro B",
                "Cidade C",
                "CE",
                "",
                "60000-000"
        );

        ClienteRequest dto = new ClienteRequest(
                "Maria",
                "maria@email.com",
                "12345678900",
                enderecoRequest
        );

        when(enderecoMapper.toEntity(any())).thenReturn(enderecoMock);

        Cliente cliente = clienteMapper.toEntity(dto);

        assertEquals("Maria", cliente.getNome());
        assertEquals("maria@email.com", cliente.getEmail());
        assertEquals("12345678900", cliente.getCpf());

        assertNotNull(cliente.getEndereco());
        assertEquals("Rua A", cliente.getEndereco().getRua());
        assertEquals("100", cliente.getEndereco().getNumero());
        assertEquals("Bairro B", cliente.getEndereco().getBairro());
        assertEquals("Cidade C", cliente.getEndereco().getCidade());
        assertEquals("CE", cliente.getEndereco().getUf());
        assertEquals("", cliente.getEndereco().getComplemento());
        assertEquals("60000-000", cliente.getEndereco().getCep());
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
        Cliente cliente = new Cliente(
                "Maria",
                "maria@email.com",
                "12345678900",
                endereco
        );
        setId(cliente, 1L);

        when(enderecoMapper.toResponse(any())).thenReturn(enderecoResponseMock);

        ClienteResponse response = clienteMapper.toResponse(cliente);

        assertEquals(1L, response.id());
        assertEquals("Maria", response.nome());
        assertEquals("maria@email.com", response.email());
        assertEquals("12345678900", response.cpf());

        assertNotNull(response.endereco());
        assertEquals("Rua A", response.endereco().rua());
        assertEquals("100", response.endereco().numero());
        assertEquals("Bairro B", response.endereco().bairro());
        assertEquals("Cidade C", response.endereco().cidade());
        assertEquals("CE", response.endereco().uf());
        assertEquals("", response.endereco().complemento());
        assertEquals("60000-000", response.endereco().cep());
    }

}

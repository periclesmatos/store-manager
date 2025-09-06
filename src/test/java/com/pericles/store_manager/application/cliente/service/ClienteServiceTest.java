package com.pericles.store_manager.application.cliente.service;

import com.pericles.store_manager.application.cliente.dto.request.ClienteRequest;
import com.pericles.store_manager.application.cliente.dto.request.ClienteUpdate;
import com.pericles.store_manager.application.cliente.dto.request.EnderecoRequest;
import com.pericles.store_manager.application.cliente.dto.response.ClienteResponse;
import com.pericles.store_manager.application.cliente.dto.response.EnderecoResponse;
import com.pericles.store_manager.application.cliente.mapper.ClienteMapper;
import com.pericles.store_manager.application.cliente.service.ClienteServiceImpl;
import com.pericles.store_manager.domain.cliente.model.Cliente;
import com.pericles.store_manager.domain.cliente.model.Endereco;
import com.pericles.store_manager.domain.cliente.repository.ClienteRepository;
import com.pericles.store_manager.domain.cliente.service.ClienteDomainService;
import com.pericles.store_manager.infrastructure.exception.NegocioException;
import com.pericles.store_manager.infrastructure.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteDomainService clienteDomainService;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private ClienteRequest request;
    private ClienteUpdate update;
    private ClienteResponse response;
    private ClienteResponse updateResponse;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        request = criarClienteRequest();
        update = criarClienteUpdate();
        response = criarClienteResponse();
        updateResponse = criarClienteUpdateResponse();
        cliente = criarCliente();
    }

    private ClienteRequest criarClienteRequest() {
        return new ClienteRequest(
                "Maria",
                "maria@email.com",
                "98765432100",
                new EnderecoRequest(
                        "sete",
                        "180",
                        "Passaré",
                        "Fortaleza",
                        "CE",
                        "",
                        "60862-180"
                )
        );
    }

    private ClienteResponse criarClienteResponse() {
        return new ClienteResponse(
                1L,
                "Maria",
                "maria@email.com",
                "98765432100",
                new EnderecoResponse(
                        "sete",
                        "180",
                        "Passaré",
                        "Fortaleza",
                        "CE",
                        "",
                        "60862-180"
                )
        );
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente(
                "Maria",
                "maria@email.com",
                "98765432100",
                new Endereco(
                        "sete",
                        "180",
                        "Passaré",
                        "Fortaleza",
                        "CE",
                        "",
                        "60862-180"
                )
        );

        try {
            Field idField = Cliente.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(cliente, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return cliente;
    }

    private ClienteUpdate criarClienteUpdate() {
        return new ClienteUpdate(
                "João",
                "joao@email.com",
                "12345678900",
                new EnderecoRequest(
                        "sete",
                        "180",
                        "Passaré",
                        "Fortaleza",
                        "CE",
                        "",
                        "60862-180"
                )
        );
    }

    private ClienteResponse criarClienteUpdateResponse() {
        return new ClienteResponse(
                1L,
                "João",
                "joao@email.com",
                "12345678900",
                new EnderecoResponse(
                        "sete",
                        "180",
                        "Passaré",
                        "Fortaleza",
                        "CE",
                        "",
                        "60862-180"
                )
        );
    }


    @Test
    void buscarEntidadePorId_clienteInativoOuInexistente_deveLancarExcecao() {
        when(clienteRepository.findByIdAndAtivoTrue(anyLong()))
                .thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> clienteService.buscarEntidadePorId(1L)
        );

        assertEquals("Cliente não encontrado ou inativo.", exception.getMessage());
    }

    @Test
    void buscarEntidadePorId_clienteAtivo_deveReornarCliente() {
        when(clienteRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.buscarEntidadePorId(1L);

        assertNotNull(resultado);
        assertEquals(cliente.getId(), resultado.getId());
        assertEquals(cliente.getEmail(), resultado.getEmail());
        assertTrue(resultado.isAtivo());

        verify(clienteRepository).findByIdAndAtivoTrue(1L);
    }

    @Test
    void cadastrar_emailCadastrado_deveLancarExcecao() {
        doThrow(new NegocioException("Email já cadastrado para cliente ativo."))
                .when(clienteDomainService)
                .validarEmailUnico(request.email());

        NegocioException exception = assertThrows(
                NegocioException.class,
                () -> clienteService.cadastrar(request)
        );

        assertEquals("Email já cadastrado para cliente ativo.", exception.getMessage());

        verify(clienteDomainService).validarEmailUnico(request.email());
        verify(clienteDomainService, never()).validarCpfUnico(any());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void cadastrar_CpfCadastrado_deveLancarExcecao() {
        doNothing().when(clienteDomainService).validarEmailUnico(request.email());

        doThrow(new NegocioException("CPF já cadastrado para cliente ativo."))
                .when(clienteDomainService)
                .validarCpfUnico(request.cpf());

        NegocioException exception = assertThrows(
                NegocioException.class,
                () -> clienteService.cadastrar(request)
        );

        assertEquals("CPF já cadastrado para cliente ativo.", exception.getMessage());

        verify(clienteDomainService).validarEmailUnico(request.email());
        verify(clienteDomainService).validarCpfUnico(request.cpf());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void cadastrar_dadosUnicos_deveCadastrarClienteClienteResponse() {
        doNothing().when(clienteDomainService).validarEmailUnico(anyString());
        doNothing().when(clienteDomainService).validarCpfUnico(anyString());

        when(clienteMapper.toEntity(request)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(clienteMapper.toResponse(cliente)).thenReturn(response);

        ClienteResponse resposta = clienteService.cadastrar(request);

        assertNotNull(resposta);
        assertEquals("Maria", resposta.nome());
        assertEquals("maria@email.com", resposta.email());

        verify(clienteDomainService).validarEmailUnico(request.email());
        verify(clienteDomainService).validarCpfUnico(request.cpf());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void bsucarPorId_ClienteInativoOuInexistente_deveLancarException() {
        when(clienteRepository.findByIdAndAtivoTrue(anyLong()))
                .thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> clienteService.buscarPorId(1L)
        );

        assertEquals("Cliente não encontrado ou inativo.", exception.getMessage());

        verify(clienteMapper, never()).toResponse(any());
    }

    @Test
    void buscarPorId_clienteAtivo_deveRetornarClienteResponse() {
        when(clienteRepository.findByIdAndAtivoTrue(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteMapper.toResponse(cliente)).thenReturn(response);

        ClienteResponse resposta = clienteService.buscarPorId(1L);

        assertNotNull(resposta);
        assertEquals(cliente.getId(), resposta.id());
        assertEquals(cliente.getCpf(), resposta.cpf());
    }

    @Test
    void atualizar_clienteInataivoOuInexistente_deveLancarexcecao() {
        when(clienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> clienteService.atualizar(1L, update)
        );

        assertEquals("Cliente não encontrado ou inativo.", ex.getMessage());
    }

    @Test
    void atualizar_emailDuplicado_deveLancarexcecao() {
        when(clienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(cliente));

        doThrow(new NegocioException("Email já cadastrado para cliente ativo."))
                .when(clienteDomainService)
                .validarEmailUnico(update.email());

        NegocioException exception = assertThrows(
                NegocioException.class,
                () -> clienteService.atualizar(cliente.getId(), update)
        );

        assertEquals("Email já cadastrado para cliente ativo.", exception.getMessage());

        verify(clienteDomainService).validarEmailUnico(update.email());
        verify(clienteDomainService, never()).validarCpfUnico(any());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void atualizar_cpfDuplicado_deveLancarexcecao() {
        when(clienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(cliente));

        doNothing().when(clienteDomainService).validarEmailUnico(update.email());

        doThrow(new NegocioException("CPF já cadastrado para cliente ativo."))
                .when(clienteDomainService)
                .validarCpfUnico(update.cpf());

        NegocioException exception = assertThrows(
                NegocioException.class,
                () -> clienteService.atualizar(cliente.getId(), update)
        );

        assertEquals("CPF já cadastrado para cliente ativo.", exception.getMessage());

        verify(clienteDomainService).validarCpfUnico(update.cpf());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void atualizar_dadosValidos_deveRetornarClienteResponseAtualizado() {
        when(clienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(cliente));

        doNothing().when(clienteDomainService).validarEmailUnico(update.email());
        doNothing().when(clienteDomainService).validarCpfUnico(update.cpf());

        doNothing().when(clienteMapper).updateToEntity(cliente, update);
        when(clienteMapper.toResponse(cliente)).thenReturn(updateResponse);

        ClienteResponse response = clienteService.atualizar(1L, update);

        assertNotNull(response);
        assertEquals("João", response.nome());
        assertEquals("joao@email.com", response.email());
        assertEquals("12345678900", response.cpf());

        verify(clienteRepository).save(cliente);
    }

    @Test
    void deveDeletarClienteComSucesso() {
        when(clienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(cliente));

        clienteService.deletar(1L);

        assertFalse(cliente.isAtivo());
    }

}

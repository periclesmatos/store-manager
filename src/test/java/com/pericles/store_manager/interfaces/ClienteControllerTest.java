package com.pericles.store_manager.interfaces;

import com.pericles.store_manager.application.cliente.dto.request.ClienteRequest;
import com.pericles.store_manager.application.cliente.dto.request.ClienteUpdate;
import com.pericles.store_manager.application.cliente.dto.request.EnderecoRequest;
import com.pericles.store_manager.application.cliente.dto.response.ClienteResponse;
import com.pericles.store_manager.application.cliente.dto.response.EnderecoResponse;
import com.pericles.store_manager.application.cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteRequest clienteRequest;
    private ClienteRequest clienteRequestEmailNull;
    private ClienteResponse clienteResponse;
    private ClienteUpdate clienteUpdate;

    @BeforeEach
    void setUp() {
        clienteRequest = criarClienteRequest();
        clienteResponse = criarClienteResponse();
        clienteUpdate = criarClienteUpdate();
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

    private ClienteRequest criarClienteEmailNull() {
        return new ClienteRequest(
                "Maria",
                null,
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

    @Test
    void criar_clienteEmailNull_deverRetornarErroValidacaoResposta() {

    }

    @Test
    void criar_clienteValido_deveRetornarCreatedComLocation() {
        when(clienteService.cadastrar(clienteRequest)).thenReturn(clienteResponse);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");

        ResponseEntity<ClienteResponse> resposta = clienteController.criar(clienteRequest, uriBuilder);
        URI expectedLocation = URI.create("http://localhost/clientes/1");

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(clienteResponse, resposta.getBody());
        assertEquals(expectedLocation, resposta.getHeaders().getLocation());

        verify(clienteService, times(1)).cadastrar(clienteRequest);
    }

}

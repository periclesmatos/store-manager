package com.pericles.store_manager.application.cliente.dto;

import com.pericles.store_manager.application.cliente.dto.request.ClienteRequest;
import com.pericles.store_manager.application.cliente.dto.request.EnderecoRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarClienteRequestValido() {
        EnderecoRequest endereco = new EnderecoRequest(
                "Rua A",
                "100",
                "Bairro B",
                "Cidade C",
                "CE",
                "",
                "60000-000"
        );

        ClienteRequest clienteRequest = new ClienteRequest(
                "Maria",
                "maria@email.com",
                "12345678900",
                endereco
        );

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);
        assertTrue(violations.isEmpty(), "Não deve ter violações para cliente válido");
    }

    @Test
    void deveDetectarCamposObrigatoriosInvalidos() {
        ClienteRequest clienteRequest = new ClienteRequest(
                "",
                "emailinvalido",
                "",
                null
        );

        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequest);

        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome do cliente é obrigatório")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email inválido")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("CPF é obrigatório")));
    }

}

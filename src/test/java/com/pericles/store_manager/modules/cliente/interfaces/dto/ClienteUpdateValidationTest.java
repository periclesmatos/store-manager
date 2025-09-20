package com.pericles.store_manager.modules.cliente.interfaces.dto;

import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteUpdate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClienteUpdateValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarEmailValido() {
        ClienteUpdate dto = new ClienteUpdate("João", "joao@email.com", null, null);
        Set<ConstraintViolation<ClienteUpdate>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveDetectarEmailInvalido() {
        ClienteUpdate dto = new ClienteUpdate(null, "email-invalido", null, null);
        Set<ConstraintViolation<ClienteUpdate>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email inválido")));
    }

    @Test
    void deveAceitarCamposNulos() {
        ClienteUpdate dto = new ClienteUpdate(null, null, null, null);
        Set<ConstraintViolation<ClienteUpdate>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

}

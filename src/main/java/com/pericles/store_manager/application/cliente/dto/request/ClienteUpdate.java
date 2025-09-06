package com.pericles.store_manager.application.cliente.dto.request;

import jakarta.validation.constraints.Email;

public record ClienteUpdate(
        String nome,
        @Email(message = "Email inválido")
        String email,
        String cpf,
        EnderecoRequest endereco
) {
}

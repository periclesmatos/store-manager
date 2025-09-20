package com.pericles.store_manager.modules.cliente.interfaces.dto.request;

import jakarta.validation.constraints.Email;

public record ClienteUpdate(
        String nome,
        @Email(message = "Email inválido")
        String email,
        String cpf,
        EnderecoRequest endereco
) {
}

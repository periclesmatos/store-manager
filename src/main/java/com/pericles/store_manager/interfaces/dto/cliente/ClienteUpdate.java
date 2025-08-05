package com.pericles.store_manager.interfaces.dto.cliente;

import jakarta.validation.constraints.Email;

public record ClienteUpdate(
        String nome,
        @Email
        String email,
        String cpf,
        EnderecoRequest endereco
) {
}

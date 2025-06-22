package com.pericles.store_manager.dto.cliente;

import jakarta.validation.constraints.Email;

public record ClienteUpdateDTO(
        String nome,
        @Email
        String email,
        String cpf,
        EnderecoRequest endereco
) {
}

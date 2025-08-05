package com.pericles.store_manager.interfaces.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank(message = "Nome5 do cliente é obrigatório")
        String nome,
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "CPF é obrigatório")
        String cpf,
        EnderecoRequest endereco
) {
}

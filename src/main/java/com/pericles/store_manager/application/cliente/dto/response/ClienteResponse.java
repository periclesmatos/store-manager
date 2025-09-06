package com.pericles.store_manager.application.cliente.dto.response;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        EnderecoResponse endereco
) {
}

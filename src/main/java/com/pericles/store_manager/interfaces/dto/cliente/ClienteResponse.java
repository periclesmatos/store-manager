package com.pericles.store_manager.interfaces.dto.cliente;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        EnderecoResponse endereco
) {
}

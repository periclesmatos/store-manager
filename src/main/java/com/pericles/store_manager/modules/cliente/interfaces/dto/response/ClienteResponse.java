package com.pericles.store_manager.modules.cliente.interfaces.dto.response;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        EnderecoResponse endereco
) {
}

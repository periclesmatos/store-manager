package com.pericles.store_manager.application.cliente.dto.response;

public record EnderecoResponse(
        String rua,
        String numero,
        String bairro,
        String cidade,
        String uf,
        String complemento,
        String cep
) {
}

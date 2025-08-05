package com.pericles.store_manager.interfaces.dto.cliente;

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

package com.pericles.store_manager.interfaces.dto.cliente;

public record EnderecoRequest(
        String rua,
        String numero,
        String bairro,
        String cidade,
        String uf,
        String complemento,
        String cep
) {
}

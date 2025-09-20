package com.pericles.store_manager.modules.cliente.interfaces.dto.response;

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

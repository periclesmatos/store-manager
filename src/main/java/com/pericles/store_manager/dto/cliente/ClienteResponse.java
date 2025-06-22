package com.pericles.store_manager.dto.cliente;

import com.pericles.store_manager.domain.Cliente;
import com.pericles.store_manager.domain.Endereco;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        Endereco endereco
) {
    public ClienteResponse(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getEndereco());
    }
}

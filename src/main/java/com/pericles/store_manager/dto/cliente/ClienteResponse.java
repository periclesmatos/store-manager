package com.pericles.store_manager.dto.cliente;

import com.pericles.store_manager.domain.Cliente;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String cpf
) {
    public ClienteResponse(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf());
    }
}

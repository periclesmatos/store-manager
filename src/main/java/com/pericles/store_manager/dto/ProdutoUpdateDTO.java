package com.pericles.store_manager.dto;

import jakarta.validation.constraints.NotNull;

public record ProdutoUpdateDTO(
        String nome,
        String descricao
) {
}

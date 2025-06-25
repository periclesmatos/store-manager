package com.pericles.store_manager.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "Nome do produto é obrigatório.")
        String nome,
        String descricao,
        @Positive @NotNull(message = "O preço deve ser maior que zero.")
        BigDecimal preco,
        @PositiveOrZero @NotNull(message = "A quantidade não pode ser negativa.")
        Integer estoque
) {
}

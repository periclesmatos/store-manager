package com.pericles.store_manager.application.produto.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AtualizarPreco(
        @NotNull(message = "O preço pe obrigatório.")
        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal preco
) {
}

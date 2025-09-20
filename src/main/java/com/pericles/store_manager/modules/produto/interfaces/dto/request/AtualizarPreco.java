package com.pericles.store_manager.modules.produto.interfaces.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AtualizarPreco(
        @NotNull(message = "O preço pe obrigatório.")
        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal preco
) {
}

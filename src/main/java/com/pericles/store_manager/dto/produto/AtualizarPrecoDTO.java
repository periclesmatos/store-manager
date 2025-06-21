package com.pericles.store_manager.dto.produto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AtualizarPrecoDTO(
        @NotNull
        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal preco
) {
}

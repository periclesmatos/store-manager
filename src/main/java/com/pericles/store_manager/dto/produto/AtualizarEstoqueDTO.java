package com.pericles.store_manager.dto.produto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AtualizarEstoqueDTO(
        @NotNull
        @PositiveOrZero(message = "A quantidade não pode ser negativa.")
        Integer estoque
) {
}

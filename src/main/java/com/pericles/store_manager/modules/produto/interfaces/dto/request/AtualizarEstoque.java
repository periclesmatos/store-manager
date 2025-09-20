package com.pericles.store_manager.modules.produto.interfaces.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AtualizarEstoque(
        @NotNull(message = "A quantidade é obrigatória.")
        @PositiveOrZero(message = "A quantidade não pode ser negativa.")
        Integer estoque
) {
}

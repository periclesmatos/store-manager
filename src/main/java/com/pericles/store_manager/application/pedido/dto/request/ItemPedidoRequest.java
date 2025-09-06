package com.pericles.store_manager.application.pedido.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequest(
        @NotNull
        Long produtoId,
        @NotNull @Positive
        Integer quantidade
) {
}

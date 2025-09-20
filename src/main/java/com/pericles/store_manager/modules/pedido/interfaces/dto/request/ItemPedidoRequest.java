package com.pericles.store_manager.modules.pedido.interfaces.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequest(
        @NotNull
        Long produtoId,
        @NotNull @Positive
        Integer quantidade
) {
}

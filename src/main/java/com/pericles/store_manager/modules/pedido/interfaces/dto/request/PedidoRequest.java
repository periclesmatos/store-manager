package com.pericles.store_manager.modules.pedido.interfaces.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequest(
        @NotNull
        Long clienteId,
        @NotEmpty
        List<ItemPedidoRequest> itens
) {
}

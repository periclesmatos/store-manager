package com.pericles.store_manager.application.pedido.dto.request;

import com.pericles.store_manager.domain.pedido.model.StatusPedido;

public record StatusPedidoRequest(
        Enum<StatusPedido> statusPedido
) {
}

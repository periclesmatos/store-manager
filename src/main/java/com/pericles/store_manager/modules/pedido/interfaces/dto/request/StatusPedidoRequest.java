package com.pericles.store_manager.modules.pedido.interfaces.dto.request;

import com.pericles.store_manager.modules.pedido.domain.model.StatusPedido;

public record StatusPedidoRequest(
        Enum<StatusPedido> statusPedido
) {
}

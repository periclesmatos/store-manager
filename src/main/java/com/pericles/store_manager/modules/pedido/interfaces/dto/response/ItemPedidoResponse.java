package com.pericles.store_manager.modules.pedido.interfaces.dto.response;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long id,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal precoTotal
) {
}

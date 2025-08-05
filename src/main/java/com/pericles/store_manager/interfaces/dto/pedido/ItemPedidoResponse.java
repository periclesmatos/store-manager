package com.pericles.store_manager.interfaces.dto.pedido;

import com.pericles.store_manager.domain.model.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoResponse(
        Long id,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal precoTotal
) {
    public ItemPedidoResponse(ItemPedido itemPedido) {
        this(itemPedido.getId(), itemPedido.getProduto().getNome(), itemPedido.getQuantidade(), itemPedido.getPrecoUnitario(), itemPedido.getPrecoTotal());
    }
}

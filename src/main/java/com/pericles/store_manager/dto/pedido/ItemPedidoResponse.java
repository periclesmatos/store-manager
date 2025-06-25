package com.pericles.store_manager.dto.pedido;

import com.pericles.store_manager.domain.ItemPedido;

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

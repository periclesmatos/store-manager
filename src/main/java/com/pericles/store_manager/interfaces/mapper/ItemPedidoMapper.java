package com.pericles.store_manager.interfaces.mapper;

import com.pericles.store_manager.domain.model.ItemPedido;
import com.pericles.store_manager.interfaces.dto.pedido.ItemPedidoResponse;

public class ItemPedidoMapper {

    public static ItemPedidoResponse toResponse(ItemPedido itemPedido) {
        return new ItemPedidoResponse(
                itemPedido.getId(),
                itemPedido.getProduto().getNome(),
                itemPedido.getQuantidade(),
                itemPedido.getPrecoUnitario(),
                itemPedido.getPrecoTotal()
        );
    }

}

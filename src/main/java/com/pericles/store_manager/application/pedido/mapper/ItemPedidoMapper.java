package com.pericles.store_manager.application.pedido.mapper;

import com.pericles.store_manager.domain.pedido.model.ItemPedido;
import com.pericles.store_manager.application.pedido.dto.response.ItemPedidoResponse;

public interface ItemPedidoMapper {
    ItemPedidoResponse toResponse(ItemPedido itemPedido);
}

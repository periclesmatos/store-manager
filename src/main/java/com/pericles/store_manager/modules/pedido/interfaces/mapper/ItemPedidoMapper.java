package com.pericles.store_manager.modules.pedido.interfaces.mapper;

import com.pericles.store_manager.modules.pedido.domain.model.ItemPedido;
import com.pericles.store_manager.modules.pedido.interfaces.dto.response.ItemPedidoResponse;

public interface ItemPedidoMapper {
    ItemPedidoResponse toResponse(ItemPedido itemPedido);
}

package com.pericles.store_manager.modules.pedido.interfaces.mapper;

import com.pericles.store_manager.modules.pedido.domain.model.Pedido;
import com.pericles.store_manager.modules.pedido.interfaces.dto.response.PedidoResponse;

public interface PedidoMapper {
    PedidoResponse toResponse(Pedido pedido);
}

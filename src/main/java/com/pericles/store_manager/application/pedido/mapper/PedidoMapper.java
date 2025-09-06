package com.pericles.store_manager.application.pedido.mapper;

import com.pericles.store_manager.domain.pedido.model.Pedido;
import com.pericles.store_manager.application.pedido.dto.response.PedidoResponse;

public interface PedidoMapper {
    PedidoResponse toResponse(Pedido pedido);
}

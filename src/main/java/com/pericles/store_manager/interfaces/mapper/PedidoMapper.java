package com.pericles.store_manager.interfaces.mapper;

import com.pericles.store_manager.domain.model.Pedido;
import com.pericles.store_manager.interfaces.dto.pedido.PedidoResponse;

public class PedidoMapper {

    public static PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getNome(),
                pedido.getStatusPedido(),
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                pedido.getItens().stream()
                        .map(ItemPedidoMapper::toResponse)
                        .toList()
        );
    }

}

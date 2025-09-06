package com.pericles.store_manager.application.pedido.mapper;

import com.pericles.store_manager.application.pedido.dto.response.PedidoResponse;
import com.pericles.store_manager.domain.pedido.model.Pedido;
import org.springframework.stereotype.Service;

@Service
public class PedidoMapperImpl implements PedidoMapper {

    private final ItemPedidoMapper itemPedidoMapper;

    public PedidoMapperImpl(ItemPedidoMapper itemPedidoMapper) {
        this.itemPedidoMapper = itemPedidoMapper;
    }

    public PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getNome(),
                pedido.getStatusPedido(),
                pedido.getValorTotal(),
                pedido.getDataPedido(),
                pedido.getItens().stream()
                        .map(itemPedidoMapper::toResponse)
                        .toList()
        );
    }

}

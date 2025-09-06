package com.pericles.store_manager.application.pedido.mapper;

import com.pericles.store_manager.application.pedido.dto.response.ItemPedidoResponse;
import com.pericles.store_manager.domain.pedido.model.ItemPedido;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoMapperImpl implements ItemPedidoMapper {

    public ItemPedidoResponse toResponse(ItemPedido itemPedido) {
        return new ItemPedidoResponse(
                itemPedido.getId(),
                itemPedido.getProduto().getNome(),
                itemPedido.getQuantidade(),
                itemPedido.getPrecoUnitario(),
                itemPedido.getPrecoTotal()
        );
    }

}

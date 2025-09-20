package com.pericles.store_manager.modules.pedido.interfaces.mapper;

import com.pericles.store_manager.modules.pedido.interfaces.dto.response.ItemPedidoResponse;
import com.pericles.store_manager.modules.pedido.domain.model.ItemPedido;
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

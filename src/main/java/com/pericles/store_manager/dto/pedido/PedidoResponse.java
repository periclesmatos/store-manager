package com.pericles.store_manager.dto.pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pericles.store_manager.domain.Pedido;
import com.pericles.store_manager.domain.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        String nomeCliente,
        StatusPedido statusPedido,
        BigDecimal valorTotal,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataPedido,

        List<ItemPedidoResponse> itens
) {
    public PedidoResponse(Pedido pedido) {
        this(
            pedido.getId(),
            pedido.getCliente().getNome(),
            pedido.getStatusPedido(),
            pedido.getValorTotal(),
            pedido.getDataPedido(),
            pedido.getItens().stream()
                    .map(ItemPedidoResponse::new)
                    .toList()
        );
    }
}

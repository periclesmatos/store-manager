package com.pericles.store_manager.application.pedido.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pericles.store_manager.domain.pedido.model.StatusPedido;

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
}

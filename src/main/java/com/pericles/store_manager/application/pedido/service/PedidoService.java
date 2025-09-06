package com.pericles.store_manager.application.pedido.service;

import com.pericles.store_manager.application.pedido.dto.request.ItemPedidoRequest;
import com.pericles.store_manager.application.pedido.dto.request.PedidoRequest;
import com.pericles.store_manager.application.pedido.dto.response.PedidoResponse;
import com.pericles.store_manager.domain.pedido.model.Pedido;
import com.pericles.store_manager.domain.pedido.model.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
    Pedido buscarEntidadePorId(Long id);
    PedidoResponse cadastrar(PedidoRequest request);
    PedidoResponse buscarPorId(Long id);
    PedidoResponse atualizarItens(Long pedidoId, List<ItemPedidoRequest> requests);
    PedidoResponse modificarStatus(Long id, StatusPedido statusPedido);
    Page<PedidoResponse> listarComFiltro(StatusPedido statusPedido, Long clienteId, LocalDateTime dataInicio, LocalDateTime dataFinal, Pageable pageable);
}

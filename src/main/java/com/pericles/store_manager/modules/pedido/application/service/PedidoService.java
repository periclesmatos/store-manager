package com.pericles.store_manager.modules.pedido.application.service;

import com.pericles.store_manager.modules.pedido.interfaces.dto.request.ItemPedidoRequest;
import com.pericles.store_manager.modules.pedido.interfaces.dto.request.PedidoRequest;
import com.pericles.store_manager.modules.pedido.interfaces.dto.response.PedidoResponse;
import com.pericles.store_manager.modules.pedido.domain.model.Pedido;
import com.pericles.store_manager.modules.pedido.domain.model.StatusPedido;
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

package com.pericles.store_manager.application.service;

import com.pericles.store_manager.domain.model.*;
import com.pericles.store_manager.interfaces.dto.pedido.ItemPedidoRequest;
import com.pericles.store_manager.interfaces.dto.pedido.PedidoRequest;
import com.pericles.store_manager.interfaces.dto.pedido.PedidoResponse;
import com.pericles.store_manager.infrastructure.exception.RecursoNaoEncontradoException;
import com.pericles.store_manager.domain.repository.PedidoRepository;
import com.pericles.store_manager.interfaces.mapper.PedidoMapper;
import com.pericles.store_manager.interfaces.specification.PedidoSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService, ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    @Transactional(readOnly = true)
    public Pedido buscarEntidadePorId(Long id) {
        return pedidoRepository
                .findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com ID " + id + " não encontrado."));
    }

    @Transactional
    public PedidoResponse cadastrar(PedidoRequest request) {
        Cliente cliente = clienteService.buscarEntidadePorId(request.clienteId());
        Pedido pedido = new Pedido(cliente);

        for (ItemPedidoRequest item : request.itens()) {
            Produto produto = produtoService.buscarEntidadePorId(item.produtoId());
            pedido.adicionarItem(produto, item.quantidade());
        }

        pedido.calcularTotal();
        pedidoRepository.save(pedido);
        return PedidoMapper.toResponse(pedido);
    }

    @Transactional(readOnly = true)
    public Page<PedidoResponse> listarComFiltro(
            StatusPedido statusPedido,
            Long clienteId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Pageable pageable
    ) {
        Specification<Pedido> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (statusPedido != null) {
            spec = spec.and(PedidoSpecifications.comStatus(statusPedido));
        }

        if (clienteId != null) {
            spec = spec.and(PedidoSpecifications.comClienteId(clienteId));
        }

        if (dataInicio != null || dataFim != null) {
            spec = spec.and(PedidoSpecifications.comDataPedidoEntre(dataInicio, dataFim));
        }

        return pedidoRepository.findAll(spec, pageable).map(PedidoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = buscarEntidadePorId(id);
        return PedidoMapper.toResponse(pedido);
    }

    @Transactional
    public void atualizarItens(Long pedidoId, List<ItemPedidoRequest> requests) {
        Pedido pedido = buscarEntidadePorId(pedidoId);

        for (ItemPedido item : pedido.getItens()) {
            produtoService.reabastecerEstoque(item.getProduto().getId(), item.getQuantidade());
        }

        pedido.getItens().clear();

        for (ItemPedidoRequest item : requests) {
            Produto produto = produtoService.buscarEntidadePorId(item.produtoId());
            pedido.adicionarItem(produto, item.quantidade());
        }

        pedido.calcularTotal();
    }

    @Transactional
    public void processarAcao(Long pedidoId, AcaoPedido acaoPedido) {
        Pedido pedido = buscarEntidadePorId(pedidoId);
        acaoPedido.executar(pedido);
    }

}

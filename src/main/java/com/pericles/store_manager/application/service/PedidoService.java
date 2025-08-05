package com.pericles.store_manager.application.service;

import com.pericles.store_manager.domain.model.*;
import com.pericles.store_manager.interfaces.dto.pedido.ItemPedidoRequest;
import com.pericles.store_manager.interfaces.dto.pedido.PedidoRequest;
import com.pericles.store_manager.interfaces.dto.pedido.PedidoResponse;
import com.pericles.store_manager.infrastructure.exception.RecursoNaoEncontradoException;
import com.pericles.store_manager.domain.repository.PedidoRepository;
import com.pericles.store_manager.interfaces.specification.PedidoSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public Pedido registrarPedido(PedidoRequest pedidoRequest) {
        Cliente cliente = clienteService.buscarClienteAtivoPorId(pedidoRequest.clienteId());
        Pedido pedido = new Pedido(cliente);

        for (ItemPedidoRequest itemPedidoRequest : pedidoRequest.itens()) {
            Produto produto = produtoService.buscarProdutoAtivoPorId(itemPedidoRequest.produtoId());
            ItemPedido item = new ItemPedido(pedido, produto, itemPedidoRequest.quantidade());
            produtoService.debitarEstoque(produto.getId(), itemPedidoRequest.quantidade());
            pedido.adicionarItem(item);
        }

        pedido.calcularTotal();
        pedidoRepository.save(pedido);
        return pedido;
    }

    @Transactional(readOnly = true)
    public Page<PedidoResponse> listarPedidosComFiltro(
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

        return pedidoRepository.findAll(spec, pageable).map(PedidoResponse::new);
    }

    @Transactional(readOnly = true)
    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com ID " + id + " não encontrado."));
    }

    @Transactional
    public void atualizarItens(Long pedidoId, List<ItemPedidoRequest> itensRequest) {
        Pedido pedido = buscarPedidoPorId(pedidoId);

        for (ItemPedido item : pedido.getItens()) {
            produtoService.reabastecerEstoque(item.getProduto().getId(), item.getQuantidade());
        }

        pedido.getItens().clear();

        for (ItemPedidoRequest itemRequest : itensRequest) {
            Produto produto = produtoService.buscarProdutoAtivoPorId(itemRequest.produtoId());
            produtoService.debitarEstoque(produto.getId(), itemRequest.quantidade());
            ItemPedido item = new ItemPedido(pedido, produto, itemRequest.quantidade());
            pedido.adicionarItem(item);
        }

        pedido.calcularTotal();
    }

    @Transactional
    public void processarAcao(Long pedidoId, AcaoPedido acaoPedido) {
        Pedido pedido = buscarPedidoPorId(pedidoId);
        acaoPedido.executar(pedido);
    }

}

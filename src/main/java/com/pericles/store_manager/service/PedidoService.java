package com.pericles.store_manager.service;

import com.pericles.store_manager.domain.*;
import com.pericles.store_manager.dto.pedido.ItemPedidoRequest;
import com.pericles.store_manager.dto.pedido.PedidoRequest;
import com.pericles.store_manager.dto.pedido.PedidoResponse;
import com.pericles.store_manager.exception.RecursoNaoEncontradoException;
import com.pericles.store_manager.repository.PedidoRepository;
import com.pericles.store_manager.specification.PedidoSpecifications;
import jakarta.persistence.EntityNotFoundException;
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
            produtoService.debitarEstoque(produto.getId(), itemPedidoRequest.quantidade());
            ItemPedido item = new ItemPedido(pedido, produto, itemPedidoRequest.quantidade());
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

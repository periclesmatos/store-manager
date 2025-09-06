package com.pericles.store_manager.application.pedido.service;

import com.pericles.store_manager.application.cliente.service.ClienteService;
import com.pericles.store_manager.application.pedido.dto.request.ItemPedidoRequest;
import com.pericles.store_manager.application.pedido.dto.request.PedidoRequest;
import com.pericles.store_manager.application.pedido.dto.response.PedidoResponse;
import com.pericles.store_manager.application.pedido.mapper.PedidoMapper;
import com.pericles.store_manager.application.produto.service.ProdutoService;
import com.pericles.store_manager.domain.cliente.model.Cliente;
import com.pericles.store_manager.domain.cliente.specification.ClienteIdSpecification;
import com.pericles.store_manager.domain.pedido.model.StatusPedido;
import com.pericles.store_manager.domain.pedido.model.ItemPedido;
import com.pericles.store_manager.domain.pedido.model.Pedido;
import com.pericles.store_manager.domain.pedido.repository.PedidoRepository;
import com.pericles.store_manager.domain.pedido.specification.DataPedidoSpecification;
import com.pericles.store_manager.domain.pedido.specification.StatusPedidoSpecification;
import com.pericles.store_manager.domain.produto.model.Produto;
import com.pericles.store_manager.infrastructure.exception.NegocioException;
import com.pericles.store_manager.infrastructure.exception.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements  PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, ClienteService clienteService, ProdutoService produtoService, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.pedidoMapper = pedidoMapper;
    }

    @Transactional(readOnly = true)
    public Pedido buscarEntidadePorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com ID " + id + " não encontrado."));
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
        return pedidoMapper.toResponse(pedido);
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = buscarEntidadePorId(id);
        return pedidoMapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponse modificarStatus(Long id, StatusPedido statusPedido) {
        Pedido pedido = buscarEntidadePorId(id);
        validarTransicao(pedido.getStatusPedido(), statusPedido);
        pedido.modificarStatusPedido(statusPedido);
        return pedidoMapper.toResponse(pedido);
    }

    @Transactional(readOnly = true)
    public Page<PedidoResponse> listarComFiltro(
            StatusPedido statusPedido,
            Long clienteId,
            LocalDateTime dataInicio,
            LocalDateTime dataFinal,
            Pageable pageable
    ) {
        Specification<Pedido> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (statusPedido != null) {
            spec = spec.and(new StatusPedidoSpecification(statusPedido).toSpecification());
        }

        if (clienteId != null) {
            spec = spec.and(new ClienteIdSpecification(clienteId).toSpecification());
        }

        if (dataInicio != null || dataFinal != null) {
            spec = spec.and(new DataPedidoSpecification(dataInicio, dataFinal).toSpecification());
        }

        return pedidoRepository.findAll(spec, pageable).map(pedidoMapper::toResponse);
    }


    @Transactional
    public PedidoResponse atualizarItens(Long pedidoId, List<ItemPedidoRequest> requests) {
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

        return pedidoMapper.toResponse(pedido);
    }

    private void validarTransicao(StatusPedido statusAtual, StatusPedido statusNovo) {
        if (statusAtual == StatusPedido.ENTREGUE || statusAtual == StatusPedido.CANCELADO || statusAtual == StatusPedido.RETIRADO) {
            throw new NegocioException("Não é possivel alterar pedido finalizado ou cancelado");
        }

        switch (statusNovo) {
            case PROCESSANDO:
                if (statusAtual != StatusPedido.AGUARDANDO_PAGAMENTO) {
                    throw new NegocioException("Só é possível processar pedidos que estão aguardando pagamento.");
                }
                break;

            case ENVIADO:
                if (statusAtual != StatusPedido.PROCESSANDO) {
                    throw new NegocioException("Só é possível enviar pedidos que estão em processamento.");
                }
                break;

            case RETIRADO:
                if (statusAtual != StatusPedido.AGUARDANDO_PAGAMENTO) {
                    throw new NegocioException("Só é possível retirar pedidos que estão aguardando pagamento.");
                }

            case ENTREGUE:
                if (statusAtual != StatusPedido.ENVIADO) {
                    throw new NegocioException("Só é possível entregar pedidos que já foram enviados.");
                }
                break;

            default:
                throw new NegocioException("Transição inválida de status.");
        }

    }

}

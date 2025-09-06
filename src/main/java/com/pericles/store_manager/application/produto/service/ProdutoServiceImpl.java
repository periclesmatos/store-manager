package com.pericles.store_manager.application.produto.service;

import com.pericles.store_manager.application.produto.dto.request.AtualizarEstoque;
import com.pericles.store_manager.application.produto.dto.request.AtualizarPreco;
import com.pericles.store_manager.application.produto.dto.request.ProdutoRequest;
import com.pericles.store_manager.application.produto.dto.request.ProdutoUpdate;
import com.pericles.store_manager.application.produto.dto.response.ProdutoResponse;
import com.pericles.store_manager.domain.produto.model.Produto;
import com.pericles.store_manager.domain.produto.repository.ProdutoRepository;
import com.pericles.store_manager.infrastructure.exception.RecursoNaoEncontradoException;
import com.pericles.store_manager.application.produto.mapper.ProdutoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository,  ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Produto buscarEntidadePorId(Long id) {
        return produtoRepository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID " + id + " inativo ou não encontrado."));
    }

    @Override
    public ProdutoResponse cadastrar(ProdutoRequest request) {
        Produto produto = produtoMapper.toEntity(request);
        produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = buscarEntidadePorId(id);
        return produtoMapper.toResponse(produto);
    }

    @Override
    public ProdutoResponse atualizarProduto(Long id, ProdutoUpdate produtoUpdate) {
        Produto produto = buscarEntidadePorId(id);
        produtoMapper.updateToEntity(produto, produtoUpdate);
        produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }

    @Override
    public ProdutoResponse modificarPreco(Long id, AtualizarPreco atualizarPreco) {
        Produto produto = buscarEntidadePorId(id);
        produto.modificarPreco(atualizarPreco.preco());
        produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }

    @Override
    public ProdutoResponse modificarEstoque(Long id, AtualizarEstoque atualizarEstoque) {
        Produto produto = buscarEntidadePorId(id);
        produto.atualizarEstoque(atualizarEstoque.estoque());
        produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProdutoResponse> listar(Pageable pageable) {
        return produtoRepository.findAllByAtivoTrue(pageable).map(produtoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProdutoResponse> filtrarPorNome(String nome, Pageable pageable) {
        if (nome != null && !nome.isBlank()) {
            return produtoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, pageable)
                    .map(produtoMapper::toResponse);
        }
        return listar(pageable);
    }

    @Override
    public void debitarEstoque(Long produtoId, int quantidade) {
        Produto produto = buscarEntidadePorId(produtoId);
        produto.debitarEstoque(quantidade);
        produtoRepository.save(produto);
    }

    @Override
    public void reabastecerEstoque(Long produtoId, int quantidade) {
        Produto produto = buscarEntidadePorId(produtoId);
        produto.reabastecerEstoque(quantidade);
        produtoRepository.save(produto);
    }

    @Override
    public void deletar(Long id) {
        Produto produto = buscarEntidadePorId(id);
        produto.desativar();
        produtoRepository.save(produto);
    }
}

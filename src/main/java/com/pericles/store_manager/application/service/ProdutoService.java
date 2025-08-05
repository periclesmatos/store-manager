package com.pericles.store_manager.application.service;

import com.pericles.store_manager.domain.model.Produto;
import com.pericles.store_manager.infrastructure.exception.RecursoNaoEncontradoException;
import com.pericles.store_manager.domain.repository.ProdutoRepository;
import com.pericles.store_manager.interfaces.dto.produto.*;
import com.pericles.store_manager.interfaces.mapper.ProdutoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public Produto buscarEntidadePorId(Long id) {
        return produtoRepository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID " + id + "  inativo ou não encontrado."));
    }

    @Transactional
    public ProdutoResponse cadastrar(ProdutoRequest request) {
        Produto produto = ProdutoMapper.toEntity(request);
        produtoRepository.save(produto);
        return ProdutoMapper.toResponse(produto);
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponse> listar(Pageable pageable) {
        return produtoRepository.findAllByAtivoTrue(pageable).map(ProdutoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = buscarEntidadePorId(id);
        return ProdutoMapper.toResponse(produto);
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponse> filtrarPorNome(String nome, Pageable pageable) {
        if (nome != null && !nome.isBlank()) {
            return produtoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, pageable).map(ProdutoMapper::toResponse);
        }
        return produtoRepository.findAllByAtivoTrue(pageable).map(ProdutoMapper::toResponse);
    }

    @Transactional
    public ProdutoResponse atualizarProduto(Long id, ProdutoUpdate produtoUpdate) {
        Produto produto = buscarEntidadePorId(id);
        produto.atualizarProduto(produtoUpdate);
        return ProdutoMapper.toResponse(produto);
    }

    @Transactional
    public ProdutoResponse modificarPreco(Long id, AtualizarPreco atualizarPreco) {
        Produto produto = buscarEntidadePorId(id);
        produto.modificarPreco(atualizarPreco.preco());
        return ProdutoMapper.toResponse(produto);
    }

    @Transactional
    public ProdutoResponse modificarEstoque(Long id, AtualizarEstoque atualizarEstoque) {
        Produto produto = buscarEntidadePorId(id);
        produto.modificarEstoque(atualizarEstoque.estoque());
        return ProdutoMapper.toResponse(produto);
    }

    @Transactional
    public void debitarEstoque(Long produtoId, int quantidade) {
        Produto produto = buscarEntidadePorId(produtoId);
        produto.debitarEstoque(quantidade);
    }

    @Transactional
    public void reabastecerEstoque(Long produtoId, int quantidade) {
        Produto produto = buscarEntidadePorId(produtoId);
        produto.reabastecerEstoque(quantidade);
    }

    @Transactional
    public void deletar(Long id) {
        var produto = buscarEntidadePorId(id);
        produto.desativar();
    }

}

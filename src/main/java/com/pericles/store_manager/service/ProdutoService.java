package com.pericles.store_manager.service;

import com.pericles.store_manager.domain.Produto;
import com.pericles.store_manager.dto.produto.*;
import com.pericles.store_manager.exception.EstoqueInsuficienteException;
import com.pericles.store_manager.exception.RecursoNaoEncontradoException;
import com.pericles.store_manager.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto registrarProduto(ProdutoRequest produtoRequest) {
        var produto = new Produto(produtoRequest);
        produtoRepository.save(produto);
        return produto;
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponse> listarProdutosAtivos(Pageable pageable) {
        return produtoRepository.findAllByAtivoTrue(pageable).map(ProdutoResponse::new);
    }

    @Transactional(readOnly = true)
    public Produto buscarProdutoAtivoPorId(Long id) {
        return produtoRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID " + id + "  inativo ou não encontrado."));
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponse> filtrarProdutosPorNome(String nome, Pageable pageable) {
        if (nome != null && !nome.isBlank()) {
            return produtoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, pageable).map(ProdutoResponse::new);
        }
        return produtoRepository.findAllByAtivoTrue(pageable).map(ProdutoResponse::new);
    }

    @Transactional
    public Produto atualizarProduto(Long id, ProdutoUpdateDTO produtoUpdate) {
        var produto = buscarProdutoAtivoPorId(id);
        produto.atualizarProduto(produtoUpdate);
        return produto;
    }

    @Transactional
    public Produto modificarPreco(Long id, AtualizarPrecoDTO atualizarPreco) {
        var produto = buscarProdutoAtivoPorId(id);
        produto.modificarPreco(atualizarPreco.preco());
        return produto;
    }

    @Transactional
    public Produto modificarEstoque(Long id, AtualizarEstoqueDTO atualizarEstoque) {
        var produto = buscarProdutoAtivoPorId(id);
        produto.modificarEstoque(atualizarEstoque.estoque());
        return produto;
    }

    @Transactional
    public void debitarEstoque(Long produtoId, int quantidade) {
        Produto produto = buscarProdutoAtivoPorId(produtoId);

        if (produto.getEstoque() < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        produto.debitarEstoque(quantidade);
    }

    @Transactional
    public void reabastecerEstoque(Long produtoId, int quantidade) {
        Produto produto = buscarProdutoAtivoPorId(produtoId);
        produto.reabastecerEstoque(quantidade);
    }

    @Transactional
    public void deletarProduto(Long id) {
        var produto = buscarProdutoAtivoPorId(id);
        produto.deletarProduto();
    }

}

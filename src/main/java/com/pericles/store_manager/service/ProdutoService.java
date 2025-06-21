package com.pericles.store_manager.service;

import com.pericles.store_manager.domain.Produto;
import com.pericles.store_manager.dto.*;
import com.pericles.store_manager.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto registrarProduto(ProdutoRequest produtoRequest) {
        var produto = new Produto(produtoRequest);
        produtoRepository.save(produto);
        return produto;
    }

    public Page<ProdutoResponse> listarProdutosAtivos(Pageable pageable) {
        return produtoRepository.findAllByAtivoTrue(pageable).map(ProdutoResponse::new);
    }

    public Produto buscarProduto(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }

    public Produto atualizarProduto(Long id, ProdutoUpdateDTO produtoUpdate) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
        produto.atualizarProduto(produtoUpdate);
        return produto;
    }

    public Produto modificarPreco(Long id, AtualizarPrecoDTO atualizarPreco) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
        produto.modificarPreco(atualizarPreco.preco());
        return produto;
    }

    public Produto modificarEstoque(Long id, AtualizarEstoqueDTO atualizarEstoque) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
        produto.modificarEstoque(atualizarEstoque.estoque());
        return produto;
    }

    public void deletarProduto(Long id) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
        produto.deletarProduto();
    }

}

package com.pericles.store_manager.modules.produto.application.service;

import com.pericles.store_manager.modules.produto.interfaces.dto.request.AtualizarEstoque;
import com.pericles.store_manager.modules.produto.interfaces.dto.request.AtualizarPreco;
import com.pericles.store_manager.modules.produto.interfaces.dto.request.ProdutoRequest;
import com.pericles.store_manager.modules.produto.interfaces.dto.request.ProdutoUpdate;
import com.pericles.store_manager.modules.produto.interfaces.dto.response.ProdutoResponse;
import com.pericles.store_manager.modules.produto.domain.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoService {
    Produto buscarEntidadePorId(Long id);
    ProdutoResponse cadastrar(ProdutoRequest request);
    ProdutoResponse buscarPorId(Long id);
    ProdutoResponse atualizarProduto(Long id, ProdutoUpdate produtoUpdate);
    ProdutoResponse modificarPreco(Long id, AtualizarPreco atualizarPreco);
    ProdutoResponse modificarEstoque(Long id, AtualizarEstoque atualizarEstoque);
    Page<ProdutoResponse> listar(Pageable pageable);
    Page<ProdutoResponse> filtrarPorNome(String nome, Pageable pageable);
    void debitarEstoque(Long produtoId, int quantidade);
    void reabastecerEstoque(Long produtoId, int quantidade);
    void deletar(Long id);
}

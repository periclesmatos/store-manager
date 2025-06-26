package com.pericles.store_manager.dto.produto;

import com.pericles.store_manager.domain.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque
) {
    public ProdutoResponse(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getEstoque());
    }
}

package com.pericles.store_manager.interfaces.dto.produto;

import com.pericles.store_manager.domain.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque
) {
}

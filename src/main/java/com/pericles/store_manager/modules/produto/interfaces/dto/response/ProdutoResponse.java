package com.pericles.store_manager.modules.produto.interfaces.dto.response;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque
) {
}

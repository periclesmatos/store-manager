package com.pericles.store_manager.interfaces.mapper;

import com.pericles.store_manager.domain.model.Produto;
import com.pericles.store_manager.interfaces.dto.produto.ProdutoRequest;
import com.pericles.store_manager.interfaces.dto.produto.ProdutoResponse;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoRequest dto) {
        return new Produto(
                dto.nome(),
                dto.descricao(),
                dto.preco(),
                dto.estoque()
        );
    }

    public static ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque()
        );
    }

}

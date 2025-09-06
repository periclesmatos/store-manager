package com.pericles.store_manager.application.produto.mapper;

import com.pericles.store_manager.application.produto.dto.request.ProdutoRequest;
import com.pericles.store_manager.application.produto.dto.request.ProdutoUpdate;
import com.pericles.store_manager.application.produto.dto.response.ProdutoResponse;
import com.pericles.store_manager.domain.produto.model.Produto;

public class ProdutoMapperImpl implements ProdutoMapper {

    public Produto toEntity(ProdutoRequest dto) {
        return new Produto(
                dto.nome(),
                dto.descricao(),
                dto.preco(),
                dto.estoque()
        );
    }

    public ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque()
        );
    }

    public void updateToEntity(Produto produto, ProdutoUpdate dto) {
        if (dto.nome() != null) {
            produto.atualizarNome(dto.nome());
        }

        if (dto.descricao() != null) {
            produto.atualizarDescricao(dto.descricao());
        }
    }

}

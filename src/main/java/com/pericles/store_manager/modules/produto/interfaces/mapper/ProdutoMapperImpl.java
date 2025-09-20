package com.pericles.store_manager.modules.produto.interfaces.mapper;

import com.pericles.store_manager.modules.produto.interfaces.dto.request.ProdutoRequest;
import com.pericles.store_manager.modules.produto.interfaces.dto.request.ProdutoUpdate;
import com.pericles.store_manager.modules.produto.interfaces.dto.response.ProdutoResponse;

import org.springframework.stereotype.Component;

import com.pericles.store_manager.modules.produto.domain.model.Produto;

@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public Produto toEntity(ProdutoRequest dto) {
        return new Produto(
                dto.nome(),
                dto.descricao(),
                dto.preco(),
                dto.estoque()
        );
    }

    @Override
    public ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque()
        );
    }

    @Override
    public void updateToEntity(Produto produto, ProdutoUpdate dto) {
        if (dto.nome() != null) {
            produto.atualizarNome(dto.nome());
        }

        if (dto.descricao() != null) {
            produto.atualizarDescricao(dto.descricao());
        }
    }

}

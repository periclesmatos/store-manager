package com.pericles.store_manager.application.produto.mapper;

import com.pericles.store_manager.application.produto.dto.request.ProdutoUpdate;
import com.pericles.store_manager.domain.produto.model.Produto;
import com.pericles.store_manager.application.produto.dto.request.ProdutoRequest;
import com.pericles.store_manager.application.produto.dto.response.ProdutoResponse;

public interface ProdutoMapper {
    Produto toEntity(ProdutoRequest dto);
    ProdutoResponse toResponse(Produto produto);
    void updateToEntity(Produto produto, ProdutoUpdate dto);
}

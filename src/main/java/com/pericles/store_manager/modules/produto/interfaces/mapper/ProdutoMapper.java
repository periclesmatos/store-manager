package com.pericles.store_manager.modules.produto.interfaces.mapper;

import com.pericles.store_manager.modules.produto.interfaces.dto.request.ProdutoUpdate;
import com.pericles.store_manager.modules.produto.domain.model.Produto;
import com.pericles.store_manager.modules.produto.interfaces.dto.request.ProdutoRequest;
import com.pericles.store_manager.modules.produto.interfaces.dto.response.ProdutoResponse;

public interface ProdutoMapper {
    Produto toEntity(ProdutoRequest dto);
    ProdutoResponse toResponse(Produto produto);
    void updateToEntity(Produto produto, ProdutoUpdate dto);
}

package com.pericles.store_manager.domain.produto.repository;

import com.pericles.store_manager.domain.produto.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProdutoRepository {
    Produto save(Produto produto);
    Page<Produto> findAllByAtivoTrue(Pageable pageable);
    Page<Produto> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable pageable);
    Optional<Produto> findByIdAndAtivoTrue(Long id);
}

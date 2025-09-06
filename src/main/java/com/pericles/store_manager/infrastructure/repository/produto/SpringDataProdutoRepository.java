package com.pericles.store_manager.infrastructure.repository.produto;

import com.pericles.store_manager.domain.produto.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findAllByAtivoTrue(Pageable pageable);
    Page<Produto> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable pageable);
    Optional<Produto> findByIdAndAtivoTrue(Long id);
}

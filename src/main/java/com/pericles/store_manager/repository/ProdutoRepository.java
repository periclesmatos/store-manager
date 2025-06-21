package com.pericles.store_manager.repository;

import com.pericles.store_manager.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findAllByAtivoTrue(Pageable pageable);
}

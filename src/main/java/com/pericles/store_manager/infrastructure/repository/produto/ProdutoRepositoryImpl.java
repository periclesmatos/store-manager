package com.pericles.store_manager.infrastructure.repository.produto;

import com.pericles.store_manager.domain.produto.model.Produto;
import com.pericles.store_manager.domain.produto.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final SpringDataProdutoRepository springDataProdutoRepository;

    public ProdutoRepositoryImpl(SpringDataProdutoRepository springDataProdutoRepository) {
        this.springDataProdutoRepository = springDataProdutoRepository;
    }

    @Override
    public Produto save(Produto produto) {
        return springDataProdutoRepository.save(produto);
    }

    @Override
    public Page<Produto> findAllByAtivoTrue(Pageable pageable) {
        return springDataProdutoRepository.findAllByAtivoTrue(pageable);
    }

    @Override
    public Page<Produto> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable pageable) {
        return springDataProdutoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, pageable);
    }

    @Override
    public Optional<Produto> findByIdAndAtivoTrue(Long id) {
        return springDataProdutoRepository.findByIdAndAtivoTrue(id);
    }

}

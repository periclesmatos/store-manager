package com.pericles.store_manager.domain.model;

import com.pericles.store_manager.interfaces.dto.produto.ProdutoUpdate;
import com.pericles.store_manager.infrastructure.exception.EstoqueInsuficienteException;
import com.pericles.store_manager.infrastructure.exception.NegocioException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    private String descricao;

    @Column(precision = 19, scale = 4)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer estoque;
    private boolean ativo;

    public Produto(String nome, String descricao, BigDecimal preco, Integer estoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.ativo = true;
    }

    public void atualizarProduto(ProdutoUpdate produtoUpdate) {
        if (produtoUpdate.nome() != null) this.nome = produtoUpdate.nome();
        if (produtoUpdate.descricao() != null) this.descricao = produtoUpdate.descricao();
    }

    public void modificarPreco(BigDecimal novoPreco) {
        if (novoPreco == null || novoPreco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("Preço inválido");
        }
        this.preco = novoPreco;
    }

    public void modificarEstoque(Integer novoEstoque) {
        if (novoEstoque == null || novoEstoque < 0) {
            throw new NegocioException("A quantidade no estoque não pode ser negativa.");
        }
        this.estoque = novoEstoque;
    }

    public void debitarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new NegocioException("Quantidade para debito não pode ser negativa.");
        }
        if (getEstoque() < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + getNome());
        }
        this.estoque -= quantidade;
    }

    public void reabastecerEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new NegocioException("Quantidade para reabastecer estoque não pode ser negativa.");
        }
        this.estoque += quantidade;
    }

    public void desativar() {
        this.ativo = false;
    }

}

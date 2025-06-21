package com.pericles.store_manager.domain;

import com.pericles.store_manager.dto.ProdutoRequest;
import com.pericles.store_manager.dto.ProdutoUpdateDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotBlank
    private String nome;
    private String descricao;

    @NotNull
    @Positive
    @Column(precision = 19, scale = 4)
    private BigDecimal preco;

    @NotNull
    @PositiveOrZero
    private Integer estoque;
    private boolean ativo;

    public Produto(ProdutoRequest produtoRequest) {
        this.nome = produtoRequest.nome();
        this.descricao = produtoRequest.descricao();
        this.preco = produtoRequest.preco();
        this.estoque = produtoRequest.estoque();
        this.ativo = true;
    }

    public void atualizarProduto(ProdutoUpdateDTO produtoUpdate) {
        if (produtoUpdate.nome() != null) this.nome = produtoUpdate.nome();
        if (produtoUpdate.descricao() != null) this.descricao = produtoUpdate.descricao();
    }

    public void modificarPreco(BigDecimal novoPreco) {
        if (novoPreco == null || novoPreco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
        this.preco = novoPreco;
    }

    public void modificarEstoque(Integer novoEstoque) {
        if (novoEstoque == null || novoEstoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        this.estoque = novoEstoque;
    }

    public void deletarProduto() {
        this.ativo = false;
    }

}

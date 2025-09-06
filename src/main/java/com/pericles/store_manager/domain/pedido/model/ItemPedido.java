package com.pericles.store_manager.domain.pedido.model;

import com.pericles.store_manager.domain.produto.model.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "itens_pedido")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Positive
    @Column(nullable = false)
    private Integer quantidade;

    @PositiveOrZero
    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @PositiveOrZero
    @Column(name = "preco_total", nullable = false)
    private BigDecimal precoTotal;

    public ItemPedido(Pedido pedido, Produto produto, @NotNull @Positive Integer quantidade) {
        this.pedido = Objects.requireNonNull(pedido, "Pedido não pode ser nulo");
        this.produto = Objects.requireNonNull(produto, "Produto não pode ser nulo");
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
        this.precoTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

}

package com.pericles.store_manager.modules.pedido.domain.model;

import com.pericles.store_manager.modules.cliente.domain.model.Cliente;
import com.pericles.store_manager.modules.produto.domain.model.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "cliente_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pedido", nullable = false)
    private StatusPedido statusPedido;

    @PositiveOrZero
    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "data_pedido", nullable = false, updatable = false)
    private LocalDateTime dataPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.statusPedido = StatusPedido.AGUARDANDO_PAGAMENTO;
        this.valorTotal = BigDecimal.ZERO;
    }

    @PrePersist
    public void prePersist() {
        this.dataPedido = LocalDateTime.now();
    }

    public void calcularTotal() {
        this.valorTotal = itens.stream()
                .map((ItemPedido::getPrecoTotal))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(Produto produto, int quantidade) {
        produto.debitarEstoque(quantidade);
        ItemPedido item =  new ItemPedido(this, produto, quantidade);
        itens.add(item);
    }

    public void modificarStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

}

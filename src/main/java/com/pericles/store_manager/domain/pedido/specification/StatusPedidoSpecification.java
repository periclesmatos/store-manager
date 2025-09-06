package com.pericles.store_manager.domain.pedido.specification;

import com.pericles.store_manager.domain.pedido.model.StatusPedido;
import com.pericles.store_manager.domain.pedido.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

public class StatusPedidoSpecification implements PedidoSpecificationStrategy {

    private final StatusPedido status;

    public StatusPedidoSpecification(StatusPedido status) {
        this.status = status;
    }

    @Override
    public Specification<Pedido> toSpecification() {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("statusPedido"), status);
    }

}

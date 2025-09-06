package com.pericles.store_manager.domain.cliente.specification;

import com.pericles.store_manager.domain.pedido.model.Pedido;
import com.pericles.store_manager.domain.pedido.specification.PedidoSpecificationStrategy;
import org.springframework.data.jpa.domain.Specification;

public class ClienteIdSpecification implements PedidoSpecificationStrategy {

    private final Long clienteId;

    public ClienteIdSpecification(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public Specification<Pedido> toSpecification() {
        return (root, query, cb) -> clienteId == null ? null : cb.equal(root.get("cliente"), clienteId);
    }

}

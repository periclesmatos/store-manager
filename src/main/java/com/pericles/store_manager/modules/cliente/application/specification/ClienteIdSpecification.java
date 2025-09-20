package com.pericles.store_manager.modules.cliente.application.specification;

import com.pericles.store_manager.modules.pedido.domain.model.Pedido;
import com.pericles.store_manager.modules.pedido.application.specification.PedidoSpecificationStrategy;
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

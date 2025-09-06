package com.pericles.store_manager.domain.pedido.specification;

import com.pericles.store_manager.domain.pedido.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

public interface PedidoSpecificationStrategy {
    Specification<Pedido> toSpecification();
}

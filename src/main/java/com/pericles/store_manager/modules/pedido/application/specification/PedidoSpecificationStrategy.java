package com.pericles.store_manager.modules.pedido.application.specification;

import com.pericles.store_manager.modules.pedido.domain.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

public interface PedidoSpecificationStrategy {
    Specification<Pedido> toSpecification();
}

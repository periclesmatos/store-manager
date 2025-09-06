package com.pericles.store_manager.domain.pedido.specification;

import com.pericles.store_manager.domain.pedido.model.Pedido;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class DataPedidoSpecification implements PedidoSpecificationStrategy {

    private final LocalDateTime dataInicio;
    private final LocalDateTime dataFinal;

    public DataPedidoSpecification(LocalDateTime dataInicio, LocalDateTime dataFinal) {
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
    }

    @Override
    public Specification<Pedido> toSpecification() {
        return (root, query, cb) -> {
            if (dataInicio == null && dataFinal == null) {
                return null;
            }

            if (dataInicio != null && dataFinal != null) {
                return cb.between(root.get("dataPedido"), dataInicio, dataFinal);
            }

            if (dataInicio != null) {
                return cb.greaterThanOrEqualTo(root.get("dataPedido"), dataInicio);
            }

            return cb.lessThanOrEqualTo(root.get("dataPedido"), dataFinal);
        };
    }

}


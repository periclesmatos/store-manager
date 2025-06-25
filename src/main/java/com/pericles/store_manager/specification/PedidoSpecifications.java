package com.pericles.store_manager.specification;

import com.pericles.store_manager.domain.Pedido;
import com.pericles.store_manager.domain.StatusPedido;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PedidoSpecifications {

    public static Specification<Pedido> comStatus(StatusPedido status) {
        return ((root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("statusPedido"), status));
    }

    public static Specification<Pedido> comClienteId(Long clienteId) {
        return (((root, query, criteriaBuilder) ->
                clienteId == null ? null : criteriaBuilder.equal(root.get("cliente").get("id"), clienteId)));
    }

    public static Specification<Pedido> comDataPedidoEntre(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return (root, query, criteriaBuilder) -> {
            if (dataInicio == null && dataFim == null) return null;
            if (dataInicio != null && dataFim != null)
                return criteriaBuilder.between(root.get("dataPedido"), dataInicio, dataFim);
            else if (dataInicio != null)
                return criteriaBuilder.greaterThanOrEqualTo(root.get("dataPedido"), dataInicio);
            else
                return criteriaBuilder.lessThanOrEqualTo(root.get("dataPedido"), dataFim);
        };
    }

}

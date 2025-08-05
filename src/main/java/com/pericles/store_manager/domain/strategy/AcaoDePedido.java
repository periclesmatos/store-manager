package com.pericles.store_manager.domain.strategy;

import com.pericles.store_manager.domain.model.Pedido;

public interface AcaoDePedido {
    void executar(Pedido pedido);
}

package com.pericles.store_manager.strategy;

import com.pericles.store_manager.domain.Pedido;

public interface AcaoDePedido {
    void executar(Pedido pedido);
}

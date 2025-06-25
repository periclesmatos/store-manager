package com.pericles.store_manager.domain;

import com.pericles.store_manager.strategy.AcaoDePedido;

public enum AcaoPedido implements AcaoDePedido {

    CONFIRMAR_PAGAMENTO {
        @Override
        public void executar(Pedido pedido) {
            if (pedido.getStatusPedido() != StatusPedido.AGUARDANDO_PAGAMENTO) {
                throw new IllegalArgumentException("Pagamento já confirmado.");
            }
            pedido.modificarStatusPedido(StatusPedido.PROCESSANDO);
        }
    },

    CONFIRMAR_ENVIO {
        @Override
        public void executar(Pedido pedido) {
            if (pedido.getStatusPedido() != StatusPedido.PROCESSANDO) {
                throw new IllegalStateException("Pedido ainda não está em processamento.");
            }
            pedido.modificarStatusPedido(StatusPedido.ENVIADO);
        }
    },

    CONFIRMAR_ENTREGA {
        @Override
        public void executar(Pedido pedido) {
            if (pedido.getStatusPedido() != StatusPedido.ENVIADO) {
                throw new IllegalStateException("Pedido ainda não foi enviado.");
            }
            pedido.modificarStatusPedido(StatusPedido.CONCLUIDO);
        }
    },

    CONFIRMAR_RETIRADA {
        @Override
        public void executar(Pedido pedido) {
            if (pedido.getStatusPedido() != StatusPedido.PROCESSANDO) {
                throw new IllegalStateException("Pedido não está disponível para retirada.");
            }
            pedido.modificarStatusPedido(StatusPedido.CONCLUIDO);
        }
    },

    CANCELAR {
        @Override
        public void executar(Pedido pedido) {
            if (pedido.getStatusPedido() == StatusPedido.CONCLUIDO) {
                throw new IllegalStateException("Pedido já concluído não pode ser cancelado.");
            }
            pedido.modificarStatusPedido(StatusPedido.CANCELADO);

            for (ItemPedido item : pedido.getItens()) {
                Produto produto =  item.getProduto();
                produto.reabastecerEstoque(item.getQuantidade());
            }
        }
    };

}

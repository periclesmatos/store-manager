package com.pericles.store_manager.modules.shared.exception;

public class EstoqueInsuficienteException extends  RuntimeException {

    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }

}

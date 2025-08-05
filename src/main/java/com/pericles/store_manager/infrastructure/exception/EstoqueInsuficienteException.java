package com.pericles.store_manager.infrastructure.exception;

public class EstoqueInsuficienteException extends  RuntimeException {

    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem);
    }

}

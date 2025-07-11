package com.pericles.store_manager.exception;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String username) {
        super("Usuário já existe: " + username);
    }
}

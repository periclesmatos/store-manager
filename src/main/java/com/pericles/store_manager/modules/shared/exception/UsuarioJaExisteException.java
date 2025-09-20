package com.pericles.store_manager.modules.shared.exception;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String username) {
        super("Usuário já existe: " + username);
    }
}

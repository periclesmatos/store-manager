package com.pericles.store_manager.infrastructure.exception;

import java.time.LocalDateTime;

public record ErroResposta(int status, String erro, LocalDateTime timestamp) {

    public ErroResposta(int status, String erro) {
        this(status, erro, LocalDateTime.now());
    }
}

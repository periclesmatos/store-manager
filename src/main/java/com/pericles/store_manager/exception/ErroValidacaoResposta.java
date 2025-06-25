package com.pericles.store_manager.exception;

import java.time.LocalDateTime;
import java.util.List;


public record ErroValidacaoResposta(
        int status,
        String erro,
        LocalDateTime timestamp,
        List<ErroCampo> campos
) {
    public ErroValidacaoResposta(List<ErroCampo> campos) {
        this(400, "Erro de validação", LocalDateTime.now(), campos);
    }
}

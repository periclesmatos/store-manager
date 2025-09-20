package com.pericles.store_manager.modules.cliente.domain.service;

import org.springframework.stereotype.Service;

@Service
public interface ClienteDomainService {
    void validarCpfUnico(String cpf);
    void validarEmailUnico(String email);
}

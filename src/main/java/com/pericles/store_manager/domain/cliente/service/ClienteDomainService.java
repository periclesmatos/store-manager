package com.pericles.store_manager.domain.cliente.service;

import org.springframework.stereotype.Service;

@Service
public interface ClienteDomainService {
    void validarCpfUnico(String cpf);
    void validarEmailUnico(String email);
}

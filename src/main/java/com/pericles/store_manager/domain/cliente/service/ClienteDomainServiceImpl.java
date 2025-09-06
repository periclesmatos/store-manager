package com.pericles.store_manager.domain.cliente.service;

import com.pericles.store_manager.domain.cliente.repository.ClienteRepository;
import com.pericles.store_manager.infrastructure.exception.NegocioException;
import org.springframework.stereotype.Service;

@Service
public class ClienteDomainServiceImpl implements ClienteDomainService {

    private final ClienteRepository clienteRepository;

    public ClienteDomainServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void validarCpfUnico(String cpf) {
        if (clienteRepository.existsByCpfAndAtivoTrue(cpf)) {
            throw new NegocioException("CPF já cadastrado para cliente ativo.");
        }
    }

    @Override
    public void validarEmailUnico(String email) {
        if (clienteRepository.existsByEmailAndAtivoTrue(email)) {
            throw new NegocioException("Email já cadastrado para cliente ativo.");
        }
    }

}

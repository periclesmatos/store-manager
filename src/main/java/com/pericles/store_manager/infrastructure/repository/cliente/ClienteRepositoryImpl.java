package com.pericles.store_manager.infrastructure.repository.cliente;

import com.pericles.store_manager.domain.cliente.model.Cliente;
import com.pericles.store_manager.domain.cliente.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final SpringDataClienteRepository springDataClienteRepository;

    public ClienteRepositoryImpl(SpringDataClienteRepository springDataClienteRepository) {
        this.springDataClienteRepository = springDataClienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return springDataClienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findByIdAndAtivoTrue(Long id) {
        return springDataClienteRepository.findByIdAndAtivoTrue(id);
    }

    @Override
    public boolean existsByCpfAndAtivoTrue(String cpf) {
        return springDataClienteRepository.existsByCpfAndAtivoTrue(cpf);
    }

    @Override
    public boolean existsByEmailAndAtivoTrue(String email) {
        return springDataClienteRepository.existsByEmailAndAtivoTrue(email);
    }

    @Override
    public Page<Cliente> findAllByAtivoTrue(Pageable pageable) {
        return springDataClienteRepository.findAllByAtivoTrue(pageable);
    }

    @Override
    public Page<Cliente> buscarClientesPorTermo(String termo, Pageable pageable) {
        return springDataClienteRepository.buscarClientesPorTermo(termo, pageable);
    }

}

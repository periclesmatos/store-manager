package com.pericles.store_manager.modules.cliente.domain.repository;

import com.pericles.store_manager.modules.cliente.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findByIdAndAtivoTrue(Long id);
    boolean existsByCpfAndAtivoTrue(String cpf);
    boolean existsByEmailAndAtivoTrue(String email);
    Page<Cliente> findAllByAtivoTrue(Pageable pageable);
    Page<Cliente> buscarClientesPorTermo(String termo, Pageable pageable);
}

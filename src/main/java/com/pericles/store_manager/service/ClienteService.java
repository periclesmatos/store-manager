package com.pericles.store_manager.service;

import com.pericles.store_manager.domain.Cliente;
import com.pericles.store_manager.dto.cliente.ClienteRequest;
import com.pericles.store_manager.dto.cliente.ClienteResponse;
import com.pericles.store_manager.dto.cliente.ClienteUpdateDTO;
import com.pericles.store_manager.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente cadastrarCliente(@Valid ClienteRequest clienteRequest) {
        var cliente = new Cliente(clienteRequest);
        clienteRepository.save(cliente);
        return cliente;
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> listarClientesAtivos(Pageable pageable) {
        return clienteRepository.findAllByAtivoTrue(pageable).map(ClienteResponse::new);
    }

    @Transactional(readOnly = true)
    public Cliente buscarClienteAtivoPorId(Long id) {
        return clienteRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new EntityNotFoundException("Cliente inativo ou não encontrado."));
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> buscarClientes(String termo, Pageable pageable) {
        return clienteRepository.buscarClientesPorTermo(termo, pageable).map(ClienteResponse::new);
    }

    @Transactional
    public Cliente atualizarCliente(Long id, @Valid ClienteUpdateDTO clienteUpdate) {
        var cliente = buscarClienteAtivoPorId(id);
        cliente.atualizarCliente(clienteUpdate);
        return cliente;
    }

    @Transactional
    public void deletarCliente(Long id) {
        var cliente = buscarClienteAtivoPorId(id);
        cliente.deletarCliente();
    }

}

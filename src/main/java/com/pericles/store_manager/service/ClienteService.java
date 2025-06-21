package com.pericles.store_manager.service;

import com.pericles.store_manager.domain.Cliente;
import com.pericles.store_manager.dto.cliente.ClienteRequest;
import com.pericles.store_manager.dto.cliente.ClienteResponse;
import com.pericles.store_manager.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(@Valid ClienteRequest clienteRequest) {
        var cliente = new Cliente(clienteRequest);
        clienteRepository.save(cliente);
        return cliente;
    }

    public Page<ClienteResponse> listarClientesAtivos(Pageable pageable) {
        return clienteRepository.findAllByAtivoTrue(pageable).map(ClienteResponse::new);
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));
    }

    public Page<ClienteResponse> buscarClientes(String termo, Pageable pageable) {
        return clienteRepository.buscarClientesPorTermo(termo, pageable).map(ClienteResponse::new);
    }

    public Cliente atualizarCliente(Long id, @Valid ClienteRequest clienteRequest) {
        var cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));
        cliente.atualizarCliente(clienteRequest);
        return cliente;
    }

    public void deletarCliente(Long id) {
        var cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));
        cliente.deletarCliente();
    }

}

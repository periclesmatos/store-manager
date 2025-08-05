package com.pericles.store_manager.application.service;

import com.pericles.store_manager.domain.model.Cliente;
import com.pericles.store_manager.interfaces.dto.cliente.ClienteRequest;
import com.pericles.store_manager.interfaces.dto.cliente.ClienteResponse;
import com.pericles.store_manager.interfaces.dto.cliente.ClienteUpdate;
import com.pericles.store_manager.infrastructure.exception.RecursoNaoEncontradoException;
import com.pericles.store_manager.domain.repository.ClienteRepository;
import com.pericles.store_manager.interfaces.mapper.ClienteMapper;
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

    @Transactional(readOnly = true)
    public Cliente buscarEntidadePorId(Long id) {
        return clienteRepository
                .findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente com ID " + id + " inativo ou não encontrado."));
    }

    @Transactional
    public ClienteResponse cadastrar(ClienteRequest request) {
        Cliente cliente = ClienteMapper.toEntity(request);
        clienteRepository.save(cliente);
        return ClienteMapper.toResponse(cliente);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> listarTodos(Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.findAllByAtivoTrue(pageable);
        return clientes.map(ClienteMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = buscarEntidadePorId(id);
        return ClienteMapper.toResponse(cliente);
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> buscarPorTermo(String termo, Pageable pageable) {
        return clienteRepository.buscarClientesPorTermo(termo, pageable).map(ClienteMapper::toResponse);
    }

    @Transactional
    public ClienteResponse atualizar(Long id, @Valid ClienteUpdate clienteUpdate) {
        Cliente response = buscarEntidadePorId(id);
        response.atualizarCliente(clienteUpdate);
        return ClienteMapper.toResponse(response);
    }

    @Transactional
    public void deletar(Long id) {
        var cliente = buscarEntidadePorId(id);
        cliente.desativar();
    }

}

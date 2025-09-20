package com.pericles.store_manager.modules.cliente.application.service;

import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteRequest;
import com.pericles.store_manager.modules.cliente.interfaces.dto.response.ClienteResponse;
import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteUpdate;
import com.pericles.store_manager.modules.cliente.domain.model.Cliente;
import com.pericles.store_manager.modules.cliente.domain.repository.ClienteRepository;
import com.pericles.store_manager.modules.cliente.domain.service.ClienteDomainService;
import com.pericles.store_manager.modules.shared.exception.RecursoNaoEncontradoException;
import com.pericles.store_manager.modules.cliente.interfaces.mapper.ClienteMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteDomainService clienteDomainService;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteDomainService clienteDomainService, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteDomainService = clienteDomainService;
        this.clienteMapper = clienteMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarEntidadePorId(Long id) {
        return clienteRepository
            .findByIdAndAtivoTrue(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado ou inativo."));
    }

    @Override
    public ClienteResponse cadastrar(ClienteRequest request) {
        clienteDomainService.validarEmailUnico(request.email());
        clienteDomainService.validarCpfUnico(request.cpf());

        Cliente cliente = clienteMapper.toEntity(request);
        clienteRepository.save(cliente);

        return clienteMapper.toResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = buscarEntidadePorId(id);
        return clienteMapper.toResponse(cliente);
    }

    @Override
    public ClienteResponse atualizar(Long id, ClienteUpdate request) {
        Cliente cliente = buscarEntidadePorId(id);

        if (!cliente.getEmail().equals(request.email())) {
            clienteDomainService.validarEmailUnico(request.email());
        }

        if (!cliente.getCpf().equals(request.cpf())) {
            clienteDomainService.validarCpfUnico(request.cpf());
        }

        clienteMapper.updateToEntity(cliente, request);
        clienteRepository.save(cliente);

        return clienteMapper.toResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteResponse> listarTodos(Pageable pageable) {
        return clienteRepository.findAllByAtivoTrue(pageable).map(clienteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteResponse> buscarPorTermo(String termo, Pageable pageable) {
        return clienteRepository.buscarClientesPorTermo(termo, pageable).map(clienteMapper::toResponse);
    }

    @Override
    public void deletar(Long id) {
        Cliente cliente = buscarEntidadePorId(id);
        cliente.desativar();
        clienteRepository.save(cliente);
    }

}

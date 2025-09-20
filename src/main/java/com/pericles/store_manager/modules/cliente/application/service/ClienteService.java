package com.pericles.store_manager.modules.cliente.application.service;

import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteRequest;
import com.pericles.store_manager.modules.cliente.interfaces.dto.response.ClienteResponse;
import com.pericles.store_manager.modules.cliente.interfaces.dto.request.ClienteUpdate;
import com.pericles.store_manager.modules.cliente.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    Cliente buscarEntidadePorId(Long id);
    ClienteResponse cadastrar(ClienteRequest request);
    ClienteResponse buscarPorId(Long id);
    ClienteResponse atualizar(Long id, ClienteUpdate request);
    Page<ClienteResponse> listarTodos(Pageable pageable);
    Page<ClienteResponse> buscarPorTermo(String termo, Pageable pageable);
    void deletar(Long id);
}

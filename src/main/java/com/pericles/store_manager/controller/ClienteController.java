package com.pericles.store_manager.controller;

import com.pericles.store_manager.dto.cliente.ClienteRequest;
import com.pericles.store_manager.dto.cliente.ClienteResponse;
import com.pericles.store_manager.dto.cliente.ClienteUpdateDTO;
import com.pericles.store_manager.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrarCliente(@RequestBody @Valid ClienteRequest clienteRequest, UriComponentsBuilder uriComponentsBuilder) {
        var cliente = clienteService.cadastrarCliente(clienteRequest);
        var uri = uriComponentsBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteResponse(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> listarClientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = clienteService.listarClientesAtivos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarClientePorId(@PathVariable Long id) {
        var cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(new ClienteResponse(cliente));
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<ClienteResponse>> buscarClientes(@RequestParam String termo, Pageable pageable) {
        var page = clienteService.buscarClientes(termo, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Long id, @RequestBody @Valid ClienteUpdateDTO clienteUpdate) {
        var cliente = clienteService.atualizarCliente(id, clienteUpdate);
        return ResponseEntity.ok(new ClienteResponse(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponse> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

}

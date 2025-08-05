package com.pericles.store_manager.interfaces.controller;

import com.pericles.store_manager.interfaces.dto.cliente.ClienteRequest;
import com.pericles.store_manager.interfaces.dto.cliente.ClienteResponse;
import com.pericles.store_manager.interfaces.dto.cliente.ClienteUpdate;
import com.pericles.store_manager.application.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@RequestBody @Valid ClienteRequest request, UriComponentsBuilder uriComponentsBuilder) {
        ClienteResponse response = clienteService.cadastrar(request);
        URI uri = uriComponentsBuilder
                .path("/cliente/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        Page<ClienteResponse> responses = clienteService.listarTodos(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        ClienteResponse response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<ClienteResponse>> buscarComTermo(@RequestParam String termo, Pageable pageable) {
        Page<ClienteResponse> responses = clienteService.buscarPorTermo(termo, pageable);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteUpdate clienteUpdate) {
        ClienteResponse response = clienteService.atualizar(id, clienteUpdate);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponse> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}

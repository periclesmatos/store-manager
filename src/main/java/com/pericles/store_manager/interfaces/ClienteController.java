package com.pericles.store_manager.interfaces;

import com.pericles.store_manager.application.cliente.dto.request.ClienteRequest;
import com.pericles.store_manager.application.cliente.dto.response.ClienteResponse;
import com.pericles.store_manager.application.cliente.dto.request.ClienteUpdate;
import com.pericles.store_manager.application.cliente.service.ClienteService;
import jakarta.validation.Valid;
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

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@Valid @RequestBody ClienteRequest request, UriComponentsBuilder uriComponentsBuilder) {
        ClienteResponse response = clienteService.cadastrar(request);

        URI uri = uriComponentsBuilder
                .path("/clientes/{id}")
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
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}

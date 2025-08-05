package com.pericles.store_manager.interfaces.controller;

import com.pericles.store_manager.application.service.ProdutoService;
import com.pericles.store_manager.interfaces.dto.produto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@RequestBody @Valid ProdutoRequest request, UriComponentsBuilder uriBuilder) {
        ProdutoResponse response = produtoService.cadastrar(request);
        URI uri = uriBuilder
                .path("/produto/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        Page<ProdutoResponse> responses = produtoService.listar(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        ProdutoResponse response = produtoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<ProdutoResponse>> buscarPorNome(@RequestParam(required = false) String nome, Pageable pageable) {
        Page<ProdutoResponse> responses = produtoService.filtrarPorNome(nome, pageable);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoUpdate produtoUpdate) {
        ProdutoResponse response = produtoService.atualizarProduto(id, produtoUpdate);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/preco")
    public ResponseEntity<ProdutoResponse> modificarPreco(@PathVariable Long id, @RequestBody @Valid AtualizarPreco atualizarPreco) {
        ProdutoResponse response = produtoService.modificarPreco(id, atualizarPreco);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutoResponse> modificarEstoque(@PathVariable Long id, @RequestBody @Valid AtualizarEstoque atualizarEstoque) {
        ProdutoResponse response = produtoService.modificarEstoque(id, atualizarEstoque);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoResponse> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}

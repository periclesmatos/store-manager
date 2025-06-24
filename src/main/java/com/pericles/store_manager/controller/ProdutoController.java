package com.pericles.store_manager.controller;

import com.pericles.store_manager.dto.produto.*;
import com.pericles.store_manager.service.ProdutoService;
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
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoResponse> cadastrarProduto(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriComponentsBuilder) {
        var produto = produtoService.registrarProduto(produtoRequest);
        var uri = uriComponentsBuilder.path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoResponse(produto));
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> listarProdutosAtivos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = produtoService.listarProdutosAtivos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarProduto(@PathVariable Long id) {
        var produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(new ProdutoResponse(produto));
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<ProdutoResponse>> buscarProdutosPorNome(@RequestParam(required = false) String nome, Pageable pageable) {
        var page = produtoService.FiltrarProdutosPorNome(nome, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoUpdateDTO produtoUpdate) {
        var produto = produtoService.atualizarProduto(id, produtoUpdate);
        return ResponseEntity.ok(new ProdutoResponse(produto));
    }

    @PatchMapping("/{id}/preco")
    @Transactional
    public ResponseEntity<ProdutoResponse> modificarPreco(@PathVariable Long id, @RequestBody @Valid AtualizarPrecoDTO atualizarPreco) {
        var produto = produtoService.modificarPreco(id, atualizarPreco);
        return ResponseEntity.ok(new ProdutoResponse(produto));
    }

    @PatchMapping("/{id}/estoque")
    @Transactional
    public ResponseEntity<ProdutoResponse> modificarEstoque(@PathVariable Long id, @RequestBody @Valid AtualizarEstoqueDTO atualizarEstoque) {
        var produto = produtoService.modificarEstoque(id, atualizarEstoque);
        return ResponseEntity.ok(new ProdutoResponse(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoResponse> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

}

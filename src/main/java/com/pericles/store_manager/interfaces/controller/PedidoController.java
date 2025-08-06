package com.pericles.store_manager.interfaces.controller;

import com.pericles.store_manager.domain.model.AcaoPedido;
import com.pericles.store_manager.domain.model.StatusPedido;
import com.pericles.store_manager.interfaces.dto.pedido.ItemPedidoRequest;
import com.pericles.store_manager.interfaces.dto.pedido.PedidoRequest;
import com.pericles.store_manager.interfaces.dto.pedido.PedidoResponse;
import com.pericles.store_manager.infrastructure.exception.NegocioException;
import com.pericles.store_manager.application.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> registrarPedido(@RequestBody @Valid PedidoRequest request, UriComponentsBuilder uriBuilder) {
        PedidoResponse response = pedidoService.cadastrar(request);
        URI uri = uriBuilder
                .path("/pedido/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponse>> listarPedidos(
            @RequestParam(required = false) StatusPedido statusPedido,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @PageableDefault(size = 10, sort = "dataPedido") Pageable pageable
    ) {
        var pedidos = pedidoService.listarComFiltro(statusPedido, clienteId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable Long id) {
        PedidoResponse response = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{pedidoId}/itens")
    public ResponseEntity<PedidoResponse> atualizarItens(@PathVariable Long pedidoId, @RequestBody @NotEmpty @Valid List<ItemPedidoRequest> itensRequest) {
        pedidoService.atualizarItens(pedidoId, itensRequest);
        PedidoResponse response = pedidoService.buscarPorId(pedidoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{pedidoId}/acao/{acao}")
    public ResponseEntity<PedidoResponse> processarAcao(@PathVariable Long pedidoId, @PathVariable String acao) {
        AcaoPedido acaoPedido;
        try {
            acaoPedido = AcaoPedido.valueOf(acao.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NegocioException("Ação inválida para o pedido: " + acao);
        }

        pedidoService.processarAcao(pedidoId, acaoPedido);
        PedidoResponse response = pedidoService.buscarPorId(pedidoId);
        return ResponseEntity.ok(response);
    }

}

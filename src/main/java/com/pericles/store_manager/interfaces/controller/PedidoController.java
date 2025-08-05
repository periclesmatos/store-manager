package com.pericles.store_manager.interfaces.controller;

import com.pericles.store_manager.domain.model.AcaoPedido;
import com.pericles.store_manager.domain.model.Pedido;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> registrarPedido(@RequestBody @Valid PedidoRequest pedidoRequest, UriComponentsBuilder uriComponentsBuilder) {
        var pedido = pedidoService.registrarPedido(pedidoRequest);
        var uri = uriComponentsBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).body(new PedidoResponse(pedido));
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponse>> listarPedidos(
            @RequestParam(required = false) StatusPedido statusPedido,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @PageableDefault(size = 10, sort = "dataPedido") Pageable pageable
    ) {
        var pedidos = pedidoService.listarPedidosComFiltro(statusPedido, clienteId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable Long id) {
        var pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(new PedidoResponse(pedido));
    }

    @PutMapping("/{pedidoId}/itens")
    public ResponseEntity<PedidoResponse> atualizarItens(@PathVariable Long pedidoId, @RequestBody @NotEmpty @Valid List<ItemPedidoRequest> itensRequest) {
        pedidoService.atualizarItens(pedidoId, itensRequest);
        Pedido pedidoAtualizado = pedidoService.buscarPedidoPorId(pedidoId);
        return ResponseEntity.ok(new PedidoResponse(pedidoAtualizado));
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
        Pedido pedidoAtualizado = pedidoService.buscarPedidoPorId(pedidoId);
        return ResponseEntity.ok(new PedidoResponse(pedidoAtualizado));
    }

}

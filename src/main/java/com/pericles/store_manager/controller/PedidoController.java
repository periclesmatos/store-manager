package com.pericles.store_manager.controller;

import com.pericles.store_manager.domain.AcaoPedido;
import com.pericles.store_manager.domain.Pedido;
import com.pericles.store_manager.domain.StatusPedido;
import com.pericles.store_manager.dto.pedido.ItemPedidoRequest;
import com.pericles.store_manager.dto.pedido.PedidoRequest;
import com.pericles.store_manager.dto.pedido.PedidoResponse;
import com.pericles.store_manager.service.PedidoService;
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
@RequestMapping("/pedido")
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

    @PostMapping("/{pedidoId}/confirmar-pagamento")
    public ResponseEntity<PedidoResponse> confirmarPagamento(@PathVariable Long pedidoId) {
        pedidoService.processarAcao(pedidoId, AcaoPedido.CONFIRMAR_PAGAMENTO);
        Pedido pedidoAtualizado = pedidoService.buscarPedidoPorId(pedidoId);
        return ResponseEntity.ok(new PedidoResponse(pedidoAtualizado));
    }

    @PostMapping("/{pedidoId}/confirmar-envio")
    public ResponseEntity<PedidoResponse> confirmarEnvio(@PathVariable Long pedidoId) {
        pedidoService.processarAcao(pedidoId, AcaoPedido.CONFIRMAR_ENVIO);
        Pedido pedidoAtualizado = pedidoService.buscarPedidoPorId(pedidoId);
        return ResponseEntity.ok(new PedidoResponse(pedidoAtualizado));
    }

    @PostMapping("/{pedidoId}/confirmar-entrega")
    public ResponseEntity<PedidoResponse> confirmarEntrega(@PathVariable Long pedidoId) {
        pedidoService.processarAcao(pedidoId, AcaoPedido.CONFIRMAR_ENTREGA);
        Pedido pedidoAtualizado = pedidoService.buscarPedidoPorId(pedidoId);
        return ResponseEntity.ok(new PedidoResponse(pedidoAtualizado));
    }

    @PostMapping("/{pedidoId}/confirmar-retirada")
    public ResponseEntity<PedidoResponse> confirmarRetirada(@PathVariable Long pedidoId) {
        pedidoService.processarAcao(pedidoId, AcaoPedido.CONFIRMAR_RETIRADA);
        Pedido pedidoAtualizado = pedidoService.buscarPedidoPorId(pedidoId);
        return ResponseEntity.ok(new PedidoResponse(pedidoAtualizado));
    }

    @PostMapping("/{pedidoId}/cancelar")
    public ResponseEntity<PedidoResponse> cancelar(@PathVariable Long pedidoId) {
        pedidoService.processarAcao(pedidoId, AcaoPedido.CANCELAR);
        Pedido pedidoAtualizado = pedidoService.buscarPedidoPorId(pedidoId);
        return ResponseEntity.ok(new PedidoResponse(pedidoAtualizado));
    }

}

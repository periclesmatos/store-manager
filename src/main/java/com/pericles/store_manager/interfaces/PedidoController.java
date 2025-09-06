package com.pericles.store_manager.interfaces;

import com.pericles.store_manager.domain.pedido.model.StatusPedido;
import com.pericles.store_manager.application.pedido.dto.request.ItemPedidoRequest;
import com.pericles.store_manager.application.pedido.dto.request.PedidoRequest;
import com.pericles.store_manager.application.pedido.dto.response.PedidoResponse;
import com.pericles.store_manager.application.pedido.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> registrarPedido(@Valid @RequestBody PedidoRequest request, UriComponentsBuilder uriBuilder) {
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
        PedidoResponse response = pedidoService.atualizarItens(pedidoId, itensRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizaStatus(@PathVariable Long id, @RequestBody StatusPedido statusPedido) {
        PedidoResponse response = pedidoService.modificarStatus(id, statusPedido);
        return ResponseEntity.ok(response);
    }

}

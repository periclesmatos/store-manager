package com.pericles.store_manager.controller;

import com.pericles.store_manager.domain.Usuario;
import com.pericles.store_manager.dto.usuario.UsuarioRequest;
import com.pericles.store_manager.dto.usuario.UsuarioResponse;
import com.pericles.store_manager.security.TokenResponse;
import com.pericles.store_manager.security.TokenService;
import com.pericles.store_manager.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenteicacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioRequest usuarioRequest) {
        var usuario = usuarioService.cadastrarUsuario(usuarioRequest);
        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> efetuarLogin(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(usuarioRequest.username(), usuarioRequest.password());
        var authentication = manager.authenticate(authenticationToken);
        var token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenResponse(token));
    }

}

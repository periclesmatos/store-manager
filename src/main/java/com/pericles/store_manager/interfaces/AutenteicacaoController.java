package com.pericles.store_manager.interfaces;

import com.pericles.store_manager.domain.usuario.model.Usuario;
import com.pericles.store_manager.application.usuario.dto.UsuarioRequest;
import com.pericles.store_manager.application.usuario.dto.UsuarioResponse;
import com.pericles.store_manager.infrastructure.security.TokenResponse;
import com.pericles.store_manager.infrastructure.security.TokenService;
import com.pericles.store_manager.application.usuario.service.UsuarioService;
import jakarta.validation.Valid;
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

    private final UsuarioService usuarioService;

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    public AutenteicacaoController(UsuarioService usuarioService, AuthenticationManager manager, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.manager = manager;
        this.tokenService = tokenService;
    }

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

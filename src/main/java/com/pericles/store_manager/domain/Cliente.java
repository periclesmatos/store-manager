package com.pericles.store_manager.domain;

import com.pericles.store_manager.dto.cliente.ClienteRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String cpf;
    private boolean ativo;

    public Cliente(ClienteRequest clienteRequest) {
        this.nome = clienteRequest.nome();
        this.email = clienteRequest.email();
        this.cpf = clienteRequest.cpf();
        this.ativo = true;
    }

    public void atualizarCliente(ClienteRequest clienteRequest) {
        if (clienteRequest.nome() != null) this.nome = clienteRequest.nome();
        if (clienteRequest.email() != null) this.email = clienteRequest.email();
        if (clienteRequest.cpf() != null) this.cpf = clienteRequest.cpf();
    }

    public void deletarCliente() {
        this.ativo = false;
    }
}

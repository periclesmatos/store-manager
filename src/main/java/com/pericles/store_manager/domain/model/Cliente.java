package com.pericles.store_manager.domain.model;

import com.pericles.store_manager.interfaces.dto.cliente.ClienteUpdate;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpf;
    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Cliente(String nome, String email, String cpf, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.ativo = true;
        this.endereco = endereco != null ? endereco : new Endereco();
    }

    public void atualizarCliente(ClienteUpdate clienteUpdate) {
        if (clienteUpdate.nome() != null) this.nome = clienteUpdate.nome();
        if (clienteUpdate.email() != null) this.email = clienteUpdate.email();
        if (clienteUpdate.cpf() != null) this.cpf = clienteUpdate.cpf();
        if (clienteUpdate.endereco() != null) this.endereco.atualizarEndereco(clienteUpdate.endereco());
    }

    public void desativar() {
        this.ativo = false;
    }

}

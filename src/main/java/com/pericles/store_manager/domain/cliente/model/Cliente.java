package com.pericles.store_manager.domain.cliente.model;

import com.pericles.store_manager.infrastructure.exception.NegocioException;
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

    public void atualizarNome(String nome) {
        this.nome = nome;
    }

    public void atualizarEmail(String email) {
        this.email = email;
    }

    public void atualizarCpf(String cpf) {
        this.cpf = cpf;
    }

    public void desativar() {
        if (!isAtivo()) {
            throw new NegocioException("Cliente já está inativo.");
        }

        this.ativo = false;
    }

    public void atualizarEndereco(Endereco endereco) {
        this. endereco = endereco;
    }

}

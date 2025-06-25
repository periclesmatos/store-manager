package com.pericles.store_manager.domain;

import com.pericles.store_manager.dto.cliente.ClienteRequest;
import com.pericles.store_manager.dto.cliente.ClienteUpdateDTO;
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

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpf;
    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Cliente(ClienteRequest clienteRequest) {
        this.nome = clienteRequest.nome();
        this.email = clienteRequest.email();
        this.cpf = clienteRequest.cpf();
        this.ativo = true;
        this.endereco = new Endereco();
        if (clienteRequest.endereco() != null) {
            this.endereco.atualizarEndereco(clienteRequest.endereco());
        }
    }

    public void atualizarCliente(ClienteUpdateDTO clienteUpdate) {
        if (clienteUpdate.nome() != null) this.nome = clienteUpdate.nome();
        if (clienteUpdate.email() != null) this.email = clienteUpdate.email();
        if (clienteUpdate.cpf() != null) this.cpf = clienteUpdate.cpf();
        if (clienteUpdate.endereco() != null) this.endereco.atualizarEndereco(clienteUpdate.endereco());
    }

    public void deletarCliente() {
        this.ativo = false;
    }

}

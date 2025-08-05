package com.pericles.store_manager.domain.model;

import com.pericles.store_manager.interfaces.dto.cliente.EnderecoRequest;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class Endereco {

    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;
    private String cep;

    public Endereco() {
        this("", "", "", "", "", "", "");
    }

    public Endereco(
            String rua,
            String numero,
            String bairro,
            String cidade,
            String uf,
            String complemento,
            String cep
    ) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.complemento = complemento;
        this.cep = cep;
    }

    public void atualizarEndereco(EnderecoRequest enderecoRequest) {
        if (enderecoRequest.rua() != null) this.rua = enderecoRequest.rua();
        if (enderecoRequest.numero() != null) this.numero = enderecoRequest.numero();
        if (enderecoRequest.bairro() != null) this.bairro = enderecoRequest.bairro();
        if (enderecoRequest.cidade() != null) this.cidade = enderecoRequest.cidade();
        if (enderecoRequest.uf() != null) this.uf = enderecoRequest.uf();
        if (enderecoRequest.complemento() != null) this.complemento = enderecoRequest.complemento();
        if (enderecoRequest.cep() != null) this.cep = enderecoRequest.cep();
    }

}

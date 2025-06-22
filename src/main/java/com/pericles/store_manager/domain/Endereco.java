package com.pericles.store_manager.domain;

import com.pericles.store_manager.dto.cliente.EnderecoRequest;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Endereco(EnderecoRequest enderecoRequest) {
        this(
            enderecoRequest.rua() != null ? enderecoRequest.rua() : "",
            enderecoRequest.numero() != null ? enderecoRequest.numero() : "",
            enderecoRequest.bairro() != null ? enderecoRequest.bairro() : "",
            enderecoRequest.cidade() != null ? enderecoRequest.cidade() : "",
            enderecoRequest.uf() != null ? enderecoRequest.uf() : "",
            enderecoRequest.complemento() != null ? enderecoRequest.complemento() : "",
            enderecoRequest.cep() != null ? enderecoRequest.cep() : ""
        );
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

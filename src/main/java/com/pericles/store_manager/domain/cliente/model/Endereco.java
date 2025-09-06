package com.pericles.store_manager.domain.cliente.model;

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
        this.rua = "";
        this.numero = "";
        this.bairro = "";
        this.cidade = "";
        this.uf = "";
        this.complemento = "";
        this.cep = "";
    }

    public void atualizarRua(String rua) {
        this.rua = rua;
    }

    public void atualizarNumero(String numero) {
        this.numero = numero;
    }

    public void atualizarBairro(String bairro) {
        this.bairro = bairro;
    }

    public void atualizarCidade(String cidade) {
        this.cidade = cidade;
    }

    public void atualizarUf(String uf) {
        this.uf = uf;
    }

    public void atualizarComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void atualizarCep(String cep) {
        this.cep = cep;
    }

}

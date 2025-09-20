package com.pericles.store_manager.modules.cliente.infrastructure.repository;

import com.pericles.store_manager.modules.cliente.domain.model.Cliente;
import com.pericles.store_manager.modules.cliente.domain.model.Endereco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class SpringDataClienteRepositoryTest {

    @Autowired
    private SpringDataClienteRepository springDataClienteRepository;

    private Cliente criarClienteNoBanco(String nome, String email, String cpf, boolean ativo) {
        Cliente cliente = new Cliente(
                nome,
                email,
                cpf,
                new Endereco("Rua sete", "180", "Passare", "Fortaleza", "CE", "", "60862-180")
        );
        if (!ativo) cliente.desativar();
        return springDataClienteRepository.save(cliente);
    }

    @Test
    void save_clienteValido_devePersistirNoBanco() {
        Cliente cliente =  criarClienteNoBanco("Ativo1", "ativo1@email.com", "11111111111", true);
        Cliente resposta = springDataClienteRepository.save(cliente);

        assertNotNull(resposta.getId());
        assertEquals("Ativo1", resposta.getNome());
    }

    @Test
    void findByIdAndAtivoTrue_clienteAtivo_deveRetornarCliente() {
        Cliente ativo = criarClienteNoBanco("Ativo", "ativo@email.com", "11111111111", true);

        Optional<Cliente> resposta = springDataClienteRepository.findByIdAndAtivoTrue(ativo.getId());

        assertTrue(resposta.isPresent());
    }

    @Test
    void findByIdAndAtivoTrue_clienteInativo_deveRetornarVazio() {
        Cliente inativo = criarClienteNoBanco("Inativo1", "inativo1@email.com", "22222222222", false);

        Optional<Cliente> resposta = springDataClienteRepository.findByIdAndAtivoTrue(inativo.getId());

        assertTrue(resposta.isEmpty());
    }

    @Test
    void findByIdAndAtivoTrue_clienteNaoExistente_deveRetornarVazia() {
        Optional<Cliente> resposta = springDataClienteRepository.findByIdAndAtivoTrue(1L);

        assertTrue(resposta.isEmpty());
    }

    @Test
    void existsByCpfAndAtivoTrue_clienteAtivoExistente_deveRetornarVerdadeiro() {
        Cliente ativo = criarClienteNoBanco("Ativo", "ativo@email.com", "11111111111", true);

        boolean resposta = springDataClienteRepository.existsByCpfAndAtivoTrue(ativo.getCpf());

        assertTrue(resposta);
    }

    @Test
    void existsByCpfAndAtivoTrue_clienteNaoExistente_deveRetornarFalso() {
        Cliente intativo = criarClienteNoBanco("Inativo1", "inativo1@email.com", "22222222222", false);

        boolean resposta = springDataClienteRepository.existsByCpfAndAtivoTrue(intativo.getCpf());

        assertFalse(resposta);
    }

    @Test
    void existsByEmailAndAtivoTrue_clienteAtivoExistente_deveRetornarVerdadeiro() {
        Cliente ativo = criarClienteNoBanco("Ativo", "ativo@email.com", "11111111111", true);

        boolean resposta = springDataClienteRepository.existsByEmailAndAtivoTrue(ativo.getEmail());

        assertTrue(resposta);
    }

    @Test
    void existsByEmailAndAtivoTrue_clienteNaoExistente_deveRetornarFalso() {
        Cliente intativo = criarClienteNoBanco("Inativo1", "inativo1@email.com", "22222222222", false);

        boolean resposta = springDataClienteRepository.existsByEmailAndAtivoTrue(intativo.getEmail());

        assertFalse(resposta);
    }

    @Test
    void findAllByAtivoTrue_quandoExistirClientesAtivos_deveRetornarSomenteClientesAtivos() {
        criarClienteNoBanco("Ativo1", "ativo1@email.com", "11111111111", true);
        criarClienteNoBanco("Inativo1", "inativo1@email.com", "22222222222", false);

        Page<Cliente> resposta = springDataClienteRepository.findAllByAtivoTrue(PageRequest.of(0, 10));

        assertEquals(1, resposta.getTotalElements());
        assertTrue(resposta.getContent().stream().allMatch(Cliente::isAtivo));
    }

    @Test
    void findAllByAtivoTrue_quandoNaoExistirClientesAtivos_deveRetornarListaVazia() {
        criarClienteNoBanco("Inativo1", "inativo1@email.com", "11111111111", false);
        criarClienteNoBanco("Inativo2", "inativo2@email.com", "22222222222", false);

        Page<Cliente> resposta = springDataClienteRepository.findAllByAtivoTrue(PageRequest.of(0, 10));

        assertTrue(resposta.isEmpty());
    }

    @Test
    void findAllByAtivoTrue_quandoNaoExistirClientesNoBanco_deveRetornarListaVazia() {
        Page<Cliente> resposta = springDataClienteRepository.findAllByAtivoTrue(PageRequest.of(0,10));

        assertTrue(resposta.isEmpty());
    }

    @Test
    void buscarClientesPorTermo_quandoNomeContemTermo_deveRetornarClientesCorrespondentes() {
        criarClienteNoBanco("Maria Silva", "maria@email.com", "12345678900", true);
        criarClienteNoBanco("João Souza", "joao@email.com", "98765432100", true);

        Page<Cliente> resposta = springDataClienteRepository.buscarClientesPorTermo("Silva", PageRequest.of(0, 10));

        assertEquals(1, resposta.getTotalElements());
    }

    @Test
    void buscarClientesPorTermo_quandoEmailContemTermo_deveRetornarClientesCorrespondentes() {
        criarClienteNoBanco("Maria Silva", "maria@email.com", "12345678900", true);
        criarClienteNoBanco("João Souza", "joao@email.com", "98765432100", true);

        Page<Cliente> resposta = springDataClienteRepository.buscarClientesPorTermo("maria@", PageRequest.of(0, 10));

        assertEquals(1, resposta.getTotalElements());
    }

    @Test
    void buscarClientesPorTermo_quandoCpfContemTermo_deveRetornarClientesCorrespondentes() {
        criarClienteNoBanco("Maria Silva", "maria@email.com", "12345678900", true);
        criarClienteNoBanco("João Souza", "joao@email.com", "98765432100", true);

        Page<Cliente> resposta = springDataClienteRepository.buscarClientesPorTermo("12345678900", PageRequest.of(0, 10));

        assertEquals(1, resposta.getTotalElements());
    }

    @Test
    void buscarClientesPorTermo_quandoNenhumClienteContemTermo_deveRetornarListaVazia() {
        criarClienteNoBanco("Maria Silva", "maria@email.com", "12345678900", true);
        criarClienteNoBanco("João Souza", "joao@email.com", "98765432100", true);

        Page<Cliente> resposta = springDataClienteRepository.buscarClientesPorTermo("Pericles", PageRequest.of(0, 10));

        assertTrue(resposta.isEmpty());
    }

}

package com.desafioItau.entidades;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ClienteEntidadeTest {

    @Test
    void criarClienteComCpf() {
        ClienteEntidade clienteEntidade = ClienteEntidade.builder().id(1L).nome("leonardo")
                .cpf("123").telefone("12345678").endereco("rua pedro").build();  //Cenario

        Assertions.assertThat(clienteEntidade.getId()).isNotNull();                   //verificação
        Assertions.assertThat(clienteEntidade.getNome()).isEqualTo("leonardo");
        Assertions.assertThat(clienteEntidade.getCpf()).isEqualTo("123");
        Assertions.assertThat(clienteEntidade.getTelefone()).isEqualTo("12345678");
        Assertions.assertThat(clienteEntidade.getEndereco()).isEqualTo("rua pedro");
    }

    @Test
    void getNome() {
    }

    @Test
    void getCpf() {
    }

    @Test
    void getCnpj() {
    }

    @Test
    void getTelefone() {
    }

    @Test
    void getEndereco() {
    }

    @Test
    void getDataHora() {
    }

    @Test
    void getContas() {
    }

    @Test
    void setId() {
    }

    @Test
    void setNome() {
    }

    @Test
    void setCpf() {
    }

    @Test
    void setCnpj() {
    }

    @Test
    void setTelefone() {
    }

    @Test
    void setEndereco() {
    }

    @Test
    void setDataHora() {
    }

    @Test
    void setContas() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }

    @Test
    void builder() {
    }
}
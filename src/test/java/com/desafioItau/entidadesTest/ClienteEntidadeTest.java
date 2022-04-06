package com.desafioItau.entidadesTest;

import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.repositorys.ClienteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)  //junit5
@ActiveProfiles("test")
@DataJpaTest
public class ClienteEntidadeTest {

    @Autowired
    TestEntityManager testEntityManager;

    @MockBean
    ClienteRepository clienteRepository;

//    @BeforeEach
//    public void setUp(){
//
//    }

    @Test
    @DisplayName("deve criar um cliente!")
    public void criarCliente() {
        ClienteEntidade clienteEntidade = ClienteEntidade.builder().id(1L).cpf("123")
                .telefone("12345678").endereco("rua pedro").build();


        Assertions.assertThat(clienteEntidade.getId()).isNotNull();
        Assertions.assertThat(clienteEntidade.getCpf()).isEqualTo("123");


    }
}

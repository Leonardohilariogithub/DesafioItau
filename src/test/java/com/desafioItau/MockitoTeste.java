package com.desafioItau;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)  //junit5
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class MockitoTeste {

    @Autowired
    MockMvc mockMvc;

    @Mock
    List<String> lista;

    @BeforeEach  //atualizado no junit 5

    @Test
    @DisplayName("primeiroTeste")
    public void primeiroTesteMockito(){
        Mockito.when(lista.size()).thenReturn(2);

        int size = 0;
        if(1 == 2){
            size = lista.size();
            size = lista.size();
        }

        //Assertions.assertThat(size).isEqualTo(2);
        Mockito.verify(lista, Mockito.never()).size();

    }
}

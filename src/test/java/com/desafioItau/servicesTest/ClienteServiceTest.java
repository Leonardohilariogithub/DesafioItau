package com.desafioItau.servicesTest;

import com.desafioItau.services.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)  //junit5
@ActiveProfiles("test")
public class ClienteServiceTest {

    ClienteService clienteService;

    @Test
    @DisplayName("Deve salvar um cliente")
    public void deveSalvar(){
        //cenario


        //execucao


        //verificacao

    }

}

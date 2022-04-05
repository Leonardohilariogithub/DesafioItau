package com.desafioItau;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)  //junit5
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class MockitoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("deve criar um livro")
    public void criarTeste1() {


    }
}

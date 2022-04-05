package com.desafioItau.controllersTest;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)  //junit5
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class clienteControllerTest {

    static String bookApi = "/clientes";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClienteService clienteService;

    @Test
    @DisplayName("Deve criar cliente com sucesso!")
    public void criarClienteTest() throws Exception{

        ClienteDto clienteDto = new ClienteDto();

        String json = new ObjectMapper().writeValueAsString(clienteDto);  //recebe objeto e retorna JSON

        MockHttpServletRequestBuilder resquest = MockMvcRequestBuilders   //Requisição
                .post(bookApi)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(resquest)                                      //vai receber a Requisição
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(101))
                .andExpect(jsonPath("nome").value(clienteDto.getNome()))
                .andExpect(jsonPath("cpf").value(clienteDto.getCpf()))
                .andExpect(jsonPath("cnpj").value(clienteDto.getCnpj()))
                .andExpect(jsonPath("telefone").value(clienteDto.getTelefone()))
                .andExpect(jsonPath("endereco").value(clienteDto.getEndereco()))

        ;

    }
}

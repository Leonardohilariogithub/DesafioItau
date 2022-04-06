package com.desafioItau.controllersTest;

import com.desafioItau.services.ClienteService;
import com.desafioItau.services.ContaService;
import com.desafioItau.services.OperacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)  //junit5
@ActiveProfiles("test")   //rodar no perfil de teste
@WebMvcTest
@AutoConfigureMockMvc
public class clienteControllerTest {

    //static String bookApi = "/clientes";

    @Autowired
    MockMvc mockMvc;                 // simulando requisição para API

//    @Autowired
//    TestEntityManager testEntityManager;
//
    @MockBean
    ClienteService clienteService;

    @MockBean
    ContaService contaService;

    @MockBean
    OperacaoService operacaoService;

    @MockBean
    ModelMapper modelMapper;

    @Test
    @DisplayName("Deve criar um cliente com sucesso!")
    public void criarClienteTest() throws Exception{

//        ClienteDto clienteDto = new ClienteDto();
//
//        String json = new ObjectMapper().writeValueAsString(clienteDto);  //recebe objeto e retorna JSON
//
//        MockHttpServletRequestBuilder resquest = MockMvcRequestBuilders   //Requisição
//                .post(bookApi)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(json);
//
//        mockMvc
//                .perform(resquest)                                      //vai receber a Requisição
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("id").value(101))
//                .andExpect(jsonPath("nome").value(clienteDto.getNome()))
//                .andExpect(jsonPath("cpf").value(clienteDto.getCpf()))
//                .andExpect(jsonPath("cnpj").value(clienteDto.getCnpj()))
//                .andExpect(jsonPath("telefone").value(clienteDto.getTelefone()))
//                .andExpect(jsonPath("endereco").value(clienteDto.getEndereco()))
//
//        ;

    }
}

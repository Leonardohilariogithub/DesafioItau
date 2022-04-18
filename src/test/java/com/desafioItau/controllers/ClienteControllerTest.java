package com.desafioItau.controllers;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.services.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.BeanUtils;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

import static com.desafioItau.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)  //junit5
@ActiveProfiles("test")   //rodar no perfil de teste
@MockitoSettings(strictness = Strictness.LENIENT)
class ClienteControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("Deve salvar cliente com sucesso")
    void salvarCliente() {
        //cenario
        Mockito.when(clienteService.criarCliente(any())).thenReturn(mockClienteEntidade());

        //ação ou execução
        var result = clienteController.salvarCliente(mockClienteDto());

        //verificação
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(mockClienteEntidade(), result.getBody());
    }

    @Test
    @DisplayName("Deve listar clientes com sucesso")
    void listarCliente() {
        //cenario
        Mockito.when(clienteService.findAll()).thenReturn(List.of(mockClienteEntidade()));

        //ação ou execução
        var result = clienteController.listarCliente();

        //verificação
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals("leonardo", result.getBody().get(0).getNome()); //verificando o nome
        Assertions.assertEquals("045.371.833-73", result.getBody().get(0).getCpf());
        Assertions.assertEquals("14.640.118/0001-99", result.getBody().get(0).getCnpj());
        Assertions.assertEquals("123456789", result.getBody().get(0).getTelefone());
        Assertions.assertEquals("centro", result.getBody().get(0).getEndereco());
    }

    @Test
    @DisplayName("Deve obter clientes com sucesso com CPF")
    void obterCliente() {

        Mockito.when(clienteService.obter(any())).thenReturn(mockClienteEntidade());

        var result = clienteController.obterCliente("045.371.833-73");

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
    }

    @Test
    @DisplayName("Deve falhar Cliente ao criar com CPF")
    void deveFalharAoObterCliente() throws Exception {

        Mockito.when(clienteService.obter(any())).thenThrow(ClienteExistenteException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/obterCliente/?cpf=045.371.833-73"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve obter clientes com sucesso com CNPJ")
    void obterClienteCnpj() {
        Mockito.when(clienteService.obterCnpj(any())).thenReturn(mockClienteEntidade());

        var result = clienteController.obterClienteCnpj("14.640.118/0001-99");

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
    }

    @Test
    @DisplayName("Deve falhar Cliente ao criar com CNPJ")
    void deveFalharAoObterClienteCnpj() throws Exception {

        Mockito.when(clienteService.obterCnpj(any())).thenThrow(ClienteExistenteException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/obterClienteCnpj/?cnpj=14.640.118/0001-99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar Cliente ao criar com CPF")
    void atualizarCliente() throws Exception {
        ClienteDto clienteDto = mockClienteDto();
        ClienteEntidade cliente = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, cliente);

        when(clienteService.atualizar(cliente, "045.371.833.73")).thenReturn(cliente);

        mockMvc.perform(put("/clientes/atualizar/?cpf=045.371.833.73").contentType("application/json").content(asJsonString(clienteDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(cliente.getNome())))
                .andExpect(jsonPath("$.cpf", is(cliente.getCpf())))
                .andExpect(jsonPath("$.telefone", is(cliente.getTelefone())))
                .andExpect(jsonPath("$.endereco", is(cliente.getEndereco())));
    }

    @Test
    @DisplayName("Deve atualizar Cliente ao criar com CNPJ")
    void atualizarClienteCnpj() throws Exception {
        ClienteDto clienteDto = mockClienteDto();
        ClienteEntidade cliente = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, cliente);

        when(clienteService.atualizarCnpj(cliente, "14.640.118/0001-99")).thenReturn(cliente);

        mockMvc.perform(put("/clientes/atualizarCnpj/?cnpj=14.640.118/0001-99").contentType("application/json").content(asJsonString(clienteDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(cliente.getNome())))
                .andExpect(jsonPath("$.cnpj", is(cliente.getCnpj())))
                .andExpect(jsonPath("$.telefone", is(cliente.getTelefone())))
                .andExpect(jsonPath("$.endereco", is(cliente.getEndereco())));
    }

    @Test
    @DisplayName("Deve deletar Cliente ao criar com CPF")
    void deletarCliente() throws Exception {
        doNothing().when(clienteService).deletarCliente("045.371.833-73");

        mockMvc.perform(delete("/clientes/deletar/?cpf=045.371.833-73"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve atualizar Cliente ao criar com CNPJ")
    void deletarClienteCnpj() throws Exception {
        doNothing().when(clienteService).deletarClienteCnpj("14.640.118/0001-99");

        mockMvc.perform(delete("/clientes/deletarCnpj/?cnpj=14.640.118/0001-99"))
                .andExpect(status().isNoContent());
    }

    private ClienteEntidade mockClienteEntidade() {
        return ClienteEntidade.builder()
                .id(1L).nome("leonardo").cpf("045.371.833-73").cnpj("14.640.118/0001-99")
                .telefone("123456789").endereco("centro").build();
    }

    private ClienteDto mockClienteDto() {
        return ClienteDto.builder()
                .id(1L).nome("leonardo").cpf("045.371.833-73").cnpj("14.640.118/0001-99")
                .telefone("123456789").endereco("centro").build();
    }
}
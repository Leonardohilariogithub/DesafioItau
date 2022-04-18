package com.desafioItau.controllers;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.services.ContaService;
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

import java.math.BigDecimal;
import java.util.List;

import static com.desafioItau.enums.EnumTipoDaConta.PESSOA_FISICA;
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
class ContaControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ContaController contaController;

    @Mock
    private ContaService contaService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("Deve criar conta com CPF")
    void criarConta() {
        //cenario
        Mockito.when(contaService.criarConta(any())).thenReturn(mockContaEntidade());

        //ação ou execução
        var result = contaController.criarConta(mockContaDto());

        //verificação
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(mockContaEntidade(), result.getBody());
    }

    @Test
    @DisplayName("Deve listar contas")
    void listarConta() {
        //cenario
        Mockito.when(contaService.findAll()).thenReturn(List.of(mockContaEntidade()));

        //ação ou execução
        var result = contaController.listarConta();

        //verificação
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals("1234", result.getBody().get(0).getAgencia()); //verificando o nome
    }

    @Test
    @DisplayName("Deve obter conta com numero da conta")
    void obterConta() {
        Mockito.when(contaService.obter(any())).thenReturn(mockContaEntidade());

        var result = contaController.obterConta("55555");

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
    }

    @Test
    @DisplayName("Deve falhar ao criar conta sem CPF")
    void deveFalharAoObterConta() throws Exception {

        Mockito.when(contaService.obter(any())).thenThrow(ClienteExistenteException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/obterConta/?numeroDaConta=55557"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve obter conta com numero do CPF")
    void obterCpf() {
        Mockito.when(contaService.obter(any())).thenReturn(mockContaEntidade());

        var result = contaController.obterCpf("045.371.833-73");

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
    }

    @Test
    @DisplayName("Deve obter conta com numero do CNPJ")
    void buscarCnpj() {
        Mockito.when(contaService.obter(any())).thenReturn(mockContaEntidade());

        var result = contaController.buscarCnpj("14.640.118/0001-99");

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
    }

    @Test
    @DisplayName("Deve atualizar conta com numero da conta")
    void atualizarConta() throws Exception {
        ContaDto contaDto = mockContaDto();
        ContaEntidade conta = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, conta);

        when(contaService.atualizar(conta, "55555")).thenReturn(conta);

        mockMvc.perform(put("/contas/atualizar/?numeroDaConta=55555").contentType("application/json").content(asJsonString(contaDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agencia", is(conta.getAgencia())))
                .andExpect(jsonPath("$.numeroDaConta", is(conta.getNumeroDaConta())))
                //.andExpect(jsonPath("$.tipoDaConta", is(conta.getTipoDaConta(PESSOA_FISICA))))
                .andExpect(jsonPath("$.digitoVerificador", is(conta.getDigitoVerificador())))
                .andExpect(jsonPath("$.clienteCpf", is(conta.getClienteCpf())))
                .andExpect(jsonPath("$.clienteCnpj", is(conta.getClienteCnpj())))
                //.andExpect(jsonPath("$.saldo", is(conta.getSaldo())))
                .andExpect(jsonPath("$.saqueSemTaxa", is(conta.getSaqueSemTaxa())))
                .andExpect(jsonPath("$.aviso", is(conta.getAviso())));

    }

    @Test
    @DisplayName("Deve deletar conta com numero da conta")
    void deletarConta() throws Exception {
        doNothing().when(contaService).deletarConta("55555");

        mockMvc.perform(delete("/contas/deletar/?numeroDaConta=55555"))
                .andExpect(status().isNoContent());
    }

    private ContaEntidade mockContaEntidade() {
        return ContaEntidade.builder()
                .id(1L).agencia("1234").numeroDaConta("55555").tipoDaConta(PESSOA_FISICA)
                .digitoVerificador(7).clienteCpf("045.371.833-73").clienteCnpj("14.640.118/0001-99")
                .saldo(BigDecimal.valueOf(0)).saqueSemTaxa(5).aviso("Sucesso").build();
    }

    private ContaDto mockContaDto() {
        return ContaDto.builder()
                .id(1L).agencia("1234").numeroDaConta("55555").tipoDaConta(PESSOA_FISICA)
                .digitoVerificador(7).clienteCpf("045.371.833-73").clienteCnpj("14.640.118/0001-99")
                .saldo(BigDecimal.valueOf(0)).saqueSemTaxa(5).aviso("Sucesso").build();
    }
}
package com.desafioItau.services;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.repositorys.ClienteRepository;
import com.desafioItau.repositorys.ContaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.desafioItau.enums.EnumTipoDaConta.PESSOA_FISICA;
import static com.desafioItau.enums.EnumTipoDaConta.PESSOA_JURIDICA;
import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ContaServiceTest {

    @InjectMocks                            //vai criar instancia real pq vou testar os metodos da class
    private ContaService contaService;

    @Mock                                    //vai criar uma instancia de mentira/simular acesso ao banco
    private ContaRepository contaRepository;

    @Mock
    private ContaEntidade contaEntidade;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve salvar uma conta com cpf")
    void criarConta() {
        //cenario
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").tipoDaConta(PESSOA_FISICA).clienteCpf("04537183373").build();

        ClienteEntidade cliente = ClienteEntidade.builder().id(1L).nome("leo").cpf("04537183373").cnpj(null).telefone("centro").endereco("centro").build();

        when(clienteRepository.findClienteByCpf("04537183373")).thenReturn(cliente);
        when(clienteRepository.findClienteByCnpj(null)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        ContaEntidade contaSalva = contaService.criarConta(contaDto);

        assertEquals(contaSalva.getAgencia(), "1515");
        assertEquals(contaSalva.getTipoDaConta(), PESSOA_FISICA);
        assertEquals(contaSalva.getSaqueSemTaxa(),5);
    }

    @Test
    @DisplayName("Deve salvar as contas")
    void save() {
        when(contaRepository.findAll()).thenReturn(List.of(contaEntidade));

        List<ContaEntidade> lista = contaService.findAll();

        assertNotNull(lista);
        assertEquals(1, lista.size());   //apenas um usuario
    }

    @Test
    @DisplayName("Deve listar as contas")
    void findAll() {
        when(contaRepository.findAll()).thenReturn(List.of(contaEntidade));

        List<ContaEntidade> lista = contaService.findAll();

        assertNotNull(lista);
        assertEquals(1, lista.size());   //apenas um usuario
    }

    @Test
    @DisplayName("Deve obter as contas pelo numero da conta")
    void obter() {
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").numeroDaConta("12345").tipoDaConta(PESSOA_FISICA)
                .digitoVerificador(5).clienteCpf("045.371.833.-73").saldo(BigDecimal.valueOf(0))
                .saqueSemTaxa(5).aviso("conta criada com sucesso").build();

        ContaEntidade contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);

        when(contaRepository.findContaByNumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(contaEntidade);

        assertEquals(contaService.obter(contaDto.getNumeroDaConta()), contaEntidade);
    }

    @Test
    @DisplayName("Deve atualizar as contas")
    void atualizar() {
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").numeroDaConta("12345").tipoDaConta(PESSOA_FISICA)
                .digitoVerificador(5).clienteCpf("045.371.833.-73").saldo(BigDecimal.valueOf(0))
                .saqueSemTaxa(5).aviso("conta criada com sucesso").build();

        ContaEntidade contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);

        when(contaRepository.findContaByNumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(contaEntidade);
        when(contaRepository.save(contaEntidade)).thenReturn(contaEntidade);

        assertEquals(contaDto.getNumeroDaConta(), contaEntidade.getNumeroDaConta());
    }

    @Test
    @DisplayName("Deve obter os contas pelo ID")
    void findById() {
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").numeroDaConta("12345").tipoDaConta(PESSOA_FISICA)
                .digitoVerificador(5).clienteCpf("045.371.833.-73").saldo(BigDecimal.valueOf(0))
                .saqueSemTaxa(5).aviso("conta criada com sucesso").build();

        ContaEntidade contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);

        when(contaRepository.findById(contaDto.getId())).thenReturn(Optional.of(contaEntidade));
    }

    @Test
    @DisplayName("Deve deletar os contas pelo numero da conta")
    void deletarConta() {
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").numeroDaConta("12345").tipoDaConta(PESSOA_FISICA)
                .digitoVerificador(5).clienteCpf("045.371.833.-73").saldo(BigDecimal.valueOf(0))
                .saqueSemTaxa(5).aviso("conta criada com sucesso").build();

        ContaEntidade contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);

        when(contaRepository.findContaByNumeroDaConta(contaDto.getNumeroDaConta())).thenReturn(contaEntidade);
        doNothing().when(contaRepository).delete(contaEntidade);
        contaService.deletarConta(contaEntidade.getNumeroDaConta());

        verify(contaRepository, times(1)).delete(contaEntidade);
    }

    @Test
    void buscarCpf() {
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").numeroDaConta("12345").tipoDaConta(PESSOA_FISICA)
                .digitoVerificador(5).clienteCpf("045.371.833.-73").saldo(BigDecimal.valueOf(0))
                .saqueSemTaxa(5).aviso("conta criada com sucesso").build();

        ContaEntidade contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);

        ClienteEntidade cliente = ClienteEntidade.builder().id(1L).nome("leo").cpf("045.371.833.-73")
                .cnpj(null).endereco("centro").telefone("centro").build();

        when(contaRepository.findByClienteId(contaDto.getId())).thenReturn(Collections.singletonList(contaEntidade));
        when(clienteRepository.findClienteByCpf(contaDto.getClienteCpf())).thenReturn(cliente);
        List<ContaEntidade> contaLista = contaService.buscarCpf(contaEntidade.getClienteCpf());

        assertThat(contaLista, Matchers.is(Matchers.not(empty())));
        assertThat(contaLista.get(0), Matchers.is(equalTo(contaEntidade)));
      }

    @Test
    void buscarCnpj() {
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").numeroDaConta("12345").tipoDaConta(PESSOA_JURIDICA)
                .digitoVerificador(5).clienteCnpj("48.594.743/0001-63").saldo(BigDecimal.valueOf(0))
                .saqueSemTaxa(5).aviso("conta criada com sucesso").build();

        ContaEntidade contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);

        ClienteEntidade cliente = ClienteEntidade.builder().id(1L).nome("leo").cnpj("48.594.743/0001-63")
                .cnpj(null).endereco("centro").telefone("centro").build();

        when(contaRepository.findByClienteId(contaDto.getId())).thenReturn(Collections.singletonList(contaEntidade));
        when(clienteRepository.findClienteByCnpj(contaDto.getClienteCnpj())).thenReturn(cliente);
        List<ContaEntidade> contaLista = contaService.buscarCnpj(contaEntidade.getClienteCnpj());

        assertThat(contaLista, Matchers.is(Matchers.not(empty())));
        assertThat(contaLista.get(0), Matchers.is(equalTo(contaEntidade)));
    }
}
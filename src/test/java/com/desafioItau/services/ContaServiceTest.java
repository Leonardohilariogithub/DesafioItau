package com.desafioItau.services;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.repositorys.ContaRepository;
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
import java.util.List;
import java.util.Optional;

import static com.desafioItau.enums.EnumTipoDaConta.PESSOA_FISICA;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("Deve salvar uma conta com cpf")
    void criarConta() {
        //cenario
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").numeroDaConta("12345").tipoDaConta(PESSOA_FISICA)
                .digitoVerificador(5).clienteCpf("045.371.833.-73").saldo(BigDecimal.valueOf(0))
                .saqueSemTaxa(5).aviso("conta criada com sucesso").build();

        ContaEntidade contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);

        //execucao
        when(contaRepository.save(contaEntidade)).thenReturn(contaEntidade);

        ContaEntidade contaSalva = contaService.criarConta(contaDto);

        //verificacao
        assertThat(contaSalva.getId()).isNotNull();
        assertThat(contaSalva.getAgencia()).isEqualTo("1515");
        assertThat(contaSalva.getNumeroDaConta()).isEqualTo("12345");
        assertThat(contaSalva.getTipoDaConta()).isEqualTo(PESSOA_FISICA);
        assertThat(contaSalva.getDigitoVerificador()).isEqualTo(5);
        assertThat(contaSalva.getClienteCpf()).isEqualTo("045.371.833.-73");
        assertThat(contaSalva.getSaldo()).isEqualTo(0);
        assertThat(contaSalva.getSaqueSemTaxa()).isEqualTo(5);
        assertThat(contaSalva.getAviso()).isEqualTo("conta criada com sucesso");
        verify(contaRepository, times(1)).findContaByNumeroDaConta(contaEntidade.getNumeroDaConta());
        verify(contaRepository, times(1)).save(contaEntidade);
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

        when(contaRepository.findClienteByNumeroDaConta(contaDto.getClienteCpf())).thenReturn(contaEntidade);

        assertEquals(contaService.buscarCpf(contaDto.getClienteCpf()), contaEntidade);
    }

    @Test
    void buscarCnpj() {
    }
}
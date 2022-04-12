package com.desafioItau.services;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.repositorys.ClienteRepository;
import com.desafioItau.repositorys.ContaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

import static com.desafioItau.enums.EnumTipoDaConta.PESSOA_FISICA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaServiceTest {

    @InjectMocks                                 //vai criar instancia real pq vou testar os metodos da class
    private ContaService contaService;

    @Mock                                     //vai criar uma instancia de mentira/simular acesso ao banco
    private ContaRepository contaRepository;

    @Mock
    ClienteEntidade clienteEntidade;

    @Test
    @DisplayName("Deve salvar uma conta com cpf")
    void criarConta() {
        ContaDto contaDto = ContaDto.builder().id(1L).agencia("1515").numeroDaConta("12345")
                .tipoDaConta(PESSOA_FISICA).digitoVerificador(2).clienteCpf("04537183373")
                .saldo(BigDecimal.valueOf(0)).saqueSemTaxa(5).aviso("a").build();

        ContaEntidade contaEntidade = new ContaEntidade();
        BeanUtils.copyProperties(contaDto, contaEntidade);

        when(contaRepository.save(contaEntidade)).thenReturn(contaEntidade);

        ContaEntidade contaSalva = contaService.criarConta(contaDto);

        //verificacao
        assertThat(contaSalva.getId()).isNotNull();
        assertThat(contaSalva.getAgencia()).isEqualTo("1515");
        assertThat(contaSalva.getNumeroDaConta()).isEqualTo("12345");
        assertThat(contaSalva.getTipoDaConta()).isEqualTo(PESSOA_FISICA);
        assertThat(contaSalva.getDigitoVerificador()).isEqualTo(2);
        assertThat(contaSalva.getClienteCpf()).isEqualTo("04537183373");
        verify(contaRepository, times(1)).findClienteByNumeroDaConta(contaEntidade.getClienteCpf());
        verify(contaRepository, times(1)).save(contaEntidade);  //Qantas vezes foi verificado porque nao dar pra ver o retorno ou nao tem retorno
    }

    @Test
    void save() {
    }

    @Test
    void findAll() {
    }

    @Test
    void obter() {
    }

    @Test
    void atualizar() {
    }

    @Test
    void findById() {
    }

    @Test
    void deletarConta() {
    }

    @Test
    void buscarCpf() {
    }

    @Test
    void buscarCnpj() {
    }
}
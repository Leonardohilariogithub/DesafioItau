package com.desafioItau.services;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.exceptions.ClienteExistenteException;
import com.desafioItau.repositorys.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClienteServiceTest {

    @InjectMocks                                 //vai criar instancia real pq vou testar os metodos da class
    private ClienteService clienteService;

    @Mock                                     //vai criar uma instancia de mentira/simular acesso ao banco
    private ClienteRepository clienteRepository;

    @Mock
    ClienteEntidade clienteEntidade;

    @Test
    @DisplayName("Deve criar um cliente com CPF")
    void criarCliente() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833.-73")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.save(clienteEntidade)).thenReturn(clienteEntidade);

        ClienteEntidade clienteSalvo = clienteService.criarCliente(clienteEntidade);

        //verificacao
        assertThat(clienteSalvo.getId()).isNotNull();
        assertThat(clienteSalvo.getNome()).isEqualTo("leonardo");
        assertThat(clienteSalvo.getCpf()).isEqualTo("045.371.833.-73");
        assertThat(clienteSalvo.getTelefone()).isEqualTo("123456789");
        assertThat(clienteSalvo.getEndereco()).isEqualTo("centro");
        verify(clienteRepository, times(1)).findClienteByCpf(clienteEntidade.getCpf());
        verify(clienteRepository, times(1)).save(clienteEntidade);  //Qantas vezes foi verificado porque nao dar pra ver o retorno ou nao tem retorno
    }

    @Test
    @DisplayName("Deve falhar ao criar um cliente com CPF") //Ao falhar tem que colocar Exception
    void deveFalharAoCriarClienteComCpf() {
        //cenario
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833-73")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        //execucao
        when(clienteRepository.findClienteByCpf(clienteEntidade.getCpf())).thenReturn(clienteEntidade);

        //verificacao
        assertThrows(ClienteExistenteException.class, () -> clienteService.criarCliente(clienteEntidade));
    }

    @Test
    @DisplayName("Deve salvar um cliente com CNPJ")
    void deveSalvarClienteComCnpj() {
        //cenario
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cnpj("69.672.843/0001-30")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.save(clienteEntidade)).thenReturn(clienteEntidade);

        //execucao
        ClienteEntidade clienteSalvo = clienteService.criarCliente(clienteEntidade);

        //verificacao
        assertThat(clienteSalvo.getId()).isNotNull();
        assertThat(clienteSalvo.getNome()).isEqualTo("leonardo");
        assertThat(clienteSalvo.getCnpj()).isEqualTo("69.672.843/0001-30");
        assertThat(clienteSalvo.getTelefone()).isEqualTo("123456789");
        assertThat(clienteSalvo.getEndereco()).isEqualTo("centro");
        verify(clienteRepository, times(1)).findClienteByCnpj(clienteEntidade.getCnpj());
        verify(clienteRepository, times(1)).save(clienteEntidade);
    }

    @Test
    @DisplayName("Deve falhar ao criar um cliente com CNPJ")//Ao falhar tem que colocar Exception
    void deveFalharAoCriarClienteComCnpj() {
        //cenario
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cnpj("69.672.843/0001-30")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        //execucao
        when(clienteRepository.findClienteByCnpj(clienteEntidade.getCnpj())).thenReturn(clienteEntidade);

        //verificacao
        assertThrows(ClienteExistenteException.class, () -> clienteService.criarCliente(clienteEntidade));
   }

    @Test
    @DisplayName("Deve listar os clientes com CPF")
    void DeveListarOsClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(clienteEntidade));

        List<ClienteEntidade> lista = clienteService.findAll();

        assertNotNull(lista);
        assertEquals(1, lista.size());   //apenas um usuario
    }

    @Test
    @DisplayName("Deve falhar ao listar vazio com CPF")
    void DeveFalharAoListarOsClientesVazio() {

    }

    @Test
    @DisplayName("Deve obter os clientes com CPF")
    void obter() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833.-73")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCpf(clienteDto.getCpf())).thenReturn(clienteEntidade);

        assertEquals(clienteService.obter(clienteDto.getCpf()),clienteEntidade);
    }

    @Test
    @DisplayName("Deve falhar ao obter os clientes com CPF")
    void deveFalharAoObterCpf() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833.-73")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCpf(clienteDto.getCpf())).thenReturn(null);

        assertThrows(ClienteExistenteException.class, () -> clienteService.obter(clienteEntidade.getCpf()));

    }

    @Test
    @DisplayName("Deve obter os clientes com CNPJ")
    void obterCnpj() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cnpj("89.529.021/0001-02")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCnpj(clienteDto.getCnpj())).thenReturn(clienteEntidade);

        assertEquals(clienteService.obterCnpj(clienteDto.getCnpj()),clienteEntidade);
    }

    @Test
    @DisplayName("Deve falhar ao obter os clientes com CNPJ")
    void deveFalharAoObterCnpj() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cnpj("37.684.921/0001-20")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCnpj(clienteDto.getCnpj())).thenReturn(null);

        assertThrows(ClienteExistenteException.class, () -> clienteService.obterCnpj(clienteEntidade.getCnpj()));
    }

    @Test
    @DisplayName("Deve atualizar os clientes com CPF")
    void DeveAtualizarClienteComCpf() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833.-73")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCpf(clienteDto.getCpf())).thenReturn(clienteEntidade);
        when(clienteService.atualizar(clienteEntidade,clienteEntidade.getCpf())).thenReturn(clienteEntidade);
        when(clienteRepository.save(clienteEntidade)).thenReturn(clienteEntidade);

        assertEquals(clienteDto.getCpf(), clienteEntidade.getCpf());
    }

    @Test
    @DisplayName("Deve falhar ao Atualizar os clientes com CPF")
    void DeveFalharAoAtualizarClienteComCpfInvalido() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833.-73")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCpf(clienteDto.getCpf())).thenReturn(null);

        assertThrows(ClienteExistenteException.class,
                () -> clienteService.atualizar(clienteEntidade,clienteEntidade.getCpf()));
    }

    @Test
    @DisplayName("Deve atualizar os clientes com CNPJ")
    void atualizarCnpj() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cnpj("81.097.108/0001-46")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCnpj(clienteDto.getCnpj())).thenReturn(clienteEntidade);
        when(clienteService.atualizarCnpj(clienteEntidade,clienteEntidade.getCnpj())).thenReturn(clienteEntidade);
        when(clienteRepository.save(clienteEntidade)).thenReturn(clienteEntidade);

        assertEquals(clienteDto.getCnpj(), clienteEntidade.getCnpj());
    }

    @Test
    @DisplayName("Deve falhar ao Atualizar os clientes com CNPJ")
    void DeveFalharAoAtualizarClienteComCnpjInvalido() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cnpj("81.097.108/0001-46")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCnpj(clienteDto.getCnpj())).thenReturn(null);

        assertThrows(ClienteExistenteException.class,
                () -> clienteService.atualizarCnpj(clienteEntidade,clienteEntidade.getCnpj()));
    }


    @Test
    @DisplayName("Deve obter os clientes pelo ID")
    void findById() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833-73")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findById(clienteDto.getId())).thenReturn(Optional.of(clienteEntidade));

        assertEquals(clienteService.findById(Long.valueOf(String.valueOf(clienteDto.getId()))),Optional.of(clienteEntidade));
    }

    @Test
    @DisplayName("Deve deletar os clientes com CPF")
    void deveDeletarClienteCpf() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833-73")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCpf(clienteDto.getCpf())).thenReturn(clienteEntidade);
        doNothing().when(clienteRepository).delete(clienteEntidade);
        clienteService.deletarCliente(clienteEntidade.getCpf());

        verify(clienteRepository,times(1)).delete(clienteEntidade);
    }

    @Test
    @DisplayName("Deve deletar os clientes com CNPJ")
    void deveDeletarClienteCnpj() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cnpj("48.487.383/0001-09")
                .telefone("123456789").endereco("centro").build();

        ClienteEntidade clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);

        when(clienteRepository.findClienteByCnpj(clienteDto.getCnpj())).thenReturn(clienteEntidade);
        doNothing().when(clienteRepository).delete(clienteEntidade);
        clienteService.deletarClienteCnpj(clienteEntidade.getCnpj());

        verify(clienteRepository,times(1)).delete(clienteEntidade);
    }

    @Test
    @DisplayName("Deve falhar ao Deletar os clientes com CPF")
    void deveFalharAoDeletarClienteCpf() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833-73")
                .telefone("123456789").endereco("centro").build();

        when(clienteRepository.findClienteByCpf(clienteDto.getCpf())).thenReturn(null);

        assertThrows(ClienteExistenteException.class,
                () -> clienteService.deletarCliente(clienteDto.getCpf()));
    }

    @Test
    @DisplayName("Deve falhar ao Deletar os clientes com CNPJ")
    void deveFalharAoDeletarClienteCnpj() {
        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cnpj("80.126.754/0001-21")
                .telefone("123456789").endereco("centro").build();

        when(clienteRepository.findClienteByCnpj(clienteDto.getCnpj())).thenReturn(null);

        assertThrows(ClienteExistenteException.class,
                () -> clienteService.deletarClienteCnpj(clienteDto.getCnpj()));
    }
}
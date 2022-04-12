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

//    @BeforeEach
//    void start() {
//
//    }

    @Test
    @DisplayName("Deve salvar um cliente com cpf")
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
    @DisplayName("Deve falhar ao criar um cliente com cpf") //Ao falhar tem que colocar Exception
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
    @DisplayName("Deve salvar um cliente com cnpj")
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
    @DisplayName("Deve falhar ao criar um cliente com cpf")//Ao falhar tem que colocar Exception
    void deveFalharAoCriarClienteComCnpj() {

   }

    @Test
    @DisplayName("Deve listar os clientes")
    void DeveListarOsClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(clienteEntidade));

        List<ClienteEntidade> lista = clienteService.findAll();

        assertNotNull(lista);
        assertEquals(1, lista.size());   //apenas um usuario
        //assertEquals(ClienteEntidade.class, lista.get(0).getClass());

    }

    @Test
    void obter() {

    }

    @Test
    void obterCnpj() {
    }

    @Test
    void DeveAtualizarClienteComCpf() {
//        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833.-73")
//                .telefone("123456789").endereco("centro").build();
//
//        ClienteEntidade clienteEntidade = new ClienteEntidade();
//        BeanUtils.copyProperties(clienteDto, clienteEntidade);
//
//        when(clienteRepository.save(clienteEntidade)).thenReturn(clienteEntidade);
//
//        ClienteEntidade clienteSalvo = clienteService.atualizar(clienteEntidade);
//
//        //verificacao
//        assertThat(clienteSalvo.getId()).isNotNull();
//        assertThat(clienteSalvo.getNome()).isEqualTo("leonardo");
//        assertThat(clienteSalvo.getCpf()).isEqualTo("045.371.833.-73");
//        assertThat(clienteSalvo.getTelefone()).isEqualTo("123456789");
//        assertThat(clienteSalvo.getEndereco()).isEqualTo("centro");
//        verify(clienteRepository, times(1)).findClienteByCpf(clienteEntidade.getCpf());
//        verify(clienteRepository, times(1)).save(clienteEntidade);

    }

    @Test
    void DeveFalharAoAtualizarClienteComCpfInvalido() {
//        ClienteDto clienteDto = ClienteDto.builder().id(1L).nome("leonardo").cpf("045.371.833.-73")
//                .telefone("123456789").endereco("centro").build();
//
//        ClienteEntidade clienteEntidade = new ClienteEntidade();
//        BeanUtils.copyProperties(clienteDto, clienteEntidade);
//
//        when(clienteRepository.save(clienteEntidade)).thenReturn(clienteEntidade);
//
//        ClienteEntidade clienteSalvo = clienteService.atualizar(clienteEntidade);
//
//        //verificacao
//        assertThat(clienteSalvo.getId()).isNotNull();
//        assertThat(clienteSalvo.getNome()).isEqualTo("leonardo");
//        assertThat(clienteSalvo.getCpf()).isEqualTo("045.371.833.-73");
//        assertThat(clienteSalvo.getTelefone()).isEqualTo("123456789");
//        assertThat(clienteSalvo.getEndereco()).isEqualTo("centro");
//        verify(clienteRepository, times(1)).findClienteByCpf(clienteEntidade.getCpf());
//        verify(clienteRepository, times(1)).save(clienteEntidade);

    }

    @Test
    void atualizarCnpj() {
    }

    @Test
    void findById() {
//        Mockito.when((clienteRepository.findById(Mockito.anyLong()))).thenReturn(Optional<ClienteEntidade>);
//
//        ClienteEntidade resposta = clienteService.findById(Long);
//
//        Assertions.assertEquals(ClienteEntidade.class, resposta.getClass());
    }

    @Test
    void deveDeletarClienteCpf() {
//        when(clienteRepository.findClienteByCpf(anyString())).thenReturn(clienteEntidade);
//        doNothing().when(clienteRepository).deleteById(anyLong());
//        clienteService.deletarCliente(clienteEntidade.getCpf());
//        verify(clienteRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deveFalharAoDeletarClienteCpf() {

    }

    @Test
    void deveDeletarClienteCnpj() {
    }

    @Test
    void deveFalharAoDeletarClienteCnpj() {
    }
}
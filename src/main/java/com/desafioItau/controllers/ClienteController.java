package com.desafioItau.controllers;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)//pemitir acessar de qualquer fonte
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController{

    private final ClienteService clienteService;

    private final ModelMapper modelMapper;

    @PostMapping(value = "/cadastro")
    public ResponseEntity<Object> salvarCliente(@RequestBody @Valid ClienteDto clienteDto){
        if(clienteService.existsByCpf(clienteDto.getCpf())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já tem cadastro, por favor verificar!");
        }
        var clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(clienteDto));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteEntidade>> listarCliente(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll(pageable));
    }

//    @GetMapping
//    public ResponseEntity<List<ClienteEntidade>> listarCliente(){
//        List<ClienteEntidade> listarCliente = clienteService.listar();
//        return ResponseEntity.ok(listarCliente);
//    }

    @GetMapping("{id}")
    public ResponseEntity<Object> obterCliente(@PathVariable Long id){
        Optional<ClienteEntidade> clienteEntidadeOptional = clienteService.findById(id);
        if (!clienteEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado ou nao existe!!");
        }
        return ResponseEntity.ok(clienteEntidadeOptional.get());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarCliente(@RequestBody @Valid ClienteEntidade clienteDto, @PathVariable Long id){
        Optional<ClienteEntidade> clienteEntidadeOptional = clienteService.findById(id);
        if (!clienteEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado ou nao existe!!");
        }
        var clienteEntidade = clienteService.atualizar(id, modelMapper.map(clienteDto, ClienteEntidade.class));
        return ResponseEntity.created(null).body(clienteEntidadeOptional.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable Long id){
        Optional<ClienteEntidade> clienteEntidadeOptional = clienteService.findById(id);
        if (!clienteEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado ou nao existe!");
        }
        clienteService.deletarCliente(clienteEntidadeOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com SUCESSO!");
    }

}


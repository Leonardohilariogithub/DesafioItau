package com.desafioItau.controllers;

import com.desafioItau.dtos.ClienteDto;
import com.desafioItau.entidades.ClienteEntidade;
import com.desafioItau.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        var clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(clienteDto));
    }

    @GetMapping
    public ResponseEntity<List<ClienteEntidade>> listarCliente(){
        List<ClienteEntidade> listarCliente = clienteService.listar();
        return ResponseEntity.ok(listarCliente);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> obterCliente(@PathVariable Long id){
        Optional<ClienteEntidade> clienteEntidade = clienteService.obter(id);
        return ResponseEntity.ok(clienteEntidade);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarCliente(@RequestBody @Valid ClienteEntidade clienteDto, @PathVariable Long id){
        Optional<ClienteEntidade> clienteEntidadeOpnal = clienteService.findById(id);
        var clienteEntidade = clienteService.atualizar(id, modelMapper.map(clienteDto, ClienteEntidade.class));
        return ResponseEntity.created(null).body(clienteEntidadeOpnal.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable Long id){
        Optional<ClienteEntidade> clienteEntidadeOptional = clienteService.findById(id);
        clienteService.deletarCliente(clienteEntidadeOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

}


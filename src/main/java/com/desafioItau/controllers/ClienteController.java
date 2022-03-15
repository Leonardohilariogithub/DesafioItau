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

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)//pemitir acessar de qualquer fonte
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController{

    private final ClienteService clienteService;
    private final ModelMapper modelMapper;

    @PostMapping(value = "/cadastro")
    public ResponseEntity<Object> salvarCliente(@RequestBody @Valid ClienteDto clienteDto){
        if(clienteService.existsByDocumento(clienteDto.getDocumento())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ou CNPJ já tem cadastro, por favor verificar!");
        }
        var clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(clienteDto));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteEntidade>> listarCliente(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll(pageable));
    }

    @GetMapping("/obterCliente/")
    public ResponseEntity<?> obterCliente(@RequestParam(name = "documento") String documento){
        ClienteEntidade cliente = clienteService.obter(documento);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @PutMapping("/atualizar/")
    public ResponseEntity<ClienteEntidade> atualizarCliente(@RequestBody @Valid ClienteDto clienteDto, @RequestParam(name = "documento") String documento){
        ClienteEntidade cliente = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, cliente);
        clienteService.atualizar(cliente,documento);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @DeleteMapping("/deletar/")
    public ResponseEntity<?> deletarCliente(@RequestParam(name = "documento") String documento){
        clienteService.deletarCliente(documento);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


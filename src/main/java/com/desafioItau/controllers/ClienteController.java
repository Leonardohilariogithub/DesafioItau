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
//        if(clienteService.existsByDocumento(clienteDto.getDocumento())){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ou CNPJ já tem cadastro, por favor verificar!");
//        }
        var clienteEntidade = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteEntidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(clienteDto));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteEntidade>> listarCliente(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll(pageable));
    }

    @GetMapping("/obterCliente/")
    public ResponseEntity<?> obterCliente(@RequestParam(name = "cpf") String cpf){
        ClienteEntidade cliente = clienteService.obter(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping("/obterClienteCnpj/")
    public ResponseEntity<?> obterClienteCnpj(@RequestParam(name = "cnpj") String cnpj){
        ClienteEntidade clienteCnpj = clienteService.obterCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(clienteCnpj);
    }

    @PutMapping("/atualizar/")
    public ResponseEntity<ClienteEntidade> atualizarCliente(@RequestBody @Valid ClienteDto clienteDto, @RequestParam(name = "cpf") String cpf){
        ClienteEntidade cliente = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, cliente);
        clienteService.atualizar(cliente,cpf);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @PutMapping("/atualizarCnpj/")
    public ResponseEntity<ClienteEntidade> atualizarClienteCnpj(@RequestBody @Valid ClienteDto clienteDto, @RequestParam(name = "cnpj") String cnpj){
        ClienteEntidade clienteCnpj = new ClienteEntidade();
        BeanUtils.copyProperties(clienteDto, clienteCnpj);
        clienteService.atualizarCnpj(clienteCnpj,cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(clienteCnpj);
    }

    @DeleteMapping("/deletar/")
    public ResponseEntity<?> deletarCliente(@RequestParam(name = "cpf") String cpf){
        clienteService.deletarCliente(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletarCnpj/")
    public ResponseEntity<?> deletarClienteCnpj(@RequestParam(name = "cnpj") String cnpj){
        clienteService.deletarClienteCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


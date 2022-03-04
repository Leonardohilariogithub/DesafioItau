package com.desafioItau.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cliente")
public class ClienteEntidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)// Gerar automaticamente
    private Long id; // UUID - identificador Universal indicado para projeto Microservices

    @Column(length = 35)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, length = 35)
    private String endereco;

    @Column(nullable = false)
    private LocalDateTime registro;// regra que vou colocar

    public ClienteEntidade(String nome, String cpf, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }
}

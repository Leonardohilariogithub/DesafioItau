package com.desafioItau.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class ClienteEntidade {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 35)
    private String nome;

    @Column(nullable = false,unique = true, length = 11)
    private String cpf;

    @Column(nullable = false,unique = true, length = 35)
    private double telefone;

    @Column(nullable = false, length = 35)
    private String endereço;

    @Column(nullable = false)
    private LocalDateTime registro;
}

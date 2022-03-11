package com.desafioItau.entidades;

import com.desafioItau.enums.EnumTipoPessoaCpfOuCnpj;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tb_cliente")
public class ClienteEntidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)// Gerar automaticamente
    private Long id; // UUID - identificador Universal indicado para projeto Microservices
    @Column(length = 35)
    private String nome;

    @Enumerated(EnumType.STRING)
    private EnumTipoPessoaCpfOuCnpj tipoDocumento;

    private String documento;

    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false, length = 35)
    private String endereco;

    private LocalDateTime registro;// regra que vou colocar

}

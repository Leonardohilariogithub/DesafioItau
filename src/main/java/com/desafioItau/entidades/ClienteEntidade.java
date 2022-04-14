package com.desafioItau.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "tb_cliente")
@Builder
@Data
public class ClienteEntidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)// Gerar automaticamente
    private Long id; // UUID - identificador Universal indicado para projeto Microservices

    @Column(nullable = false,length = 35)              //@NotNull
    private String nome;

    private String cpf;

    private String cnpj;

    @Column(nullable = false, length = 35)
    private String telefone;

    @Column(nullable = false, length = 35)
    private String endereco;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR",
            timezone = "America/São_Paulo")
    private LocalDateTime dataHora = LocalDateTime.now();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<ContaEntidade> contas = new ArrayList<>();
}

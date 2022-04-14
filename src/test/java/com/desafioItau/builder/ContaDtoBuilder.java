//package com.desafioItau.builder;
//
//import com.desafioItau.dtos.ContaDto;
//import com.desafioItau.enums.EnumTipoDaConta;
//import lombok.Builder;
//
//import java.math.BigDecimal;
//
//@Builder
//public class ContaDtoBuilder {
//
//    @Builder.Default
//    private Long id;
//
//    @Builder.Default
//    private String agencia;
//
//    @Builder.Default
//    private String numeroDaConta;
//
//    @Builder.Default
//    private EnumTipoDaConta tipoDaConta;
//
//    @Builder.Default
//    private int digitoVerificador;
//
//    @Builder.Default
//    private String clienteCpf;
//
//    @Builder.Default
//    private String clienteCnpj;
//
//    @Builder.Default
//    private BigDecimal saldo;
//
//    @Builder.Default
//    private int saqueSemTaxa;
//
//    @Builder.Default
//    private String aviso;
//
//    public ContaDto toContaDto(){
//        return new ContaDto(id,
//                agencia,
//                numeroDaConta,
//                tipoDaConta,
//                digitoVerificador,
//                clienteCpf,
//                clienteCnpj,
//                saldo,
//                saqueSemTaxa,
//                aviso
//                );
//    }
//}

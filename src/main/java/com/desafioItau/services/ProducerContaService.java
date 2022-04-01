//package com.desafioItau.services;
//
//import com.desafioItau.entidades.ContaEntidade;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.header.Header;
//import org.apache.kafka.common.header.internals.RecordHeader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.stereotype.Service;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@Service
//public class ProducerContaService {
//
//    private static final Logger logger = LoggerFactory.getLogger(ProducerContaService.class);
//    private final String topic;
//
//    @Qualifier(value = "kafkaTemplateConta")
//    private final KafkaTemplate<String, ContaEntidade> kafkaTemplate; //kafkaTemplate é apenas para produzir
//
//    public ProducerContaService(@Value("${topic.name}") String topic, KafkaTemplate<String,
//            ContaEntidade> kafkaTemplate) {
//        this.topic = topic;
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void send(ContaEntidade contaEntidade){
//
//        List<Header> headers = List.of(new RecordHeader(KafkaHeaders.TOPIC,topic.getBytes(StandardCharsets.UTF_8)));
//
//        var producerRecord = new ProducerRecord<String,ContaEntidade>(topic,null,null,null,contaEntidade, headers);
//        kafkaTemplate.send(producerRecord).addCallback(
//                success -> logger.info("Messagem send" +  contaEntidade.getNumeroDaConta()
//                        + contaEntidade.getSaqueSemTaxa()),
//                failure -> logger.info("Message failure" + failure.getMessage()));
//    }
//}




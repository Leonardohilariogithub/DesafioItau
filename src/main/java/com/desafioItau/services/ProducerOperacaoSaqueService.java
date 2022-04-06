package com.desafioItau.services;

import com.desafioItau.entidades.OperacaoEntidade;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class ProducerOperacaoSaqueService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerOperacaoSaqueService.class);
    private final String topic;

    @Qualifier(value = "kafkaTemplateOperacao")
    private final KafkaTemplate<String, OperacaoEntidade> kafkaTemplate; //kafkaTemplate é apenas para produzir

    public ProducerOperacaoSaqueService(@Value("${topic.name}") String topic, KafkaTemplate<String, OperacaoEntidade> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OperacaoEntidade operacaoEntidade) throws ExecutionException, InterruptedException, TimeoutException {

        List<Header> headers = List.of(new RecordHeader(KafkaHeaders.TOPIC,topic.getBytes(StandardCharsets.UTF_8)));

        var producerRecord = new ProducerRecord<String,OperacaoEntidade>("novoSaque",null,null,null,operacaoEntidade, headers);

//        kafkaTemplate.send(producerRecord).addCallback(                          //Mensagem no IntellijIdea
//                success -> logger.info("Messagem send" +  operacaoEntidade.toString()
//                        + operacaoEntidade),
//                failure -> logger.info("Message failure" + failure.getMessage()));

        kafkaTemplate.send(producerRecord).get(10, TimeUnit.SECONDS);     //Tempo de Retorno do Servidor
    }
}

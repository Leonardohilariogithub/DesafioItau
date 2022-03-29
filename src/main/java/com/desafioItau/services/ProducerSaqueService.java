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

@Service
public class ProducerSaqueService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerSaqueService.class);
    private final String topic;
    @Qualifier(value = "kafkaTemplateOperacao")
    private final KafkaTemplate<String, OperacaoEntidade> kafkaTemplate; //kafkaTemplate é apenas para produzir

    public ProducerSaqueService(@Value("${topic.name}") String topic, KafkaTemplate<String, OperacaoEntidade> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OperacaoEntidade operacaoEntidade){

        List<Header> headers = List.of(new RecordHeader(KafkaHeaders.TOPIC,topic.getBytes(StandardCharsets.UTF_8)));

        var producerRecord = new ProducerRecord<String,OperacaoEntidade>(topic,null,null,null,operacaoEntidade, headers);
        kafkaTemplate.send(producerRecord).addCallback(
                success -> logger.info("Messagem send" +  operacaoEntidade.toString()
                        + operacaoEntidade),
                failure -> logger.info("Message failure" + failure.getMessage()));
    }
}

package com.desafioItau.services;

import com.desafioItau.entidades.ContaEntidade;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ProducerContaService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerContaService.class);
    private final String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProducerContaService(@Value("${topic.name}") String topic, KafkaTemplate<String,
            Object> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ContaEntidade contaEntidade){

        List<Header> teste = List.of(new RecordHeader("",topic.getBytes(StandardCharsets.UTF_8)));

        var producerRecord = new ProducerRecord<String,Object>(topic,null,null,null,contaEntidade, teste);
        kafkaTemplate.send(producerRecord).addCallback(
                success -> logger.info("Messagem send" +  contaEntidade.getNumeroDaConta()
                        + contaEntidade.getSaqueSemTaxa()),
                failure -> logger.info("Message failure" + failure.getMessage()));
    }
}




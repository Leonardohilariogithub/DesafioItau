package com.desafioItau.config;

import com.desafioItau.entidades.ContaEntidade;
import com.desafioItau.entidades.OperacaoEntidade;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${topic.name}")
    private String topic;

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(topic, 3,(short) 1) ;
    }

    @Bean(value = "contaProducerFactory")
    public ProducerFactory<String, ContaEntidade> ContaProducerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean(value = "kafkaTemplateConta")
    public KafkaTemplate<String, ContaEntidade> kafkaTemplateConta(@Qualifier(value = "contaProducerFactory") ProducerFactory<String, ContaEntidade> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
    @Bean(value = "operacaoProducerFactory")
    public ProducerFactory<String, OperacaoEntidade> OperacaoProducerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean(value = "kafkaTemplateOperacao")
    public KafkaTemplate<String, OperacaoEntidade> kafkaTemplateOperacao(@Qualifier(value = "operacaoProducerFactory") ProducerFactory<String, OperacaoEntidade> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}

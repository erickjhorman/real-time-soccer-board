package org.demo.kafka.service;

import org.demo.kafka.domain.Match;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, Match> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, Match> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, Match match) {
        kafkaTemplate.send("amigoscode", match);
    }
}

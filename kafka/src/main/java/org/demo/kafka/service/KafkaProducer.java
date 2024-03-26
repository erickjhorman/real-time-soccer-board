package org.demo.kafka.service;

import org.demo.kafka.domain.Match;

public interface KafkaProducer {

    void send(String topic, Match message);
}

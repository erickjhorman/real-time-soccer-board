package org.demo.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.demo.kafka.domain.Match;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

;

@Configuration
public class kafkaConsumerConfig {
    private Map<String, Object> consumerConfig() {
        var props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return props;
    }

    @Bean
    public ConsumerFactory<String, Match> consumerFactory() {
        var deserializer = new JsonDeserializer<Match>();
        deserializer.addTrustedPackages("org.demo.kafka.domain");
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), deserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Match>> messageFactory(ConsumerFactory<String,
            Match> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Match> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}

package org.demo.kafka;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.CompositeProducerInterceptor;
import org.springframework.kafka.support.serializer.JsonSerializer;

class KafkaApplicationDiffblueTest {
    /**
     * Method under test: {@link KafkaApplication#commandLineRunner(KafkaTemplate)}
     */
    @Test
    void testCommandLineRunner() throws Exception {
        // Arrange
        KafkaApplication kafkaApplication = new KafkaApplication();
        ProducerFactory<String, Message> producerFactory = mock(ProducerFactory.class);
        StringSerializer keySerializer = new StringSerializer();
        when(producerFactory.createNonTransactionalProducer())
                .thenReturn(new MockProducer<>(true, keySerializer, new JsonSerializer<>()));
        when(producerFactory.transactionCapable()).thenReturn(true);

        KafkaTemplate<String, Message> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setAllowNonTransactional(true);

        // Act
        kafkaApplication.commandLineRunner(kafkaTemplate).run("Args");

        // Assert
        verify(producerFactory, atLeast(1)).createNonTransactionalProducer();
        verify(producerFactory).transactionCapable();
    }

    /**
     * Method under test: {@link KafkaApplication#commandLineRunner(KafkaTemplate)}
     */
    @Test
    void testCommandLineRunner2() throws Exception {
        // Arrange
        KafkaApplication kafkaApplication = new KafkaApplication();
        ProducerFactory<String, Message> producerFactory = mock(ProducerFactory.class);
        StringSerializer keySerializer = new StringSerializer();
        when(producerFactory.createNonTransactionalProducer())
                .thenReturn(new MockProducer<>(true, keySerializer, new JsonSerializer<>()));
        when(producerFactory.transactionCapable()).thenReturn(true);
        ProducerInterceptor<String, Message> producerInterceptor = mock(ProducerInterceptor.class);
        doNothing().when(producerInterceptor).onAcknowledgement(Mockito.<RecordMetadata>any(), Mockito.<Exception>any());
        when(producerInterceptor.onSend(Mockito.<ProducerRecord<String, Message>>any()))
                .thenReturn(new ProducerRecord<>("Topic", new Message("Not all who wander are lost")));
        CompositeProducerInterceptor<String, Message> producerInterceptor2 = new CompositeProducerInterceptor<>(
                producerInterceptor);

        KafkaTemplate<String, Message> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerInterceptor(producerInterceptor2);
        kafkaTemplate.setAllowNonTransactional(true);

        // Act
        kafkaApplication.commandLineRunner(kafkaTemplate).run("Args");

        // Assert
        verify(producerInterceptor, atLeast(1)).onAcknowledgement(Mockito.<RecordMetadata>any(), Mockito.<Exception>any());
        verify(producerInterceptor, atLeast(1)).onSend(Mockito.<ProducerRecord<String, Message>>any());
        verify(producerFactory, atLeast(1)).createNonTransactionalProducer();
        verify(producerFactory).transactionCapable();
    }

    /**
     * Method under test: {@link KafkaApplication#commandLineRunner(KafkaTemplate)}
     */
    @Test
    void testCommandLineRunner3() throws Exception {
        // Arrange
        KafkaApplication kafkaApplication = new KafkaApplication();
        ProducerFactory<String, Message> producerFactory = mock(ProducerFactory.class);
        StringSerializer keySerializer = new StringSerializer();
        when(producerFactory.createNonTransactionalProducer())
                .thenReturn(new MockProducer<>(false, keySerializer, new JsonSerializer<>()));
        when(producerFactory.transactionCapable()).thenReturn(true);
        ProducerInterceptor<String, Message> producerInterceptor = mock(ProducerInterceptor.class);
        when(producerInterceptor.onSend(Mockito.<ProducerRecord<String, Message>>any()))
                .thenReturn(new ProducerRecord<>("Topic", new Message("Not all who wander are lost")));
        CompositeProducerInterceptor<String, Message> producerInterceptor2 = new CompositeProducerInterceptor<>(
                producerInterceptor);

        KafkaTemplate<String, Message> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerInterceptor(producerInterceptor2);
        kafkaTemplate.setAllowNonTransactional(true);

        // Act
        kafkaApplication.commandLineRunner(kafkaTemplate).run("Args");

        // Assert
        verify(producerInterceptor, atLeast(1)).onSend(Mockito.<ProducerRecord<String, Message>>any());
        verify(producerFactory, atLeast(1)).createNonTransactionalProducer();
        verify(producerFactory).transactionCapable();
    }
}

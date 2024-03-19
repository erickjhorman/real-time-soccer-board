package org.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KaftaListener {
    @KafkaListener(topics = "amigoscode",
                    groupId = "groupId",
                    containerFactory = "messageFactory")
    void listener(Message data) {
      //log.info("Listener received: " + data + "😉");
    }
}

package org.demo.kafka;

import org.demo.kafka.domain.Match;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class KafkaListener {

    private final Logger logger = Logger.getLogger(KafkaListener.class.getName());

    @org.springframework.kafka.annotation.KafkaListener(topics = "amigoscode",
                    groupId = "groupId",
                    containerFactory = "messageFactory")
    void listener(Match data) {
        logger.info("Listener received: "  + data +  "😉");
    }
}

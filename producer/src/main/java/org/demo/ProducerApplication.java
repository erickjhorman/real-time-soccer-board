package org.demo;

import org.demo.kafka.domain.Match;
import org.demo.kafka.service.KafkaProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaProducer kafkaTemplate) {
        return args -> {
            for (Match item: generateComments()) {
                kafkaTemplate.send("amigoscode",item);
            }
        };
    }
    private List<Match> generateComments() {
        String[] comments = new String[]{"Real madrid - Vinicus passes the ball to benzema", "Real madrid - Benzema scores a goal, Gooooooooooool",
                "Barcelona - Barcelona kicks back", "Barcelona Barcelona passes the ball", "Real madrid - Real Madrid scores another goal, Gooooooooooool",
                "Barcelona - Barcelona does not know how to do"};
        var matches = new ArrayList<Match>();
        matches.add(new Match(null, "Match started", 0));

        for (String comment : comments) {
            matches.add(new Match(comment.split("-")[0], comment, comment.contains("Gooooooooooool") ? 1 : null));
        }
        return matches;
    }
}



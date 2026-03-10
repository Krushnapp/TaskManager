package org.example.kafka;


import lombok.RequiredArgsConstructor;
import org.example.dto.TaskEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

    private static final String TOPIC = "task-events";



    public void publish(TaskEvent event) {

        kafkaTemplate.send(TOPIC, event);

        System.out.println("Event sent to Kafka: " + event);
    }
}
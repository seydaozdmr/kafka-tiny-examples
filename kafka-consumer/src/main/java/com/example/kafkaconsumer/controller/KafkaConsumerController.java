package com.example.kafkaconsumer.controller;

import org.apache.coyote.Response;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class KafkaConsumerController {
    private final KafkaConsumer<String,String> consumer;
    private boolean flag=true;

    public KafkaConsumerController(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @GetMapping("/consume")
    public ResponseEntity<String> consumeKafka(@RequestParam("topicName") String topicName){
        consumer.subscribe(Arrays.asList(topicName));
        Duration duration = Duration.ZERO;
        while(flag){
            duration = Duration.ofMillis(1000);
            ConsumerRecords<String,String> records =consumer.poll(duration);
            for(ConsumerRecord<String,String> record:records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            if(duration.compareTo(Duration.ofMillis(1000))==0)
                flag=false;
        }
        return ResponseEntity.ok("Consume finished");
    }


}

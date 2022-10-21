package com.example.kafkaproducer.controller;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {
    private final KafkaProducer<String,String> producer;

    public KafkaProducerController(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    @GetMapping("/start")
    public ResponseEntity<String> startProducer(){
        for(int i =0;i<100;i++){
            producer.send(new ProducerRecord<>("my-topic",Integer.toString(i)));
        }
        //producer.close();
        return ResponseEntity.ok("Producer Started...");
    }
}

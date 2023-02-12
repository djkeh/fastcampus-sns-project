package com.fastcampus.snsproject.producer;

import com.fastcampus.snsproject.model.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmProducer {

    private final KafkaTemplate<Integer, AlarmEvent> alarmEventKafkaTemplate;

    @Value("${spring.kafka.topic.notification}")
    private String topic;

    public void send(AlarmEvent event) {
        alarmEventKafkaTemplate.send(topic, event.getReceiverUserId(), event);
        log.info("send fin");
    }
}

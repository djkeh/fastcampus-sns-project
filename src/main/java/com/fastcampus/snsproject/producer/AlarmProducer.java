package com.fastcampus.snsproject.producer;

import com.fastcampus.snsproject.model.event.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmProducer {

    private final KafkaTemplate<Integer, AlarmEvent> kafkaTemplate;

//    @Value("${spring.kafka.topic.alarm}")
    private String topic;

    public void send(AlarmEvent event){
        kafkaTemplate.send(topic, event.getReceiveUserId(), event);
        log.info("Send to Kafka finished");

        // 강의에 Kafka에 관한 내용이 없습니다.. (Ch04 - 08 학습중.. )
    }

}

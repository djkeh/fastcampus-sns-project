package com.fastcampus.snsproject.consumer;

import com.fastcampus.snsproject.model.event.AlarmEvent;
import com.fastcampus.snsproject.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

//    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack){
        log.info("Consume the event{}", event);

        // 강의에 AlarmService에 관한 내용이 없습니다.. (Ch04 - 08 학습중.. )

    }
}

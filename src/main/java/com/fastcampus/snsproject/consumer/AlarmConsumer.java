package com.fastcampus.snsproject.consumer;

import com.fastcampus.snsproject.model.event.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    //AlarmService 가 강의내용엔 구현되어있지 않아서 구현하지 않았습니다
    //private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack)
    {
        log.info("Consume the event {}", event);
        //alarmService.send(event.getAlarmType(), event.getArgs(), event.getReceiveUserId());

    }
}

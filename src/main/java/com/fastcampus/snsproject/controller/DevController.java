package com.fastcampus.snsproject.controller;

import com.fastcampus.snsproject.model.AlarmArgs;
import com.fastcampus.snsproject.model.AlarmEvent;
import com.fastcampus.snsproject.model.AlarmType;
import com.fastcampus.snsproject.model.entity.UserEntity;
import com.fastcampus.snsproject.producer.AlarmProducer;
import com.fastcampus.snsproject.repository.UserEntityRepository;
import com.fastcampus.snsproject.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-dev/v1")
@RequiredArgsConstructor
public class DevController {

    private final AlarmService notificationService;
    private final UserEntityRepository userEntityRepository;
    private final AlarmProducer alarmProducer;

    @GetMapping("/notification")
    public void test() {
        UserEntity entity = userEntityRepository.findById(5).orElseThrow();
        notificationService.send(AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(0, 0), entity.getId());
    }

    @GetMapping("/send")
    public void send() {
        alarmProducer.send(new AlarmEvent(AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(0, 0), 5));
    }
}

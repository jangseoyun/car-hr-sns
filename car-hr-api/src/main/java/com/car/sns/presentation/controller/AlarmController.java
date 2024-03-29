package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.EmitterUseCase;
import com.car.sns.application.usecase.alarm.AlarmReadUseCase;
import com.car.sns.presentation.model.response.Result;
import com.car.sns.security.CarAppPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmReadUseCase alarmReadUseCase;
    private final EmitterUseCase emitterUseCase;

    @GetMapping("")
    public ResponseEntity<Result> postNewArticleComment(@AuthenticationPrincipal CarAppPrincipal carAppPrincipal,
                                                        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(Result.success(alarmReadUseCase.alarmList(carAppPrincipal.getName(), pageable)));
    }

    @GetMapping(value = "/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal CarAppPrincipal carAppPrincipal,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        SseEmitter sseEmitter = emitterUseCase.connectAlarm(carAppPrincipal.getName());
        return sseEmitter;
    }
}

package com.example.jpa_test.aop;

import com.example.jpa_test.service.KeyService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DbKeyAop {
    final String SET_KEY_EXECUTION = "execution(public * com.example.jpa_test.service.*.*(..))";

    private final KeyService keyService;

    public DbKeyAop(KeyService keyService) {
        this.keyService = keyService;
    }

    @Before(SET_KEY_EXECUTION)
    public void before(JoinPoint joinPoint) {
        if(joinPoint.getTarget().toString().contains("KeyService")) {
            return;
        }
        keyService.setAesKey();
    }

}

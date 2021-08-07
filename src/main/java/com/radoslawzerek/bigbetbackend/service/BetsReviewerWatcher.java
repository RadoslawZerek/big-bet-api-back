package com.radoslawzerek.bigbetbackend.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BetsReviewerWatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(BetsReviewerWatcher.class);

    @Before(value = "execution(* com.radoslawzerek.bigbetbackend.service.BetsReviewer.updateBetsStatus(..))" +
            "&& args(userId) && target(object)", argNames = "userId,object")
    public void logEvent(Long userId, Object object) {
        LOGGER.info("Class: " + object.getClass().getName() + ", userId: " + userId);
    }

    @Around("execution(* com.radoslawzerek.bigbetbackend.service.BetsReviewer.updateBetsStatus(..))")
    public Object measureTime(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        try {
            long begin = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            LOGGER.info("Time consumed: " + (end - begin) + "[ms]");
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage());
            throw throwable;
        }
        return result;
    }
}

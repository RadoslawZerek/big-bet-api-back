package com.radoslawzerek.bigbetbackend.api.theoddsapi.facade;

import com.radoslawzerek.bigbetbackend.dto.BetProspectsRequestDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BetProspectFacadeWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(BetProspectFacadeWatcher.class);

    @Before(value = "execution(* com.radoslawzerek.bigbetbackend.api.theoddsapi.facade.BetProspectFacade.getCurrentBetProspectDtoList(..))" +
            "&& args(request) && target(object)", argNames = "request,object")
    public void logEvent(BetProspectsRequestDto request, Object object) {
        LOGGER.info("Class: " + object.getClass().getName() + ", leagues: " + request.getLeagues());
    }

    @Around("execution(* com.radoslawzerek.bigbetbackend.api.theoddsapi.facade.BetProspectFacade.getCurrentBetProspectDtoList(..))")
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



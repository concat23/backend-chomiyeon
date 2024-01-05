package com.ecosystem.chomiyeon.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.ecosystem.chomiyeon.controller.CmyEmployeeController.*(..))")
    public void logBeforeControllerMethodExecution() {
        logger.info("Controller method is about to be executed.");
    }

    @Before("execution(* com.ecosystem.chomiyeon.controller.CmyEmployeeController.createCmyEmployee(..))")
    public void logBeforeCreateCmyEmployeeMethodExecution() {
        logger.info("Creating CmyEmployee - Controller method is about to be executed.");
    }

    @Before("execution(* com.ecosystem.chomiyeon.controller.CmyEmployeeController.getCmyEmployee(..))")
    public void logBeforeFindByIdCmyEmployeeMethodExecution() {
        logger.info("Finding by CmyEmployee ID - Controller method is about to be executed.");
    }


}

package com.example.SpringProject.aspect;


import com.example.SpringProject.exception.RestControllerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;


@Aspect
@Configuration
public class ExecuteTimeAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("com.example.SpringProject.aspect.JoinPointConfig.logExecutionTimePointcut()")
    public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            if(e instanceof RestControllerException){
                logger.error(e.getMessage());
            }
            throw e;
        }

        long executionTime = System.currentTimeMillis() - startTime;
        logger.info("Elapsed Time is " + executionTime);
        return result;
    }
}

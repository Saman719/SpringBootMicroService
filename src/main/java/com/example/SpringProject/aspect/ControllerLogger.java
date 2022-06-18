package com.example.SpringProject.aspect;

import com.example.SpringProject.SpringProjectApplication;
import com.example.SpringProject.init.MyAppContextAware;
import com.example.SpringProject.model.ActivityLog;
import com.example.SpringProject.model.CardPrintRequest;
import com.example.SpringProject.repository.ActivityLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Configuration
public class ControllerLogger implements MyAppContextAware {

    private ApplicationContext context;

    @Autowired
    ActivityLogRepository logRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Around("com.example.SpringProject.aspect.JoinPointConfig.logMethodPointcut()")
    public Object logBeforeMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
        logger.info("Method : " + joinPoint.getSignature().getName() + " has been invoked with result of : " + result);
        String dateStr = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(new Date());
        Date currentDate = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").parse(dateStr);
        ActivityLog activityLog = context.getBean(ActivityLog.class);
        activityLog.setActivityTime(currentDate);
        activityLog.setPersonnelCode(((CardPrintRequest) joinPoint.getArgs()[0]).getPersonnelCode());
        activityLog.setCardPAN(((CardPrintRequest) joinPoint.getArgs()[0]).getCardPAN());
        activityLog.setActivityDesc(joinPoint.getSignature().getName());
        logRepository.save(activityLog);
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

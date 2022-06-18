package com.example.SpringProject.aspect;


import org.aspectj.lang.annotation.Pointcut;


public class JoinPointConfig {

    @Pointcut("@annotation(com.example.SpringProject.aspect.ExecuteTime)")
    public void logExecutionTimePointcut(){}

    @Pointcut("@annotation(com.example.SpringProject.aspect.LogMethod)")
    public void logMethodPointcut(){}
}
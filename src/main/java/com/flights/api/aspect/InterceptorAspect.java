package com.flights.api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InterceptorAspect {

	private static final Logger logger = LoggerFactory.getLogger(InterceptorAspect.class);

	@Before("@annotation(com.flights.api.annotation.LogExecution)")
	public void intercept(JoinPoint joinPoint) {
		logger.info("AspectJ intercept: " + joinPoint.toShortString());
	}

}

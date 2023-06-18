/**
 * @author Valar Morghulis
 * @Date 2023/5/31
 */
package com.project.smartcharge.system.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 负责给顶层(controller层)记录异常的类
 */
@Component
@Aspect
public class ExceptionAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    @Pointcut("execution(* com.project.smartcharge.controller..*(..))")
    public void ControllerThrowing() {
    }

    @AfterThrowing(value = "ControllerThrowing()", throwing = "exception")
    public void AfterControllerThrowing(Exception exception) {
        logger.error(exception.getMessage());

    }

}

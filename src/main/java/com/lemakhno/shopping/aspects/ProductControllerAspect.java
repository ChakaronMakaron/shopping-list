package com.lemakhno.shopping.aspects;

import java.util.Arrays;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductControllerAspect {

    private Logger logger = Logger.getLogger(ProductControllerAspect.class.getSimpleName());
    
    @Before("com.lemakhno.shopping.aspects.Pointcuts.forProductControllerMethods()")
    public void beforeControllerMethods(JoinPoint joinPoint) {
        logger.info(">>> " + joinPoint.toShortString() + "\n" + Arrays.toString(joinPoint.getArgs()));
    }
}

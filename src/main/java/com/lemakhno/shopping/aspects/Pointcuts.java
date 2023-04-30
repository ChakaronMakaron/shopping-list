package com.lemakhno.shopping.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Pointcuts {
    
    @Pointcut("execution(* com.lemakhno.shopping.controllers.ProductController.*(..))")
    public void forProductControllerMethods() {};
}

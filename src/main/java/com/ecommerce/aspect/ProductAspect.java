package com.ecommerce.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductAspect {

    @Before("execution(* com.ecommerce.service.ProductServiceImpl.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        System.out.println("Before Method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(
            pointcut = "execution(* com.ecommerce.service.ProductServiceImpl.*(..))",
            returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        System.out.println("After Returning: " + joinPoint.getSignature().getName());
        System.out.println("Returned Value: " + result);
    }

    @AfterThrowing(
            pointcut = "execution(* com.ecommerce.service.ProductServiceImpl.*(..))",
            throwing = "exception")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception exception) {
        System.out.println("Exception in Method: " + joinPoint.getSignature().getName());
        System.out.println("Exception Message: " + exception.getMessage());
    }
}

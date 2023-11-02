package io.ylab.walletservice.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Class for creating logs
 */
@Aspect
@Component
public class LoggableAspect {

    /**
     * pointcut which say that methods or classes marked with annotation {@link io.ylab.walletservice.aop.annotations.Loggable}
     * have to be executed
     */
    @Pointcut("within(@io.ylab.walletservice.aop.annotations.Loggable *) && execution(* *(..))")
    public void annotatedByLoggable() {
    }

    /**
     * Aspect for creating logs. We can see when method called, executed and time of execution
     */
    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Calling method " + proceedingJoinPoint.getSignature().toShortString());
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution of method " + proceedingJoinPoint.getSignature().toShortString() +
                " finished. Execution time is " + (endTime - startTime) + " ms");
        return result;
    }

}

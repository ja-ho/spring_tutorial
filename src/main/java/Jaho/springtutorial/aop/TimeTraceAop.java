package Jaho.springtutorial.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
//@Component     for not register in springconfig
public class TimeTraceAop {

    @Around("execution(* Jaho.springtutorial..*(..)) && !target(Jaho.springtutorial.SpringConfig)")
    //@Around("execution(* Jaho.springtutorial..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMS = finish - start;
            System.out.println("END : " + joinPoint.toString() + " - " + timeMS +"ms");
        }
    }
}

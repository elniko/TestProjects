package pkg;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Nick on 01/04/2015.
 */
@Aspect
public class MyAspect {

    @Pointcut("execution(static * pkg.MyClass.staticMethod(..))")
    public void interceptStatic(){}

    @Around("interceptStatic()")
    public void interceptor1() {
        System.out.println("GOVNO intercepted");
    }

    @Before("call(* ubpartner.logmanagement.LogManagement.info(..))")
    public void interceptLog() {
        System.out.println("GOPA");
    }

    @Before("call(* org.apache.log4j.Category.log(..))")
    public void someInterceptor() {
        System.out.println("Intercepted log");
    }

}

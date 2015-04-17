package test;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by Nick on 02/04/2015.
 */
@Aspect
public class AspectTest {

    static Executor executor;

    public static Executor getExecutor() {
        return executor;
    }

    public static void setExecutor(Executor exec) {
        executor = exec;
    }

    @After(value = "execution(* pkg.ToTest.methode3(..)) ")
    public void interceptM3() {
        System.out.println("Intercepted");
       executor.execute();
    }



}

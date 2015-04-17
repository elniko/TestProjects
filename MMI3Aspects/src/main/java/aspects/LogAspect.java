package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by Nick on 03/04/2015.
 */
@Aspect
public class LogAspect {

    static LogInterceptorExecutor logInterceptorExecutor;

    public static void setLogInterceptorExecutor(LogInterceptorExecutor logInterceptorExecutor) {
        LogAspect.logInterceptorExecutor = logInterceptorExecutor;
    }

    @Around(value = "execution(* ubpartner.logmanagement.LogManagement.getReturnCode(..))")
    public int testInterceptor() {
        return 100;
    }

    @Around(value = "execution(* ubpartner.logmanagement.LogManagementI.lmOutputResetConfig(..))")
    public void stub() {}

    @Around(value = "execution(* ubpartner.logmanagement.LogManagement.add(ubpartner.logmanagement.CustomLevel, String, boolean)) && args(level, msg, flag)")
    public void interceptAdd(Object level, String msg, boolean flag) throws Throwable {
        logInterceptorExecutor.executeAdd(level, msg, flag);
    }

}

package exceptions;

/**
 * Created by Nick on 03/04/2015.
 */
public class LogAspectNotAppliedException extends Exception{
    public LogAspectNotAppliedException () {
        super("LogAspects not applied, verify your aspect and ajc compiler");
    }
}

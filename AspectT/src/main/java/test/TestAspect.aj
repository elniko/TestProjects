package test;

/**
 * Created by Nick on 03/04/2015.
 */
public aspect TestAspect {

    pointcut interceptM3() : execution(* pkg.ToTest.methode3(..));

    before(): interceptM3() {
        System.out.println("RealAspect");
    }

}

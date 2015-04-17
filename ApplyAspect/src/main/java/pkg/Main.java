package pkg;

import test.AspectTest;

/**
 * Created by Nick on 03/04/2015.
 */
public class Main {
    public static void main(String[] args) {

        AspectTest.setExecutor(new MyExec());
        ToTest tt = new ToTest();
        tt.methode1();
    }

}

package pkg;

import ubpartner.logmanagement.LMConfiguration;
import ubpartner.logmanagement.LogManagement;
import ubpartner.logmanagement.LogManagementI;

/**
 * Created by Nick on 01/04/2015.
 */
public class Main {
    public static void main(String[] args) {
        LogManagementI.lmInit("n","y","INFO","INFO","threshold","threshold","XCT","d:/res","mmi");

        LogManagement.info("govnoooooo");

    }
}

package tools.ubp;

//import aspects.LogAspect;

import entity.LogEntity;
import entity.ProcessEntity;
import exceptions.LogAspectNotAppliedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tools.annotations.LoggerConfig;
import tools.log4j2.SqlQueryAppender;
import tools.threading.AbstractRunningProcess;
import ubpartner.xct.XctSDK;
import ubpartner.xct.commun.ReturnModeXct;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Created by Nick on 30/03/2015.
 */
@Component
@Scope("prototype")
public class XctRunningProcess extends AbstractRunningProcess {

    @LoggerConfig
    public void setLogger(Logger lg) {
        logger = lg;
    }

    public static void main(String[] args) throws Exception {

        XctRunningProcess p = new XctRunningProcess();
        p.doWork();
    }




    @Override
    public void doWork() throws LogAspectNotAppliedException {
        long start = System.currentTimeMillis();
        System.out.println("Setting parameters for Xct.");
        XctSDK xct = new XctSDK(true);
        // parameter path to the parent of root of demo files directory
        String pathToDemo = "d:/mmi3";
        // path to the folder with delivered SDK jar.
        String pathToSDK = "d:/Demo/XT demo Package Version 2_7 API/SDK";
        // setting taxonomy configuration file, input file/folder, output file
        xct.setConfigFile(new File("Y:\\08-Recette\\TAXO_DEPOT\\S2-2015-Q1_V1.3_eiopa1.5.2b\\S2-2015-Q1_V1.3_2.7.xml"));
        xct.setExcelFile(new File("Y:\\08-Recette\\DEV_TESTS\\pour Olena\\UBP\\R2.8\\ars.xbrl_S.02.01.xlsx"));
        xct.setOutPutFile(new File(pathToDemo + "/RESULTS/XCT/report.xbrl"));

        // setting taxonomy, entity, period, units, table
        xct.setModeTaxoid(ReturnModeXct.ADRESSE);
        xct.setTaxoId("'INFO'!C5");
        xct.setModeEntity(ReturnModeXct.INPUT);
        xct.setEntity("X");
        xct.setEntityScheme("http:\\www.eba.europa.eu/fr/dummy");
        xct.setModeDate(ReturnModeXct.ADRESSE);
        xct.setStartdate("'INFO'!C7");
        xct.setEnddate("'INFO'!C8");
        xct.setModeUnity(ReturnModeXct.ADRESSE);
        xct.setUnity("'INFO'!C9");
        xct.setTableMode(ReturnModeXct.ADRESSE);
        xct.setTableId("'INFO'!C10");
        //xct.setXslPath(new File(""));
        // setting validation parameters
        xct.setValidate(true);
        xct.setValidationOutputFolder(new File(pathToDemo + "/RESULTS/XCT"));
        xct.setXvtXpeConfig("classpath:XvtXpeConfig.xml");
        xct.setCustomMessages("classpath:xmldata.xml");
        xct.setSuffix("xct");
        // setting subset conversion
        xct.setSubsetConversion("y");
        //setting optional XMT typed members limit
        //xct.setLimitation("0");
        // setting cache parameters
        xct.setUseCache(true);
        xct.setCachePath("C:/ProgramData/UBPartner/UBPartner XBRL Tools/cache");
        // running conversion
        System.out.println("Running Xct SDK...");
        xct.xctSDKrun();
        //returnCode = xct.getExecReturnCode();
        long end = System.currentTimeMillis();

        System.out.println(end-start);
    }
}

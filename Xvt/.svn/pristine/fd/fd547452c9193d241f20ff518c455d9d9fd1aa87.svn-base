package ubpartner.xvt;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import ubpartner.logmanagement.LogManagement;
import ubpartner.logmanagement.commun.ConstanteLevel;
import ubpartner.rmt.ToolsIntegration;
import ubpartner.taxonomymanagement.ConfiguratedTaxonomies;
import ubpartner.taxonomymanagement.ConfiguratedTaxonomy;
import ubpartner.taxonomymanagement.DeclaratifTable;
import ubpartner.taxonomymanagement.ParallelUnzip;
import ubpartner.taxonomymanagement.ZipManagement;
import ubpartner.threads.ThreadRunner;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.utils.commun.UnsatisfiedAssertion;
import ubpartner.utils.report.HtmlReport;
import ubpartner.utils.xbrl.XbrlFact;
import ubpartner.utils.xbrl.XbrlInstance;
import ubpartner.xvt.commun.Constante;
import ubpartner.xvt.validation.Validation;
import ubpartner.xvt.validation.commun.ValidationUtils;

/**
 * Class for instance validation.
 * 
 */
public class ExecXvt {
    /**
     * Input arguments for XVT.
     */
    private enum ARGS { i, t, c, o, s, cs, help, uc, chp, cm };
    /**
     * Instance file.
     */
    private File instanceFile;

    /**
     * Taxonomy configuration file.
     */
    private File configTaxonomieFile;

    /**
     * Taxonomy ID.
     */
    private String taxonomieId;

    /**
     * The last taxonomy ID.
     */
    private String lastTaxonomieId;
    
    /**
     * Taxonomy paths list.
     */
    private ArrayList<String> listPathTaxonomie = new ArrayList<String>();
    /**
     * Suffix to be added to the validation results files.
     */
    private String suffix;
    /**
     * Configured taxonomy collection.
     */
    private ConfiguratedTaxonomies taxos;
    /**
     * Output folder.
     */
    private File outputDirectory = null;
    /**
     *  Input folder.
     */
    private File inputFolder;
    /**
     * File for service XPE configuration.
     */
    private File serviceConfigXPE = null;
    /**
     * Proceeded files counter.
     */
    private int countFichierTraite = 0;
    /**
     * Use or not application cache.
     */
    private boolean usecache = false;
    /**
     * Custom messages file.
     */
    private File customMess = null;
    /**
     * Path to cache folder.
     */
    private String cachePath = "";
    /**
     * Declarative tables found in instance.
     */
    private HashMap<String, String> declTablesValidated = new HashMap<String, String>();
    /**
     * Declarative tables not found in instance.
     */
    private HashMap<String, String> declTablesNotExpected = new HashMap<String, String>();
    /**
     * Declarative tables not found in instance.
     */
    private HashMap<String, String> declTablesNotValidated = new HashMap<String, String>();
    /**
     * Main function for instance validation.
     * 
     * @param args
     *            argument : -i - XBRL instance file.
     *            argument : -x - XPE XSL file.
     *            argument : -s - suffix for results files.
     *            argument : -c - configuration file.
     *            argument : -t - taxonomy ID.
     *            argument : -o - output folder name. 
     *            argument : -? - Help.
     * 
     */
    public ExecXvt(final String[] args) {

		this.suffix = "";
		
		if (args.length == 0) {
		    System.out.println(Constante.MSGHELP);
		    return;
		}
		String[] argsVal = args.clone();
		for (int i = 0; i < argsVal.length; i++) {
		    String arg = argsVal[i];
	
		    if (arg.charAt(0) == '-') {
		    	String argenum = arg.substring(1);
				if (argenum.equals("?")) {
				    argenum = "help";
				}
				ARGS argument = null;
				try {
				    argument = ARGS.valueOf(argenum);
				    switch (argument) {
					case i:
					    i++;
					    this.instanceFile = new File(argsVal[i]);
					    if (this.instanceFile.exists()) {
							if (ConstanteUtils.getExtension(instanceFile).equals("")) {
							    this.inputFolder = instanceFile;
							} else {
							    this.inputFolder = new File(instanceFile.getParent());
							}
					    }
					    break;
					case t:
					    i++;
					    this.taxonomieId = argsVal[i];
					    break;
					case c:
					    i++;
					    this.configTaxonomieFile = new File(argsVal[i]);
					    break;
					case o:
					    i++;
					    this.outputDirectory = new File(argsVal[i]);
					    break;
					case s:
					    i++;
					    this.suffix = argsVal[i];
					    break;
					case cs:
					    i++;
					    this.serviceConfigXPE = new File(argsVal[i]);
					    break;
					case help:
					    System.out.println(Constante.MSGHELP);
					    break;
					case uc:
						i++;
			        	if (argsVal[i].equals("y")) {
			        	    this.usecache = true;
			        	} else {
			        		this.usecache = false;
			            }
			            break;
			        case chp:
			        	i++;
			        	this.cachePath = argsVal[i];
			        	break;
			        case cm:
			        	i++;
			        	this.customMess = new File(argsVal[i]);
			        	break;
					default:
					    System.out.println(Constante.MSGOPTIONINCONNU + " -" + arg.charAt(1));
					    break;
					}
				} catch (IllegalArgumentException e) {
				    System.out.println(Constante.MSGOPTIONINCONNU + " -" + argenum);
				} catch (Error e) {
				    System.out.println(Constante.MSGOPTIONINCONNU + " -" + argenum);
				}
		    }
		}
		preparation();
    }

    /**
     * Prepares files for treatment.
     */
    private void preparation() {
		Date start = new Date();
		LogManagement.info(Constante.NOMAPPLICATION + " " + Constante.VERSIONAPPLICATION);
		HtmlReport.clearSettings();
		HtmlReport.setNameappli(Constante.NOMAPPLICATION);
		if (!StringUtils.isEmpty(Constante.VERSIONAPPLICATION)) {
		    HtmlReport.setVersionappli(Constante.VERSIONAPPLICATION);
		} else {
		   	HtmlReport.setVersionappli("0.0.0");
		}
		if (usecache && StringUtils.isEmpty(cachePath)) {
	       LogManagement.error("Path to the user cache is not defined. Please, define it or set usecache to false");
	       return;
		}
		taxos = new ConfiguratedTaxonomies();
		taxos.initialise(this.configTaxonomieFile);
		if (this.outputDirectory == null) {
		    LogManagement.error("Output directory is not specified!");
		} else {
	        this.outputDirectory.mkdirs();
	        if (instanceFile.isDirectory()) {
	        	HtmlReport.setFoldInput(true);
	        	HtmlReport.setInputFolder(this.instanceFile.getPath().substring(this.instanceFile.getPath().lastIndexOf("\\") + 1));
	        	HtmlReport.setFileName(this.instanceFile.getPath().substring(this.instanceFile.getPath().lastIndexOf("\\") + 1));
	        	HtmlReport.setInputLink(this.instanceFile.getPath().substring(this.instanceFile.getPath().lastIndexOf("\\") + 1));
	        } else {
	        	HtmlReport.setFileName(instanceFile.getName());
	        	HtmlReport.setInputLink(instanceFile.getPath());
	        	HtmlReport.setInput(instanceFile.getName());
	        }
	        ArrayList<File> listFile = ConstanteUtils.findAllInstancesOrExcels(instanceFile, Constante.XBRL);
	        ArrayList<File> xmlFiles = ConstanteUtils.findAllInstancesOrExcels(instanceFile, Constante.XML);
	        ToolsIntegration.addInstanceWithoutHeader(listFile, xmlFiles);
	        	
	        Map<String, File> mapFile = new TreeMap<String, File>();
	        
	        for (File file : listFile) {
	        	mapFile.put(file.getName(), file); 
	        }
            
	        for (String filename : mapFile.keySet()) {
	        	 int res = traitement(mapFile.get(filename), suffix);
	        	 if (res > 0) {
	        	     countFichierTraite++;
	        	 } else if (res == -1) {
	        		 return;
	        	 }
	        }
	        
	        if (!usecache) {
		        if (!listPathTaxonomie.isEmpty()) {
		        	for (String pathTaxo : listPathTaxonomie) {
		        		ZipManagement.deleteDezip(pathTaxo);
		        	 }
		        }
	        }
	        ToolsIntegration.purgeTemp();
		}
		LogManagement.add(ConstanteLevel.INFO, countFichierTraite + " " + Constante.MSGNBFICHIERTRAITE, true);
		HtmlReport.setTblsNotRestituted(declTablesNotValidated);
	 	HtmlReport.setOutput(outputDirectory.getPath());
	    HtmlReport.createReport();
		Date stop = new Date();
		LogManagement.add(ConstanteLevel.INFO, "  Start : " + start, true);
		LogManagement.add(ConstanteLevel.INFO, "  Stop  : " + stop, true);
    }
 
    /**
     * Main treatment.
     * 
     * @param instance
     *            Instance file.
     * @param cSuffix
     *            Suffix to be added to results file.
     * @return 1 - traitement ok
     *         0 - traitement avec des erreurs, mais on peut continuer
     *         -1 - traitement avec des erreurs fatales, il faut arreter.
     */
    private int traitement(final File instance, final String cSuffix) {
		try {
		    Date start = new Date();
		    LogManagement.add(ConstanteLevel.INFO, Constante.MSGTRAITEMENT + " "  + instance.getName(), true);
		    
		    HashMap <Boolean, String> fileCodeTest;
		    fileCodeTest = ConstanteUtils.isUTF8consistent(instance);
		    if (!fileCodeTest.isEmpty()) {
				 LogManagement.error("File is not properly UTF-8 coded. Please verify! Error on line: " + "\n" + fileCodeTest.get(false));
				 return 0;
		    } else {
	        	 XbrlInstance instObj = new XbrlInstance(instance, false);
	        	 ConfiguratedTaxonomy taxo = taxos.getTaxonomyForInstance(instObj);
	        	 if (taxo != null) {
	        		 String month = "" + ConstanteUtils.getMonthFromStringDate(instObj.getEndDate());
					 ArrayList<String> skipedIds = new ArrayList<String>();
					 HashMap<String, ArrayList<String>> skiped = taxo.getDeclTblsByMonth();
		             if (!skiped.isEmpty()) {
		                 skipedIds = skiped.get(month);
		             }
		             if (skipedIds == null) {
		            	 skipedIds = new ArrayList<String>();
		             }
	                this.taxonomieId = taxo.getTaxoLibelle();
	                instObj.setTaxoId(taxonomieId);
	                HtmlReport.setTaxoname(taxonomieId);
	                if (taxo.isDeclaratif()) {
	                	HtmlReport.setDeclaratif(true);
	                	for (Entry<String, DeclaratifTable> currEnt : taxo.getDeclarTablesById().entrySet()) {
	                		DeclaratifTable currTbl = currEnt.getValue();
    	    				String currValue = currTbl.getTupleValue();
    						String currConcept = currTbl.getConceptNms() + "#" + currTbl.getConceptName();
    						XbrlFact currDeclF = instObj.findDeclaratifFact(currConcept, currValue, currTbl.isTuple());
    						if (currDeclF != null) {
    							declTablesValidated.put(currTbl.getTableId(), currTbl.getLibelle());
    							if (skipedIds.contains(currTbl.getTableId())) {
    								declTablesNotExpected.put(currTbl.getTableId(), currTbl.getLibelle());
    							}
    						} else if (!skipedIds.contains(currTbl.getTableId())) {
    							declTablesNotValidated.put(currTbl.getTableId(), currTbl.getLibelle());
    						} 
	                	}
	                }
	                File zipFile = new File(ConstanteUtils.getCanonicalPath(this.configTaxonomieFile.getPath(), taxo.getZipFile()));
	                String unzipPath = ConstanteUtils.getTaxonomyTemporaryDirPath(zipFile, "XVT", usecache, cachePath);
	                ArrayList<File> taxonomies = new ArrayList<File>();
	                for (String xsd : taxo.getXsdAsStringCollection()) {
	        			File fileXsd =  new File(unzipPath + "/" + xsd);
	        			taxonomies.add(fileXsd);
	        		}
	                File mainxsd = taxonomies.get(0);
	                
	                if (!this.taxonomieId.equals(lastTaxonomieId)) {
	                     if (!listPathTaxonomie.contains(unzipPath)) {
	                         listPathTaxonomie.add(unzipPath); 
	                     }
	                     if (!mainxsd.exists()) {
		                     LogManagement.info("Unzip settings files");
		                     ParallelUnzip tz = new ParallelUnzip(unzipPath, zipFile.getCanonicalPath());
		                     if (tz.isDiskSpaceEnough()) {
					     	     ThreadRunner tr = new ThreadRunner(tz.getBigList(), tz);
					     	     tr.setThreads(2);
					     	     tr.run();
		                     } else  {
			                    	LogManagement.error("Not enough disk space to unzip the settings archive");
			                    	LogManagement.error("You need at least " + tz.getOrigSize() + " Mb available on your system drive.");
			                    	if (taxo.isDeclaratif()) {
			                    	   return -1;
			                    	} else {
			                    		return 0;
			                    	}
			                  }
	                     }
	                 }
               
	                 Validation validation = new Validation(serviceConfigXPE, customMess);
	                 ValidationUtils.initialize(validation, taxonomieId);
	                 validation.validate(instance, instObj, taxonomies, true, true, cSuffix, this.outputDirectory.getPath(), inputFolder);
	                 ValidationUtils.shutdownDTS();
	                 LogManagement.info(Constante.MSGFINTRAITEMENT + " " + instance.getName());
	                 validation.transformToHtml();
	                 fillIndexMap(instObj, validation.getValidationErrors().isEmpty(), validation.getfResultOutput().getPath(), validation.isFormulaOK(), validation.getTablesKO());
	                 validation = null;
	                 //currVal.clearMaps();
	                 instObj = null;
	                 taxo = null;
	                 lastTaxonomieId = this.taxonomieId;
	        	 }
	    	    }
	    	    Date stop = new Date();
	    	    LogManagement.add(ConstanteLevel.INFO, "  Start : " + start, true);
	    	    LogManagement.add(ConstanteLevel.INFO, "  Stop  : " + stop, true);
	    	    return 1;
		} catch (Exception e) {
		    LogManagement.error("traitement => " + e.getMessage());
		    return 0;
		} catch (Error e) {
		    LogManagement.fatal("traitement => " + e.getMessage());
		    return -1;
		}
    }
    /**
     * Fills map for a report item creation.
     * @param instObj - instance object.
     * @param xbrlValid - Xbrl and Dimension KO flag
     * @param validOutput - validation output file path.
     * @param formulaKO - formula OK flag
     * @param tablesKO - formula KO list
     */
    private void fillIndexMap(final XbrlInstance instObj, final boolean xbrlValid, final String validOutput, final boolean formulaKO, final HashMap<String, ArrayList<UnsatisfiedAssertion>> tablesKO) {
 	   
	   ArrayList<String> dfTabsList = new ArrayList<String>();
	   ArrayList<UnsatisfiedAssertion> koTabsList = new ArrayList<UnsatisfiedAssertion>();
	   
	   if (!this.declTablesValidated.isEmpty()) {
		  for (Entry<String, String> currEnt : this.declTablesValidated.entrySet()) {
			   dfTabsList = new ArrayList<String>();
			   koTabsList = new ArrayList<UnsatisfiedAssertion>();
			   HashMap<String, Object> indexMap = new HashMap<String, Object>();
			   HashMap<String, String> summMap = new HashMap<String, String>();
			   
			   summMap.put("taxonomy", HtmlReport.getTaxoname());
		 	   summMap.put("entity", instObj.getEntity());
		 	   summMap.put("endDate", instObj.getEndDate());
		 	   summMap.put("currency", instObj.getMonetaryUnitValue());
		 	   summMap.put("instance", HtmlReport.getInputLink());
		 	   summMap.put("referenceFile", HtmlReport.getInputLink());
		 	   
		 	   indexMap.put("validationFileName", validOutput);
		 	   indexMap.put("fileName", currEnt.getKey() + "#" + currEnt.getValue());
		 	   indexMap.put("summary", summMap);
		 	   indexMap.put("xbrlKo", xbrlValid);
		 	   indexMap.put("formulaKO", formulaKO);
		 	   if (!tablesKO.isEmpty()) {
		 		   if (tablesKO.containsKey(currEnt.getKey())) {
		 			  koTabsList = tablesKO.get(currEnt.getKey());
		 		   }
		 	   }
		 	   indexMap.put("koTabsList", koTabsList);
		 	   indexMap.put("dfTabsList", dfTabsList);
		 	   if (declTablesNotExpected.containsKey(currEnt.getKey())) {
		 		   indexMap.put("expected", "n");
		 	   }
               HtmlReport.addItem(indexMap);
		   } 
	   } else {
		   HashMap<String, Object> indexMap = new HashMap<String, Object>();
		   HashMap<String, String> summMap = new HashMap<String, String>();
		   
		   summMap.put("taxonomy", HtmlReport.getTaxoname());
	 	   summMap.put("entity", instObj.getEntity());
	 	   summMap.put("endDate", instObj.getEndDate());
	 	   summMap.put("currency", instObj.getMonetaryUnit());
	 	   summMap.put("instance", instObj.getInstFile().toString());
	 	   summMap.put("referenceFile", instObj.getInstFile().toString());
	 	   
	 	   indexMap.put("validationFileName", validOutput);
	 	   indexMap.put("fileName", HtmlReport.getFileName());
	 	   indexMap.put("summary", summMap);
	 	   indexMap.put("xbrlKo", xbrlValid);
	 	   indexMap.put("formulaKO", formulaKO);
	 	   indexMap.put("koTabsList", koTabsList);
	 	   indexMap.put("dfTabsList", dfTabsList);
	 	   
	 	  HtmlReport.addItem(indexMap);
	   }
    }
}

package ubpartner.xct;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ubpartner.logmanagement.LogManagement;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.xct.commun.Constante;
import ubpartner.xct.commun.ConstanteMessages;
import ubpartner.xct.commun.ConstanteXmlMapping;

/**
 * Class for the initial treatment of the arguments transferred from batch.
 * @author UBPartner
 */
public final class ConfigurationParametres {
    /**
     * Empty constructor for the final class.
     */
    private ConfigurationParametres() {
        //Class cannot be instantiated.
    }
    /**
     * Array with input modes.
     */
    private static List<String> alimModeArray =
            Arrays.asList(Constante.INPUTMODE,
            Constante.ADDRESSMODE, Constante.NAMEMODE);
    /**
     * Returns the parameter value according to the input mode.
     * @param alimMode - input mode
     *        -input - manual input
     *        -adresse - value from the cell address
     *        -nom - value from the named cell
     * @param excelWbk - workbook Excel with values
     * @param paramInput - inputed argument value
     * @param paramType - type of the inputed value
     * (string - for entity, date - date for end date and start date)
     * @param param - parameter name
     * @return paramValue - parameter value
     */
    public static String getConfigurationParametre(final String alimMode,
                  final XSSFWorkbook excelWbk, final String paramInput,
                  final String paramType, final String param) {
    	LogManagement.debug("Return the parameter value according to the rendering mode.");
        String paramValue = null;
        Date paramDate;
        int aliModeInt = alimModeArray.indexOf(alimMode);
        switch (aliModeInt) {
        case 0:
            if (paramType.equals(ConstanteXmlMapping.ENTITYTYPE)) {
                paramValue = paramInput;
            } else if (paramType.equals(Constante.DATETYPE)) {
                paramDate =  parseUnknownDate(paramInput);
                if (paramDate != null) {
                    Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                    paramValue = formatter.format(paramDate);
                } else {
                    paramValue = "";
                }
            }
            break;
        case 1:
            try {
         	    XSSFCell refCell = getCellByReference(paramInput, excelWbk, true);
                if (paramType.equals(ConstanteXmlMapping.ENTITYTYPE)) {
                    paramValue = ConstanteUtils.getFormattedCellValue(refCell);
                    paramValue = paramValue.trim();
                } else if (paramType.equals(Constante.DATETYPE)) {
                    if (refCell.getCellType() == (Cell.CELL_TYPE_NUMERIC)) {
                        if (DateUtil.isCellDateFormatted(refCell)) {
                            paramDate = refCell.getDateCellValue();
                        } else {
                        	paramDate = parseUnknownDate(ConstanteUtils.getFormattedCellValue(refCell));
                        }
                    } else {
                        paramDate = parseUnknownDate(ConstanteUtils.getFormattedCellValue(refCell));
                    }
                    if (paramDate != null) {
                        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                        paramValue = formatter.format(paramDate);
                    } else {
                        paramValue = "";
                    }
                }
            } catch (Exception e) {
                LogManagement.error("getConfigurationParametre => Failed to get reference from cell " + paramInput + " => " + e.getMessage());
                paramValue = "";
            } catch (Error e) {
            	LogManagement.fatal("getConfigurationParametre => Failed to get reference from cell " + paramInput + " => " + e.getMessage());
                paramValue = "";
            }
            break;
        case 2:
            try {
                XSSFName currName = excelWbk.getName(paramInput);
                String address = currName.getRefersToFormula();
                XSSFCell refCell = getCellByReference(address, excelWbk, true);
                if (paramType.equals(ConstanteXmlMapping.ENTITYTYPE)) {
                    paramValue = ConstanteUtils.getFormattedCellValue(refCell);
                } else if (paramType.equals(Constante.DATETYPE)) {
                    if (refCell.getCellType() == (Cell.CELL_TYPE_NUMERIC)) {
                        if (DateUtil.isCellDateFormatted(refCell)) {
                            paramDate = refCell.getDateCellValue();
                        } else {
                            paramDate = parseUnknownDate(
                                    refCell.getStringCellValue());
                        }
                    } else {
                        paramDate = parseUnknownDate(
                                refCell.getStringCellValue());
                    }
                    if (paramDate != null) {
                        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                        paramValue = formatter.format(paramDate);
                    } else {
                        paramValue = "";
                    }
                }
            } catch (Exception e) {
                LogManagement.error("getConfigurationParametre => Failed to find parameter: " + param);
                paramValue = "";
            } catch (Error e) {
            	LogManagement.fatal("getConfigurationParametre => Failed to find parameter: " + param);
                paramValue = "";
            }
            break;
        default:
            LogManagement.warn("Undefined rendering mode: " + alimMode);
            paramValue = "";
            break;
        }
        return paramValue;
    }

    /**
     * Returns the cell object which correspond to reference "Sheet!Range".
     * @param rangeRef - reference "Sheet!Range".
     * @param excelWbk - excel workbook to search cell in.
     * @param errreturn - true: return null when the cell doesn't exist
     *                    false: - create new cell
     * @return cell as XSSFCell object.
     */
    public static XSSFCell getCellByReference(final String rangeRef,
                 final XSSFWorkbook excelWbk, final boolean errreturn) {
    	LogManagement.debug("Return the cell object address in form \'Sheet!Range\'.");
        XSSFCell currCell;
        XSSFRow currRow;
        XSSFSheet currSheet;
        String sheetName = rangeRef.substring(0,
                rangeRef.lastIndexOf('!')).replace("'", "");
        String address = rangeRef.substring(rangeRef.lastIndexOf('!') + 1);
        try {
            currSheet = excelWbk.getSheet(sheetName);
            if (currSheet != null) {
                CellReference cellRef = new CellReference(address);
                if (currSheet.getRow(cellRef.getRow()) != null) {
                    currRow = currSheet.getRow(cellRef.getRow());
                    if (currRow.getCell(cellRef.getCol()) != null) {
                        currCell = currRow.getCell(cellRef.getCol());
                    } else {
                        if (errreturn) {
                            LogManagement.warn(ConstanteMessages.MSGCELLUNREACHABLE
                                           + rangeRef);
                            currCell = null;
                        } else {
                            currCell = currRow.createCell(cellRef.getCol());
                        }
                   }
                } else {
                   if (errreturn) {
                       LogManagement.warn(ConstanteMessages.MSGCELLUNREACHABLE
                                       + rangeRef);
                       currCell = null;
                   } else {
                       currRow = currSheet.createRow(cellRef.getRow());
                       currCell = currRow.createCell(cellRef.getCol());
                   }
                }
            } else {
                if (errreturn) {
                     LogManagement.warn(ConstanteMessages.ERRSHEETNAME
                             + sheetName);
                }
                currCell = null; 
            }            
        } catch (Exception e) {
            if (errreturn) {
                LogManagement.error("getCellByReference => " + ConstanteMessages.ERRSHEETNAME + sheetName);
            }
            currCell = null;
        } catch (Error e) {
        	LogManagement.fatal("getCellByReference => " + ConstanteMessages.ERRSHEETNAME + sheetName);
            currCell = null;
        }
        return currCell;
    }
    /**
     * Converts string to the date, according to list of possible formats.
     * @param dateToParse - string from which date would be created
     * @return date object
     */
    public static Date parseUnknownDate(final String dateToParse) {
    	LogManagement.debug("Converts string to the date, according to list of possible formats");
        
        Date returnDate = null;
        String [] formats;
        String currLocale = System.getProperty("user.language");
        if (currLocale.equals(Locale.US.getLanguage())) {
            formats = Constante.DATEFORMATSENG;
        } else {
            formats = Constante.DATEFORMATSEUR;
        }
        DateFormat currFormat;
        for (String format: formats) {
            currFormat = new SimpleDateFormat(format);
            try {
                returnDate = currFormat.parse(dateToParse);
                if (currFormat.format(returnDate).equals(dateToParse)) {
                    return returnDate;
                }
            } catch (Exception e) {
            	LogManagement.debug("parseUnknownDate => next format");
            } catch (Error e) {
            	LogManagement.debug("parseUnknownDate => next format");
             }
        }
        return null;
    }
    /**
     * Verifies whether all the parameters are entered.
     * If yes, then verifies that end date is after start date.
     * @param entity - entity value
     * @param endDate - end date
     * @param startDate - start date
     * @return true - if all verifications passed
     */
    public static boolean verifyConfigurationParameters(final String entity,
                           final String endDate, final String startDate) {
    	LogManagement.info("Verifies wether all the parameters are entered.");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean vrf = true;
        if ((entity.equals(""))
             || (endDate.equals(""))
             || (startDate.equals(""))) {
            vrf = false;
            LogManagement.warn("Not all configuration parameters entered. "
            		+ "Entity: " + entity + "\n"
            		+ "End date: " + endDate + "\n"
            		+ "Start date: " + startDate + "\n");
        } else {
            Date eDate;
            Date sDate;
            try {
                eDate = sdf.parse(endDate);
                sDate = sdf.parse(startDate);
                if ((eDate.before(sDate)) || (eDate.equals(sDate))) {
                    vrf = false;
                    LogManagement.warn("End date should be after start date");
                }
            } catch (ParseException e) {
               LogManagement.error("verifyConfigurationParameters => Dates invalid: " + endDate + " " + startDate);
               vrf = false;
            } catch (Error e) {
            	LogManagement.fatal("verifyConfigurationParameters => Technical error");
                vrf = false;
             }
        }        
        return vrf;
    }
    
}


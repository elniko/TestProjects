rem ----------------------------------------------------------------------
rem USERPROFILE setting
rem ----------------------------------------------------------------------
SET UBP_HOME=%USERPROFILE%
SET UBP_TEMP=%TEMP%

rem ----------------------------------------------------------------------
rem Path to XCT
rem ----------------------------------------------------------------------
set ROOT=%CD%
cd /D "%ROOT%"
set LIB=%ROOT%\lib/*
set JAR=%ROOT%\xct.jar

rem ----------------------------------------------------------------------
rem Path to UBPARTNER license directory. Is system dependend. 
rem Points to UBPARTNER folder in the windows common application data
rem or to the XCT root folder.
rem ----------------------------------------------------------------------
set UBP_LIC=%CD%

rem ----------------------------------------------------------------------
rem CACHE SETTINGS
rem ----------------------------------------------------------------------
set USE_CACHE=[y/n]
set CACHE_PATH=[if USE_CACHE=y, should be filled with path to cache]

rem ----------------------------------------------------------------------
rem Taxonomy configuration file
rem ----------------------------------------------------------------------
set TAXO_CONFIG=[Path to TAXO_config.xml]

rem ----------------------------------------------------------------------
rem Input and output directory
rem ----------------------------------------------------------------------
set INPUT=[folder_or_file_path to the input folder with one/several Excel file(s) to convert]
set OUTPUT=[folder_or_file_path to save the resulting instance(s)]
rem ----------------------------------------------------------------------------------------

rem -----------------------------------------------------------------------
rem VALIDATION settings
rem -----------------------------------------------------------------------
set XPE_ROOT=[XPE_folder_path]
set LIB_XPE=%XPE_ROOT%\lib/*
set PROP_XPE=%XPE_ROOT%\properties
set XPE_CONFIG=%ROOT%\XvtXpeConfig.xml
set CUSTOM_MESSAGES=%ROOT%\xmldata.xml

set SUFFIX=[some string]
set XVT_OUTPUT=[folder_path]
set VALIDATION_ACTIVATION=y

rem ------------------------------------------------------------------------
rem COMMONS SETTINGS XCT/XRT
rem ------------------------------------------------------------------------
set TAXO_MODE=[address\input\name]
set TAXO=[taxonomy value/cell address/cell name]
set ENTITY_MODE=[address\input\name]
set ENTITY=[entity value/cell address/cell name]
set DATES_MODE=[address\input\name]
set START_DATE=[start date value/cell address/cell name]
set END_DATE=[end date value/cell address/cell name]
set UNITS_MODE=[address\input\name]
set UNITS=[units value/cell address/cell name]

rem --------------------------------------------------------------------------
rem XCT using only
rem --------------------------------------------------------------------------
set ENTITY_PATH=[some URL]
set SUBSET_ACTIVATION=y

rem --------------------------------------------------------------------------
rem Header configuration
rem --------------------------------------------------------------------------
SET USE_HEADER=n
SET COUNTRY=FR
set HEADER_CONFIG=%ROOT%\envcfg.xml
set REPORTING_PROPERTIES=%ROOT%\reportingconfig.properties

rem --------------------------------------------------------------------------
rem DECLARATIVE TABLES SETTINGS
rem --------------------------------------------------------------------------
set TABLE_MODE=[address\input\name]
set TABLE_ID=[table id value/cell address/cell name]

rem ------------------------------------------------------------------------
rem Common log settings
rem ------------------------------------------------------------------------
set LOG_FOLDER=[some path]
set MYVAR=%DATE:/=_%_%TIME:~0,2%_%TIME:~3,2%_%TIME:~6,2%
set LOGFILE_ACTIVATION=y
set LOGPRINT_ACTIVATION=y
set POPUP=n
set FILE_LEVEL=INFO
set FILE_MODE=threshold
set PRINT_LEVEL=INFO
set PRINT_MODE=threshold

md "%LOG_FOLDER%"
set LOGFILE_PATH=%LOG_FOLDER%\XCT_%MYVAR%_logs.log

rem -------------------------------------------------------------------------
rem JAVA VM setting
rem -------------------------------------------------------------------------
set XMX=6000
set XMS=6000
set PSIZE=256
set MPERMSIZE=512
set JAVA_PATH=

"%JAVA_PATH%java" -Xms%XMS%m -Xmx%XMX%m -XX:PermSize=%PSIZE%m -XX:MaxPermSize=%MPERMSIZE%m -classpath "%LIB%";"%LIB_XPE%";"%PROP_XPE%";"%JAR%" ubpartner.Xct -c "%TAXO_CONFIG%" -i "%INPUT%" -tm "%TAXO_MODE%" -t "%TAXO%" -em "%ENTITY_MODE%" -e "%ENTITY%" -dm "%DATES_MODE%" -sd "%START_DATE%" -ed "%END_DATE%" -E "%ENTITY_PATH%" -o "%OUTPUT%" -v "%VALIDATION_ACTIVATION%" -s "%SUFFIX%" -ov "%XVT_OUTPUT%" -sc "%SUBSET_ACTIVATION%" -um "%UNITS_MODE%" -u "%UNITS%" -cs "%XPE_CONFIG%" -ti "%TABLE_ID%" -tim "%TABLE_MODE%" -ch "%HEADER_CONFIG%" -h "%USE_HEADER%" -uc "%USE_CACHE%" -chp "%CACHE_PATH%" -cm "%CUSTOM_MESSAGES%" -rt "%REPORTING_PROPERTIES%" -p "%COUNTRY%"
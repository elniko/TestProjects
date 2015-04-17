rem ----------------------------------------------------------------------
rem USERPROFILE setting
rem ----------------------------------------------------------------------
SET UBP_HOME=%USERPROFILE%
SET UBP_TEMP=%TEMP%

rem ----------------------------------------------------------------------
rem WORK_DIR points to the root folder of the demo package.
rem ----------------------------------------------------------------------
SET WORK_DIR=%CD%\..\..

rem ----------------------------------------------------------------------
rem Path to the installed XCT.
rem ----------------------------------------------------------------------
set ROOT=%CD%
cd /D "%ROOT%"
set LIB=%ROOT%\libs/*
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
set USE_CACHE=y
set CACHE_PATH=C:\ProgramData\UBPartner\UBPartner XBRL Tools

rem ----------------------------------------------------------------------
rem Taxonomy configuration file
rem ----------------------------------------------------------------------
set TAXO_CONFIG=%WORK_DIR%\TAXO\TAXO_config.xml

rem ----------------------------------------------------------------------
rem Input and output directory
rem ----------------------------------------------------------------------
set INPUT=%WORK_DIR%\UC\XCT\CONVERT
set OUTPUT=%WORK_DIR%\RESULTS\XCT\report.xbrl

rem -----------------------------------------------------------------------
rem VALIDATION settings
rem -----------------------------------------------------------------------
set XPE_ROOT=%WORK_DIR%\XPE
set LIB_XPE=%XPE_ROOT%\lib/*
set PROP_XPE=%XPE_ROOT%\properties
set XPE_CONFIG=%ROOT%\XvtXpeConfig.xml
set CUSTOM_MESSAGES=%ROOT%\xmldata.xml

set SUFFIX=xct
set XVT_OUTPUT=%WORK_DIR%\RESULTS\XCT
set VALIDATION_ACTIVATION=y

rem ------------------------------------------------------------------------
rem COMMONS SETTINGS XCT/XRT
rem ------------------------------------------------------------------------
set TAXO_MODE=address
set TAXO=INFO!C5
set ENTITY_MODE=address
set ENTITY=INFO!C6
set DATES_MODE=address
set START_DATE=INFO!C7
set END_DATE=INFO!C8
set UNITS_MODE=address
set UNITS=INFO!C9

rem --------------------------------------------------------------------------
rem XCT using only
rem --------------------------------------------------------------------------
set ENTITY_PATH=http://www.eba.europa.eu/fr/dummy
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
set TABLE_MODE=address
set TABLE_ID=INFO!C10

rem ------------------------------------------------------------------------
rem Common log settings
rem ------------------------------------------------------------------------
SET MYVAR=%DATE:/=_%_%TIME:~0,2%_%TIME:~3,2%_%TIME:~6,2%
set LOGFILE_ACTIVATION=y
set LOGPRINT_ACTIVATION=y
set POPUP=y
set FILE_LEVEL=INFO
set FILE_MODE=threshold
set PRINT_LEVEL=INFO
set PRINT_MODE=threshold
set LOG_FOLDER=%WORK_DIR%\RESULTS\LOG
md "%LOG_FOLDER%"
set LOGFILE_PATH=%LOG_FOLDER%\XCT_%MYVAR%_logs.log

rem -------------------------------------------------------------------------
rem JAVA VM setting
rem -------------------------------------------------------------------------
set XMX=6000
set XMS=1024
set PSIZE=256
set MPERMSIZE=512
set JAVA_PATH=

"%JAVA_PATH%java" -Xms%XMS%m -Xmx%XMX%m -XX:PermSize=%PSIZE%m -XX:MaxPermSize=%MPERMSIZE%m -classpath "%LIB%";"%LIB_XPE%";"%PROP_XPE%";"%JAR%" ubpartner.Xct -c "%TAXO_CONFIG%" -i "%INPUT%" -tm "%TAXO_MODE%" -t "%TAXO%" -em "%ENTITY_MODE%" -e "%ENTITY%" -dm "%DATES_MODE%" -sd "%START_DATE%" -ed "%END_DATE%" -E "%ENTITY_PATH%" -o "%OUTPUT%" -v "%VALIDATION_ACTIVATION%" -s "%SUFFIX%" -ov "%XVT_OUTPUT%" -sc "%SUBSET_ACTIVATION%" -um "%UNITS_MODE%" -u "%UNITS%" -cs "%XPE_CONFIG%" -ti "%TABLE_ID%" -tim "%TABLE_MODE%" -ch "%HEADER_CONFIG%" -h "%USE_HEADER%" -uc "%USE_CACHE%" -chp "%CACHE_PATH%" -cm "%CUSTOM_MESSAGES%" -rt "%REPORTING_PROPERTIES%" -p "%COUNTRY%"
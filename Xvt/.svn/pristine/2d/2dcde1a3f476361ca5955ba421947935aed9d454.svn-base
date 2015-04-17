rem ----------------------------------------------------------------------
rem USERPROFILE setting
rem ----------------------------------------------------------------------
SET UBP_HOME=%USERPROFILE%
SET UBP_TEMP=%TEMP%

rem ----------------------------------------------------------------------
rem Path to XVT
rem ----------------------------------------------------------------------
set ROOT=%CD%
cd /D "%ROOT%"
set LIB=%ROOT%\lib/*
set JAR=%ROOT%\xvt.jar

rem ----------------------------------------------------------------------
rem Path to UBPARTNER license directory. Is system dependend. 
rem Points to UBPARTNER folder in the windows common application data
rem or to the XVT root folder.
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
set INPUT=[folder_or_file_path to the instance(s) to validate]
set OUTPUT=[folder_or_file_path to stock ]

rem -----------------------------------------------------------------------
rem VALIDATION settings
rem -----------------------------------------------------------------------
set XPE_ROOT=[XPE_folder_path]
set LIB_XPE=%XPE_ROOT%\lib/*
set PROP_XPE=%XPE_ROOT%\properties
set XPE_CONFIG=%ROOT%\XvtXpeConfig.xml
set CUSTOM_MESSAGES=%ROOT%\xmldata.xml

set SUFFIX=[some string]

rem ------------------------------------------------------------------------
rem Common log settings
rem ------------------------------------------------------------------------
set LOG_FOLDER=[folder path]
set MYVAR=%DATE:/=_%_%TIME:~0,2%_%TIME:~3,2%_%TIME:~6,2%
set LOGFILE_ACTIVATION=y
set LOGPRINT_ACTIVATION=y
set POPUP=n
set FILE_LEVEL=INFO
set FILE_MODE=threshold
set PRINT_LEVEL=INFO
set PRINT_MODE=threshold

md "%LOG_FOLDER%"
set LOGFILE_PATH=%LOG_FOLDER%\XVT_%MYVAR%_logs.log

rem -------------------------------------------------------------------------
rem JAVA VM setting
rem -------------------------------------------------------------------------
set XMX=6000
set XMS=6000
set PSIZE=256
set MPERMSIZE=512
set JAVA_PATH=

"%JAVA_PATH%java" -Xms%XMS%m -Xmx%XMX%m -XX:PermSize=%PSIZE%m -XX:MaxPermSize=%MPERMSIZE%m -classpath "%LIB%";"%LIB_XPE%";"%PROP_XPE%";"%JAR%" ubpartner.Xvt -c "%TAXO_CONFIG%" -i "%INPUT%" -o "%OUTPUT%" -s "%SUFFIX%" -cs "%XPE_CONFIG%" -uc "%USE_CACHE%" -chp "%CACHE_PATH%" -cm "%CUSTOM_MESSAGES%"

pause
@echo off
call setJAVA_HOME.bat

set folder=..
set CLASSPATH=.
FOR %%i IN ("%folder%/lib/*.jar") DO CALL "setClassPath.bat" %folder%/lib/%%i   

%JAVA_HOME%\bin\java %JAVA_OPTS% cn.beecp.test.SingleThreadBorrow



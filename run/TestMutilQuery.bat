@echo off
call setJAVA_HOME.bat

set folder=..
set CLASSPATH=.
FOR %%i IN ("%folder%/lib/*.jar") DO CALL "setClassPath.bat" %folder%/lib/%%i   

set JAVA_OPTS= -Xms2048m -Xmx2048m
%JAVA_HOME%\bin\java org.jmin.bee.test.MutilThreadQuery


 



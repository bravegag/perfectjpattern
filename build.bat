@echo off

REM /***************************************************************/
REM /* Created By  : Giovanni Azua Garcia                          */
REM /* Created On  : 06 Oct 2007                                   */
REM /* Description : A simple batch file to run PerfectJPattern's  */
REM /*               maven build script.                           */
REM /* Requires    : Maven 2.x installed http://maven.apache.org/  */
REM /*             : %MAVEN_HOME% env variable properly set        */
REM /*             : JDK or JRE at least 1.5 recommended 1.6.0_02  */
REM /***************************************************************/
REM /* See         : http://perfectjpattern.sourceforge.net/       */
REM /***************************************************************/

@REM ==== START VALIDATION ====
if not "%MAVEN_HOME%" == "" goto OkMavenHome

echo.
echo ERROR: MAVEN_HOME not found in your environment.
echo Please set the MAVEN_HOME variable in your environment to match the
echo location of your Maven 2.x installation.
echo.
goto error

:OkMavenHome

REM show maven version
echo "*****************************************************************************"
call %MAVEN_HOME%\bin\mvn.bat -version
echo "*****************************************************************************"

REM install jta-1_1 dependency
call %MAVEN_HOME%\bin\mvn.bat install:install-file -Dfile=extlib\jta-1_0_1B-classes.jar -DgroupId=javax.transaction -DartifactId=jta -Dversion=1.0.1B -Dpackaging=jar -DgeneratePom=true

REM start build process
call %MAVEN_HOME%\bin\mvn.bat -Pall clean install site -e -Dgenerate.ydoc=true %* 
if ERRORLEVEL 1 goto error

REM workaround for Maven 2.0.7 Xref misfunctioning
xcopy /s /y perfectjpattern-api\target\site\* target\site\perfectjpattern-api\ 
xcopy /s /y perfectjpattern-core\target\site\* target\site\perfectjpattern-core\
xcopy /s /y perfectjpattern-hibernate\target\site\* target\site\perfectjpattern-hibernate\ 
xcopy /s /y perfectjpattern-spring\target\site\* target\site\perfectjpattern-spring\
xcopy /s /y perfectjpattern-examples\target\site\* target\site\perfectjpattern-examples\

REM aggregate reports with dashboard
REM call %MAVEN_HOME%\bin\mvn.bat org.codehaus.mojo:dashboard-maven-plugin:1.0-SNAPSHOT:dashboard -e

REM create assembly
call %MAVEN_HOME%\bin\mvn.bat assembly:assembly -e
if ERRORLEVEL 1 goto error

goto success

:error
set ERROR_CODE=1

exit /B %ERROR_CODE%

:success

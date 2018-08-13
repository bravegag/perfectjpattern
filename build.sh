#!/bin/sh

# /***************************************************************/
# /* Created By  : Giovanni Azua Garcia                          */
# /* Created On  : 06 Oct 2007                                   */
# /* Description : A simple batch file to run PerfectJPattern's  */
# /*               maven build script.                           */
# /* Requires    : Maven 2.x installed http://maven.apache.org/  */
# /*             : $M2_HOME env variable properly set         */
# /*             : JDK or JRE at least 1.5 recommended 1.6.0_02  */
# /***************************************************************/
# /* See         : http://perfectjpattern.sourceforge.net/       */
# /***************************************************************/

# check for M2_HOME
if [ ! -x "$M2_HOME" ] ; then
  echo "ERROR: M2_HOME not found in your environment."
  echo "Please set the M2_HOME variable in your environment to match the"
  echo "location of your Maven 3.x installation."
  exit 1
fi

rm -rf ./build.log
touch ./build.log

# show maven version
echo "******************************************************************************************" >> build.log
$M2_HOME/bin/mvn -version                                                                         >> build.log
echo "******************************************************************************************" >> build.log

# first clean all projects
$M2_HOME/bin/mvn -Pall clean -e >> build.log 2> build.err
if [ "$?" -ne 0 ]; then echo "maven clean command failed"; exit 1; fi

# build and run coverage
$M2_HOME/bin/mvn -DargLine="-Xmx512m -Xms256m -XX:+PrintCompilation -verbose:gc -Xbatch -server -XX:CICompilerCount=1" clover:setup test clover:aggregate clover:clover -e >> build.log 2>> build.err
if [ "$?" -ne 0 ]; then echo "maven build command failed"; exit 1; fi

# build the site aggregating many of the reports
$M2_HOME/bin/mvn site -DargLine="-Xmx512m -Xms256m -XX:+PrintCompilation -verbose:gc -Xbatch -server -XX:CICompilerCount=1" -Dgenerate.ydoc=true -Dgenerate.statsvn=false -e $* >> build.log 2>> build.err
if [ "$?" -ne 0 ]; then echo "maven site command failed"; exit 1; fi

# fix screwed css class that adds extra blank spaces to anchors
find ./target/site/css/ -name "*.css" -exec sed -i 's/padding-right: 18px;//g' {} \;

# fix links from FindBug report files to aggregated xref
find ./target/site/ -name "*find*" -exec sed -i 's/\.\/xref/\.\.\/xref/g' {} \;

# fix the statscm misfunctioning that copies all output to the parent folder
#find ../target -name "statscm" | awk -F/ '{ print "mv " $1"/"$2"/"$3"/"$4"/"$5"/*" " ./target/"$3"/"$4"/"$5"/" }' | sh
#rm -rf ../target

# sign the jars
#$M2_HOME/bin/mvn package -Dmaven.test.skip=true -Dsign.jar=true -Dkeystore=/home/bravegag/keystore/perfectjpatternkeystore -e $* >> build.log  2>> build.err

# make the signed jars the ones for releasing
#find . -path "*signed*" -name "*.jar" | awk -F/ '{ print "mv -f " $1"/"$2"/"$3"/"$4"/"$5" "$1"/"$2"/"$3"/"}' | sh

# create assembly for all modules
$M2_HOME/bin/mvn assembly:assembly -Dmaven.test.skip=true -e >> build.log 2>> build.err
if [ "$?" -ne 0 ]; then echo "maven assembly command failed"; exit 1; fi

firefox ./target/site/index.html &
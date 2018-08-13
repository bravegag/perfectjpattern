//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// TestPerformance.java Copyright (c) 2012 Giovanni Azua Garcia
// bravegag@hotmail.com
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//    http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
//----------------------------------------------------------------------
package org.perfectjpattern.core.behavioral.visitor;

import java.text.*;

import junit.framework.*;

import org.slf4j.*;

/**
 * Performance test suite for the {@link AbstractVisitor} implementation
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: May 1, 2009 1:49:54 PM $
 */
public
class TestPerformance
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
	public void
    testPerformanceWithoutCache()
    {
        //====================================================================
        // Tested manually with VM parameters:
        //====================================================================
        // "-server -Xbatch -XX:+PrintCompilation -verbose:gc
        // -XX:CICompilerCount=1 -Xms256m -Xmx512m"
        //====================================================================

        // no cache (uses AbstractVisitor.reusableVisit)
        IHybridVisitor myVisitor = new DoVisitor();
        StyledBody myBody = new StyledBody();
        double myTolerance = 10.0;
        runPerformanceTest(myBody, myVisitor, myTolerance);
    }

    //------------------------------------------------------------------------
    public void
    testPerformanceWithCache()
    {
        //====================================================================
        // Tested manually with VM parameters:
        //====================================================================
        // "-server -Xbatch -XX:+PrintCompilation -verbose:gc
        // -XX:CICompilerCount=1 -Xms256m -Xmx512m"
        //====================================================================

        // cache (extends AbstractVisitor)
        IHybridVisitor myVisitor = new PrintVisitor();
        StyledBody myBody = new StyledBody();
        double myTolerance = 10.0;
        runPerformanceTest(myBody, myVisitor, myTolerance);
    }
	
    //------------------------------------------------------------------------
    private void
    runPerformanceTest(ICarPart aCarPart, IHybridVisitor aVisitor,
        double aTolerance)
    {
        final int myWarmUpTimes = REPETITIONS;
        final int myBenchmarkTimes = REPETITIONS;

        // warm up classical
        theLogger.debug("------ Warming up Classic Visitor");
        runClassic(myWarmUpTimes, aCarPart, aVisitor);

        // benchmark classic
        theLogger.debug("------ Benchmarking Classic Visitor");
        System.gc();
        double myClassicElapsed = System.nanoTime();
        runClassic(myBenchmarkTimes, aCarPart, aVisitor);
        myClassicElapsed = System.nanoTime() - myClassicElapsed;

        // warm up perfectjpattern's
        theLogger.debug("------ Warming up PerfectJPattern's Visitor");
        runPerfectJPattern(myWarmUpTimes, aCarPart, aVisitor);

        // benchmark perfectjpattern's
        theLogger.debug("------ Benchmarking PerfectJPattern's Visitor");
        System.gc();
        double myPerfectJPatternElapsed = System.nanoTime();
        runPerfectJPattern(myBenchmarkTimes, aCarPart, aVisitor);
        myPerfectJPatternElapsed = System.nanoTime() - myPerfectJPatternElapsed;

        // prepare the report
        double myRatio = myPerfectJPatternElapsed / myClassicElapsed;
        String myCache = aVisitor instanceof AbstractVisitor<?>
            ? "(cache)" : "(no cache)";
        String myMessage = MessageFormat.format("perfectjpattern {0} " +
            "took {1,number,####.##}% must be lower than " +
                "{2,number,####.##}% the classic", myCache,
                    myRatio * 100, aTolerance * 100);

        StringBuilder myBuilder = new StringBuilder();
        final double myOneThousand = 1000;
        myBuilder.append(LINE_BREAK);
        myBuilder.append(LINE_BREAK);
        myBuilder.append("***************************************************");
        myBuilder.append(LINE_BREAK);
        myBuilder.append(MessageFormat.format("classic: avg per call " +
            NUMBER_FORMAT + " nanos", myClassicElapsed / myBenchmarkTimes));
        myBuilder.append(LINE_BREAK);
        myBuilder.append(MessageFormat.format("perfectjpattern {1}: avg " +
            "per call " + NUMBER_FORMAT + " nanos", myPerfectJPatternElapsed /
                myBenchmarkTimes, myCache));
        myBuilder.append(LINE_BREAK);
        myBuilder.append("***************************************************");
        myBuilder.append(LINE_BREAK);
        myBuilder.append(MessageFormat.format("classic: total elapsed " +
            NUMBER_FORMAT + " millis", myClassicElapsed / myOneThousand));
        myBuilder.append(LINE_BREAK);
        myBuilder.append(MessageFormat.format("perfectjpattern {1}: total " +
            "elapsed " + NUMBER_FORMAT + " millis", myPerfectJPatternElapsed
                / myOneThousand, myCache));
        myBuilder.append(LINE_BREAK);
        myBuilder.append(myMessage);
        myBuilder.append(LINE_BREAK);
        myBuilder.append("***************************************************");
        myBuilder.append(LINE_BREAK);

        // print full report
        theLogger.info(myBuilder.toString());

        assertTrue(myMessage, myRatio < aTolerance);
    }

    //------------------------------------------------------------------------
    private void
    runClassic(int aTimes, ICarPart aCarPart, IHybridVisitor aVisitor)
    {
        for (int i = 0; i < aTimes; i++)
        {
            aCarPart.accept(aVisitor);
        }
    }

    //------------------------------------------------------------------------
    private void
    runPerfectJPattern(int aTimes, ICarPart aCarPart, IHybridVisitor aVisitor)
    {
        for (int i = 0; i < aTimes; i++)
        {
            aVisitor.visit(aCarPart);
        }
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final String NUMBER_FORMAT = "''{0,number,############}''";

    private static final int REPETITIONS = 3000;
    private static final String LINE_BREAK = System.getProperty(
        "line.separator");

    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

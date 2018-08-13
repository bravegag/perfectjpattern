//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// TestDelegator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.extras.delegate;

import java.lang.reflect.*;

import junit.framework.*;

import org.perfectjpattern.core.api.extras.delegate.*;
import org.slf4j.*;

/**
 * Test Suite for {@link Delegator} implementation.
 * <br/><br/>
 * <b>Notes</b>: Base source code implemented by Steve Lewis and Wilhelm
 * Fitzpatrick and adapted to fit PerfectJPattern componentization
 * criteria and code conventions.
 *
 * @see IDelegate
 * @see IDelegator
 * @see Delegator
 * @see DynamicDelegator
 *
 * @author <a href="mailto:smlewis@lordjoe.com">Steve Lewis</a>
 * @author <a href="mailto:wilhelmf@agileinformatics.com">Wilhelm
 * Fitzpatrick</a>
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 2.0 $ $Date: Jun 24, 2007 11:16:36 AM $
 */
@SuppressWarnings("rawtypes")
public
class TestDelegator
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testIsSuitableMethod()
    {
        int myNoArguments = 1;
        Method[] myPossibilities = AbstractDelegator.findMethod(IStringDisplay.
            class, "doDisplay", myNoArguments);

        Class myReturnClass = void.class;
        Class[] myArguments = new Class[] {String.class };

        AbstractDelegator<IStringDisplay> myDelegate = new Delegator<
            IStringDisplay>(IStringDisplay.class);

        assertTrue("AbstractDelegator.searchCandidateMethods() did not " +
            "produce the expeted result.", myDelegate.isSuitableMethod(
                myPossibilities[0], myReturnClass, myArguments));
    }

    //------------------------------------------------------------------------
    public void
    testSearchCandidateMethods()
    {
        int myNumberOfArguments = 1;
        Method[] myPossibilities =
            AbstractDelegator.findMethod(
                IBadStringDisplay.class, "doDisplay", myNumberOfArguments);

        assertEquals("AbstractDelegator.searchCandidateMethods() did not " +
                "return the expected number of matches.", 1,
                    myPossibilities.length);

        assertEquals("AbstractDelegator.searchCandidateMethods() did not " +
            "find the right match.", "doDisplay",
                myPossibilities[0].getName());
    }

    //------------------------------------------------------------------------
    public void
    testDelegateBuild()
    {
        try
        {
            // Target interface must have a single method
            new Delegator<IBadStringDisplay>(IBadStringDisplay.class);
            fail("Delegator multiple method interface was not rejected.");
        }
        catch (IllegalArgumentException anException)
        {
            // ok
        }

        try
        {
            // Target interface must have at least one method
            new Delegator<IBadStringDisplay2>(IBadStringDisplay2.class);
            fail("Delegator interface with no methods was not rejected.");
        }
        catch (NullPointerException anException)
        {
            // ok
        }
    }

    //------------------------------------------------------------------------
    /**
     * Test calls to <code>Delegator</code>
     */
    public void
    testDelegator()
    throws Exception
    {
        IDelegator<IStringDisplay> myDelegate =
            new Delegator<IStringDisplay>(IStringDisplay.class);

        Class1 myObject1 = new Class1();
        Class2 myObject2 = new Class2();

        IStringDisplay[] myItems = new IStringDisplay[3];
        myItems[0] = myDelegate.build(myObject1, "show");
        myItems[1] = myDelegate.build(myObject2, "display");
        myItems[2] = myDelegate.build(Class3.class,
            "staticDisplay");

        for (int i = 0; i < myItems.length; i++)
        {
            IStringDisplay myItem = myItems[i];
            myItem.doDisplay("test");
        }

        final int myIterationsCount = 10000;
        timingTest(myItems, myObject1, myObject2, myIterationsCount);
    }

    //------------------------------------------------------------------------
    /**
     * Test calls to <code>Delegator</code>
     */
    public void
    testDynamicDelegator()
    throws Exception
    {
        IDelegator<IDelegate> myDelegator = new DynamicDelegator(Void.TYPE,
            String.class);

        Class1 myObject1 = new Class1();
        Class2 myObject2 = new Class2();

        IDelegate[] myDelegates = new IDelegate[3];
        myDelegates[0] = myDelegator.build(myObject1, "show");
        myDelegates[1] = myDelegator.build(myObject2, "display");

        Class3.reset();
        myDelegates[2] = myDelegator.build(Class3.class, "staticDisplay");

        for (int i = 0; i < myDelegates.length; i++)
        {
            IDelegate myDelegate = myDelegates[i];
            myDelegate.invoke("test");
        }

        assertEquals("Wrong calls count", 1, myObject1.getCount());
        assertEquals("Wrong calls count", 1, myObject2.getCount());
        assertEquals("Wrong calls count", 1, Class3.getCount());
    }

    //------------------------------------------------------------------------
    /**
     * Test of timing - note set iteration large i.e. 1000000 for reasonable
     * results
     */
    public void
    timingTest(IStringDisplay[] anItems, Class1 anObject1, Class2 anObject2,
        int anIterations)
    {
        // Warm up hotspot
        for (int i = 0; i < 100; i++)
        {
            for (int j = 0; j < anItems.length; j++)
            {
                IStringDisplay myItem = anItems[j];
                myItem.doDisplay("test");
            }

            anObject1.show("test");
            anObject2.display("test");
            Class3.staticDisplay("test");
        }

        long myStart = System.currentTimeMillis();

        for (int i = 0; i < anIterations; i++)
        {
            for (int j = 0; j < anItems.length; j++)
            {
                IStringDisplay myItem = anItems[j];
                myItem.doDisplay("test");
            }
        }

        long myEnd = System.currentTimeMillis();
        double myDelegateTime = (myEnd - myStart) / 1000.;
        double myPerIteration = (1000. * 1000. * myDelegateTime) / anIterations;

        myStart = System.currentTimeMillis();

        for (int myJ = 0; myJ < anIterations; myJ++)
        {
            anObject1.show("test");
            anObject2.display("test");
            Class3.staticDisplay("test");
        }

        myEnd = System.currentTimeMillis();

        double myDirectTime = (myEnd - myStart) / 1000.;
        double myPerCallIteration = (1000. * 1000. * myDirectTime) /
            anIterations;

        theLogger.debug("Ran '" + anIterations + "' iterations ");
        theLogger.debug("Delegator Test took '" + myDelegateTime + "' secs");
        theLogger.debug("per iteration '" + myPerIteration + "' microsecs");
        theLogger.debug("Direct Calls took '" + myDirectTime + "' secs");
        theLogger.debug("per iteration '" + myPerCallIteration + "' microsecs");
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    private static
    interface
    IStringDisplay
    {
        //--------------------------------------------------------------------
        public void
        doDisplay(String aValue);
    }

    //------------------------------------------------------------------------
    private static
    interface IBadStringDisplay
    {
        //--------------------------------------------------------------------
        public void
        doDisplay(String aValue);

        //--------------------------------------------------------------------
        public void
        doDisplay2(String aValue);
    }

    //------------------------------------------------------------------------
    private static
    interface IBadStringDisplay2
    {
        // empty
    }

    //------------------------------------------------------------------------
    private static
    class Class1
    {
        //--------------------------------------------------------------------
        public void
        show(String aValue)
        {
            theCount++;
        }

        //--------------------------------------------------------------------
        public int
        getCount()
        {
            return theCount;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private int theCount;
    }

    //------------------------------------------------------------------------
    private static
    class Class2
    {
        //--------------------------------------------------------------------
        public void
        display(String aValue)
        {
            theCount++;
        }

        //--------------------------------------------------------------------
        public int
        getCount()
        {
            return theCount;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private int theCount;
    }

    //------------------------------------------------------------------------
    // CHECKSTYLE:OFF
    private final static
    class Class3
    // CHECKSTYLE:ON
    {
        //--------------------------------------------------------------------
        public static void
        staticDisplay(String aValue)
        {
            theCount++;
        }

        //--------------------------------------------------------------------
        public static int
        getCount()
        {
            return theCount;
        }

        //--------------------------------------------------------------------
        public static void
        reset()
        {
            theCount = 0;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private static int theCount;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class.
     */
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}
//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestAbstractHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.chainofresponsibility;

import junit.framework.*;

import org.slf4j.*;

/**
 * Test Suite for <code>AbstractHandler</code> implementation.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 26, 2007 12:42:39 AM $
 */
public 
class TestAbstractParameterlessHandler 
extends TestCase 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Test that all handlers are successfully invoked.
     */
    public void 
    testChainSuccessfullyInvoked() 
    {
        theLogger.debug("Running assertions ... ");

        // Test that every element had chance to execute
        assertTrue("First element was never invoked.",  theFirst.isCalled());
        assertTrue("Second element was never invoked.", theSecond.isCalled());
        assertTrue("Third element was never invoked.", theThird.isCalled());

        theLogger.debug("Completed test");
    }

    //------------------------------------------------------------------------
    /**
     * Test that chain elements were invoked in the correct order.
     */
    public void 
    testChainInvokedInCorrectOrder() 
    {
        theLogger.debug("Running assertions ... ");
        
        // Test that the chain has the right order
        assertEquals("First element was not correctly invoked first.", 
            1, theFirst.getIndex());
        assertEquals("Second element was not correctly invoked second.", 2,
            theSecond.getIndex());
        assertEquals("Third element was not correctly invoked third.", 3, 
            theThird.getIndex());

        theLogger.debug("Completed test");
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    @Override
    protected void 
    setUp() 
    throws Exception 
    {
        super.setUp();

        theLogger.debug(
            "Creating ChainOfResponsibility test fixture ... ");

        // Resets the static counter
        TestChainBasicElement.resetCounter();

        fixture();

        theLogger.debug(
            "Completed ChainOfResponsibility test fixture.");
    }
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    /**
     * Provides sample fixture for the Chain of Responsibility Pattern.
     */
    protected void 
    fixture() 
    {
        theLogger.debug("Step #1 := Create handlers");
        theSecond = new TestChainBasicElement();
        theFirst = new TestChainBasicElement(theSecond);
        theThird = new TestChainBasicElement();

        theLogger.debug("Step #2 := Associate all chain handlers");
        theSecond.setSuccessor(theThird);

        theLogger.debug("Step #3 := Modify default IChainStrategy");
        theFirst.setChainStrategy(AllHandleStrategy.getInstance());
        theSecond.setChainStrategy(AllHandleStrategy.getInstance());
        
        theLogger.debug("Step #4 := Trigger the chain execution");
        theFirst.start();
    }    

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Concrete implementation of <code>IHandler</code>.
     */
    private static 
    class TestChainBasicElement 
    extends AbstractParameterlessHandler
    {
        //-------------------------------------------------------------------
        public
        TestChainBasicElement()
        {
            super();
        }

        //-------------------------------------------------------------------
        public
        TestChainBasicElement(TestChainBasicElement aSuccessor)
        {
            super(aSuccessor);
        }
        
        //-------------------------------------------------------------------
        /**
         * Resets the Counter
         */
        public static void 
        resetCounter() 
        {
            TestChainBasicElement.theCounter = 0;
        }

        //-------------------------------------------------------------------
        public void 
        handle() 
        {
            // test code here ...
            theIndex = ++theCounter;
        }

        //-------------------------------------------------------------------
        public final boolean
        isCalled()
        {
            return theIndex != -1;
        }
        
        //-------------------------------------------------------------------
        /**
         * @return the theIndex
         */
        public final int 
        getIndex() 
        {
            return theIndex;
        }

        //-------------------------------------------------------------------
        // members
        //-------------------------------------------------------------------
        private static int theCounter = 0;
        private int theIndex = -1;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Chain of Responsibility elements
     */
    private TestChainBasicElement theFirst = null;
    private TestChainBasicElement theSecond = null;
    private TestChainBasicElement theThird = null;    
    
    /**
     * Provides logging services for this class.
     */    
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestExactMatchAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.adapter;

import java.util.*;

import junit.framework.*;

import org.perfectjpattern.core.api.structural.adapter.*;
import org.slf4j.*;


/**
 * Test suite for the {@link Adapter} and {@link ExactMatchAdaptingStrategy} 
 * implementations
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jan 28, 2009 3:05:35 PM $
 */
public 
class TestExactMatchAdapter
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testValidate()
    {
        IAdaptingStrategy myExactMatchStrategy = 
            new ExactMatchAdaptingStrategy();
        
        try
        {
            myExactMatchStrategy.validate(ITargetInterface.class, 
                new GoodAdaptee(), new Object());
            
            // ok
        }
        catch (IllegalArgumentException anException)
        {
            fail("ExactMatch did not validate a good adaptee properly");
        }

        try
        {
            myExactMatchStrategy.validate(ITargetInterface.class, 
                new BadAdaptee(), new Object());
            
            fail("ExactMatch did not validate a bad adaptee properly");
        }
        catch (IllegalArgumentException anException)
        {
            // ok
        }
    }

    //------------------------------------------------------------------------
    public void
    testAdapter()
    {
        theLogger.debug("Step #1: Create Adapter with default " +
            "AdaptingStrategy");
        IAdapter<ITargetInterface, GoodAdaptee> myAdapter = new Adapter<
            ITargetInterface, GoodAdaptee>(ITargetInterface.class, 
                new GoodAdaptee());
        
        theLogger.debug("Step #2: Acquire the Target (Componet type view " +
            "of the Adaptee)");        
        ITargetInterface myTarget = myAdapter.getTarget();
        
        theLogger.debug("Step #3: Invoke methods on the Target interface");
        myTarget.method1("argument", Integer.valueOf(1));        
        myTarget.method2();
        
        theLogger.debug("Step #4: Run assertions");        
        assertEquals("Adapter did not invoke method1 as expected", 
            theInvokations.get(0), "method1");
        assertEquals("Adapter did not invoke method2 as expected", 
            theInvokations.get(1), "method2");
    }
    
    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    private 
    interface ITargetInterface
    {
        //--------------------------------------------------------------------
        public void
        method1(String anArgument1, Integer anArgument2);

        //--------------------------------------------------------------------
        public void
        method2();

        //--------------------------------------------------------------------
        public boolean
        isSupported();
    }
    
    //------------------------------------------------------------------------
    @SuppressWarnings("unused")
    private 
    class GoodAdaptee
    {
        //--------------------------------------------------------------------
		public void
        method1(String anArgument1, Integer anArgument2)
        {
            theInvokations.add("method1");
        }

        //--------------------------------------------------------------------
        public void
        method2()
        {
            theInvokations.add("method2");            
        }
        
        //--------------------------------------------------------------------
        public boolean
        isSupported()
        {
            return true;
        }        

        //--------------------------------------------------------------------
        public void
        method3(Double anArgument1)
        {
            // must not be called
        }
    }

    //------------------------------------------------------------------------
    @SuppressWarnings("unused")
    private 
    class BadAdaptee
    {
        //--------------------------------------------------------------------
        public void
        method1(String anArgument1, Integer anArgument2)
        {
            // empty
        }
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final List<String> theInvokations = new ArrayList<String>();
    
    /**
     * Provides logging services for this class.
     */
    // CHECKSTYLE:OFF
    private final static Logger theLogger = LoggerFactory.getLogger(
        TestExactMatchAdapter.class);
    // CHECKSTYLE:ON    
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// NameMatchAdaptingStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Test suite for the {@link Adapter} and {@link NameMatchAdaptingStrategy} 
 * implementations
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jan 28, 2009 3:05:35 PM $
 */
public 
class TestNameMatchAdapter
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testValidate()
    {
        try
        {
            Map<String, String> myMethodMapping = new HashMap<String, String>();
            myMethodMapping.put("method1", "method3");
            myMethodMapping.put("method2", "method4");
            
            IAdaptingStrategy myNameMatchStrategy = 
                new NameMatchAdaptingStrategy(myMethodMapping);
            
            myNameMatchStrategy.validate(ITargetInterface.class, 
                new GoodAdaptee(), new Object());
            
            // ok
        }
        catch (IllegalArgumentException anException)
        {
            fail("ExactMatch did not validate a good adaptee properly");
        }

        try
        {
            Map<String, String> myMethodMapping = new HashMap<String, String>();
            myMethodMapping.put("method1", "method6");
            
            IAdaptingStrategy myNameMatchStrategy = 
                new NameMatchAdaptingStrategy(myMethodMapping);
            
            myNameMatchStrategy.validate(ITargetInterface.class, 
                new BadAdaptee(), new Object());
            
            fail("ExactMatch did not validate a bad adaptee properly");
        }
        catch (IllegalArgumentException anException)
        {
            // ok
        }

        try
        {
            Map<String, String> myMethodMapping = new HashMap<String, String>();
            myMethodMapping.put("method1", "method6");
            myMethodMapping.put("method2", "method7");
            
            IAdaptingStrategy myNameMatchStrategy = 
                new NameMatchAdaptingStrategy(myMethodMapping);
            
            myNameMatchStrategy.validate(ITargetInterface.class, 
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
        theLogger.debug("Step #1: Define how to map Target methods to " +
            "Adaptee methods");
        Map<String, String> myMethodsMapping = new HashMap<String, String>();
        myMethodsMapping.put("method1", "method3");
        
        IAdaptingStrategy myNameMatchStrategy = new NameMatchAdaptingStrategy(
            myMethodsMapping);
        
        theLogger.debug("Step #2: Create Adapter with " +
            "NameMatchAdaptingStrategy");
        IAdapter<ITargetInterface, GoodAdaptee> myAdapter = new SampleAdapter(
                new GoodAdaptee(), myNameMatchStrategy);
        
        theLogger.debug("Step #3: Acquire the Target (Componet type view " +
            "of the Adaptee)");
        ITargetInterface myTarget = myAdapter.getTarget();
        
        theLogger.debug("Step #4: Invoke methods on the Target interface");
        myTarget.method1("argument", Integer.valueOf(1));        
        myTarget.method2();
        
        theLogger.debug("Step #5: Run assertions");        
        assertEquals("Adapter did not invoke method1 as expected", 
            theInvokations.get(0), "method3");
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
    }
    
    //------------------------------------------------------------------------
    public 
    class SampleAdapter
    extends Adapter<ITargetInterface, GoodAdaptee>
    {
        //--------------------------------------------------------------------
        public SampleAdapter(GoodAdaptee anAdaptee, 
            IAdaptingStrategy anAdaptingStrategy) 
        throws IllegalArgumentException
        {
            super(ITargetInterface.class, anAdaptee, anAdaptingStrategy);
        }        

        //--------------------------------------------------------------------
        public void
        method2()
        {
            theInvokations.add("method2");            
        }
    }
    
    //------------------------------------------------------------------------
    @SuppressWarnings("unused")
    private 
    class GoodAdaptee
    {
        //--------------------------------------------------------------------
        public void
        method3(String anArgument1, Integer anArgument2)
        {
            theInvokations.add("method3");
        }

        //--------------------------------------------------------------------
        public void
        method4()
        {
            theInvokations.add("method4");            
        }

        //--------------------------------------------------------------------
        public void
        method5(Double anArgument1)
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
        method6(Integer anArgument1, String anArgument2)
        {
            // empty
        }

        //--------------------------------------------------------------------
        public void
        method7()
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
        TestNameMatchAdapter.class);
    // CHECKSTYLE:ON        
}

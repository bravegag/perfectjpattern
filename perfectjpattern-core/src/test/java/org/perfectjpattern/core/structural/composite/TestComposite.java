//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestComposite.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.composite;

import java.util.*;

import junit.framework.*;

import org.perfectjpattern.core.api.structural.composite.*;
import org.slf4j.*;

/**
 * Test Suite for the {@link Composite} implementation.
 * 
 * @see Composite
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 18, 2007 11:58:58 PM $
 */
public 
class TestComposite
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testComposition()
    {
        theLogger.debug("Step #1: Create four Component elements");
        ISomeComponent myComponent1 = new SomeComponent();
        ISomeComponent myComponent2 = new SomeComponent();
        ISomeComponent myComponent3 = new SomeComponent();
        ISomeComponent myComponent4 = new SomeComponent();
                
        theLogger.debug("Step #2: Create Composite 1");
        IComposite<ISomeComponent> myComposite1 = new Composite<ISomeComponent>(
            ISomeComponent.class);  

        theLogger.debug("Step #3: Create Composite 2");
        IComposite<ISomeComponent> myComposite2 = new Composite<ISomeComponent>(
            ISomeComponent.class);  

        theLogger.debug("Step #4: Create compositions");
        myComposite1.addAll(myComponent1, myComponent2);
        
        myComposite2.addAll(myComponent3, myComponent4, myComposite1.
            getComponent());
        
        theLogger.debug("Step #5: Call a method on the Composite " +
            "Component view that triggers execution over the whole " +
            "composition");
        myComposite2.getComponent().printValue("Hello World!");
        
        theLogger.debug("Prepare to run assertions");
        Collection<ISomeComponent> myComponents = new ArrayList<
            ISomeComponent>();
        myComponents.add(myComponent1);
        myComponents.add(myComponent2);
        myComponents.add(myComponent3);
        myComponents.add(myComponent4);        
        
        theLogger.debug("Running assertions");
        for (ISomeComponent myComponent : myComponents)
        {
            assertTrue("All Elements must have been called.", ((SomeComponent) 
                myComponent).isCalled());
        }
    }
    
    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Definition of an example Component interface.
     */
    public static
    interface ISomeComponent
    {
        //--------------------------------------------------------------------
        public void 
        printValue(String aValue);
    }    
    
    //------------------------------------------------------------------------
    /**
     * Implementation of the interface defined above. 
     */
    private static
    class SomeComponent
    implements ISomeComponent
    {
        //--------------------------------------------------------------------
        public void 
        printValue(String aValue)
        {
            theLogger.debug(aValue);
            
            theCalled = true;
        }        
        
        //--------------------------------------------------------------------
        public boolean
        isCalled()
        {
            return theCalled;
        }
        
        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private boolean theCalled;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class.
     */
    // CHECKSTYLE:OFF
    private final static Logger theLogger = LoggerFactory.getLogger(
        TestComposite.class);
    // CHECKSTYLE:ON
}

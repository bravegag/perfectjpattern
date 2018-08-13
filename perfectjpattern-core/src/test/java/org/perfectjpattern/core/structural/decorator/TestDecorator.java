//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestDecorator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.decorator;

import junit.framework.*;

import org.perfectjpattern.core.api.structural.decorator.*;
import org.slf4j.*;

/**
 * Test Suite for <code>AbstarctDecorator</code> implementation.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 25, 2007 4:28:45 PM $
 */
public class TestDecorator
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testDecorator()
    {
        theLogger.debug("Step #1: Create a testing Component");
        ISomeComponent myComponent = new SomeComponent();

        theLogger.debug("Step #2: Create Component Decorator");
        IDecorator<ISomeComponent, ISomeComponent> myDecorator = 
            new ComponentDecorator(myComponent);

        theLogger.debug("Step #3: Use the Component reference of Decorator ");
        myDecorator.getComponent().printValue("Hello World!");
        
        theLogger.debug("Running assertions");
        assertTrue("printValue(...) must have been called in the Decorator", 
            ((ComponentDecorator) myDecorator).isCalled());

        assertTrue("printValue(...) must have been called in the Component", 
            ((SomeComponent) myComponent).isCalled());
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
    public static
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
    public static
    class ComponentDecorator
    extends AbstractDecorator<ISomeComponent, ISomeComponent>
    {
        //--------------------------------------------------------------------
        public 
        ComponentDecorator(ISomeComponent aComponent)
        {
            super(ISomeComponent.class, aComponent);
        }
        
        //--------------------------------------------------------------------
        public void 
        printValue(String aValue)
        {
            theLogger.debug(aValue);
            
            theCalled = true;
            
            getDecorated().printValue(aValue);
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
    private static final Logger theLogger = LoggerFactory.getLogger(
        TestDecorator.class);
    // CHECKSTYLE:ON    
}

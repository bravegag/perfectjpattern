//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestProxy.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.proxy;

import junit.framework.*;

import org.perfectjpattern.core.api.structural.proxy.*;
import org.slf4j.*;

/**
 * Test suite for the {@link AbstractProxy} implementation
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jun 18, 2008 10:57:21 PM $
 */
public 
class TestProxy
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testProxying()
    {
        theLogger.debug("Step #1: Create component and proxy");
        SomeComponent mySubject = new SomeComponent();        
        IProxy<ISomeComponent> myProxy = new AbstractProxy<
            ISomeComponent>(ISomeComponent.class, mySubject)
        {
            // empty
        };
        
        theLogger.debug("Step #2: Make sure that the 'Subject' is correct");
        assertSame("IProxy.getSubject not correctly implemented", mySubject, 
            myProxy.getRealSubject());
                
        theLogger.debug("Step #3: Get reference to proxied component");
        ISomeComponent myComponent = myProxy.getSubject();

        theLogger.debug("Step #4: Call methods through the proxied component");
        myComponent.method1("Hello proxy world!");
        myComponent.method2();
        
        theLogger.debug("Running assertions");
        assertTrue("method1 was not called", mySubject.isMethod1Called());
        
        assertTrue("method2 was not called", mySubject.isMethod2Called());
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
        method1(String aValue);

        //--------------------------------------------------------------------
        public void 
        method2();
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
        method1(String aValue)
        {
            theLogger.debug(aValue);
            
            theMethod1Called = true;
        }        
        
        //--------------------------------------------------------------------
        public void 
        method2()
        {
            theMethod2Called = true;
        }        

        //--------------------------------------------------------------------
        public boolean
        isMethod1Called()
        {
            return theMethod1Called;
        }
        
        //--------------------------------------------------------------------
        public boolean
        isMethod2Called()
        {
            return theMethod2Called;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private boolean theMethod1Called;
        private boolean theMethod2Called;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class.
     */
    // CHECKSTYLE:OFF
    private final static Logger theLogger = LoggerFactory.getLogger(TestProxy.class);
    // CHECKSTYLE:ON
}

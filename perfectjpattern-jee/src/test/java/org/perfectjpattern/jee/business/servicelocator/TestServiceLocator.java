//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestServiceLocator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.business.servicelocator;

import javax.sql.*;

import org.perfectjpattern.jee.api.business.servicelocator.*;
import org.perfectjpattern.support.test.*;

/**
 * Test suite for the {@link ServiceLocator} implementation
 *  
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 9, 2009 8:20:48 PM $
 */
public 
class TestServiceLocator
extends AbstractTestSingleton<ServiceLocator>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    TestServiceLocator()
    {
        super(ServiceLocator.class, ServiceLocator.getInstance());
    }
    
    //------------------------------------------------------------------------
    public void
    testServiceLocator()
    {
        System.setProperty("log4j.category.OpenEJB", "debug"); 
        
        IServiceLocator myLocator = ServiceLocator.getInstance();
        
        ICalculatorRemote myCalculator = myLocator.locate("CalculatorRemote");
        assertNotNull("EJB lookup was unsuccessful", myCalculator);
        
        int myResult = myCalculator.sum(2, 3);        
        assertEquals("EJB calculator call was unsuccessful", 5, myResult);
        
        // test the Service caching
        ICalculatorRemote myCached = myLocator.locate("CalculatorRemote");
        assertSame("Service was not cached", myCalculator, myCached);
        
        // test trying to lookup a misspelled Service
        try
        {
            myCalculator = myLocator.locate("CalculatorRimote");
            
            fail("ServiceLocatorException must be thrown requesting " +
                "unmatching services");
        }
        catch (ServiceLocatorException anException)
        {
            // ok
        }
        
        // test trying to lookup the wrong Service type
        try
        {
            myLocator.locate(DataSource.class, "CalculatorRemote");
            
            fail("ServiceLocatorException must be thrown requesting " +
                "wrong type of services");
        }
        catch (ServiceLocatorException anException)
        {
            // ok
        }

        myLocator.shutdown();        
    }
}

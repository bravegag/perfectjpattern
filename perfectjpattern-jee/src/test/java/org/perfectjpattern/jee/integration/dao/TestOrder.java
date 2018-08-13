//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestOrder.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.integration.dao;

import junit.framework.*;

import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Test suite for the {@link Order} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 16, 2009 10:09:38 PM $
 */
public 
class TestOrder
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testConstructor()
    {
        // create fixture instance
        IOrder myOrder = new Order("name");
        
        // run assertions
        assertEquals("Order did not work as expected", "name", 
            myOrder.getPropertyName());
        assertEquals("Order did not work as expected", AscDescEnum.ASC, 
            myOrder.getAscDesc());
        
        // test preconditions
        try
        {
            new Order(null);
            
            fail("Order preconditions are not properly checked");
        }
        catch (NullPointerException anException)
        {
            // ok
        }
        
        try
        {
            new Order("good", null);
            
            fail("Order preconditions are not properly checked");
        }
        catch (NullPointerException anException)
        {
            // ok
        }
    }

    //------------------------------------------------------------------------
    public void
    testToString()
    {
        // create fixture instance
        IOrder myOrder = new Order("name", AscDescEnum.DESC);

        // run assertions
        assertEquals("Order toString did not work as expected", "e.name desc", 
            myOrder.toString());        
    }

    //------------------------------------------------------------------------
    public void
    testValueOf()
    {
        // create fixture instance
        IOrder myOrder = new Order("name", AscDescEnum.DESC);

        // run assertions
        assertEquals("Order valueOf did not work as expected", myOrder, 
            Order.valueOf(myOrder.toString()));        
    }
    
    //------------------------------------------------------------------------
    public void
    testHashCode()
    {
        // create fixture instance
        IOrder myOrder = new Order("name", AscDescEnum.DESC);

        // run assertions
        assertEquals("Order hashCode did not work as expected", 68311515, 
            myOrder.hashCode());        
    }    
}

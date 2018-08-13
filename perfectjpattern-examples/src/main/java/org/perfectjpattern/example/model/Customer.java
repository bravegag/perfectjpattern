//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Customer.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.example.model;

import java.util.*;

/**
 * Customer Model object
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 5, 2008 2:59:04 AM $
 */
public 
class Customer
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    Customer()
    {
        // do nothing
    }

    //------------------------------------------------------------------------
    public 
    Customer(String aName)
    {
        theName = aName;        
        theOrders = new ArrayList<Order>();
    }

    //------------------------------------------------------------------------
    /**
     * Returns the id
     *
     * @return the id
     */
    public Long 
    getId()
    {
        return theId;
    }
    
    //------------------------------------------------------------------------
    /**
     * Sets the id of the person
     * 
     * @param anId the id to set
     */
    public void 
    setId(Long anId)
    {
        theId = anId;
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns the name
     *
     * @return the name
     */
    public String 
    getName()
    {
        return theName;
    }
    
    //------------------------------------------------------------------------
    /**
     * Sets the name of the person
     *
     * @param aName the name to set
     */
    public void 
    setName(String aName)
    {
        theName = aName;
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns the orders
     * 
     * @return the orders
     */
    public List<Order> 
    getOrders()
    {
        return theOrders;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the orders 
     * 
     * @param anOrders the orders to set
     */
    public void 
    setOrders(List<Order> anOrders)
    {
        theOrders = anOrders;
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private Long theId;
    private String theName;
    private List<Order> theOrders;
}

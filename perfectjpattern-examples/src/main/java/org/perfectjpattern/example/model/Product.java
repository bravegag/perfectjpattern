//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Product.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Product Model object
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 5, 2008 2:59:45 AM $
 */
public 
class Product
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    Product()
    {
        // do nothing
    }

    //------------------------------------------------------------------------
    public 
    Product(String aName, double aListPrice)
    {
        theName = aName;
        theListPrice = aListPrice;
        theOrders = new HashSet<Order>();
    }

    //------------------------------------------------------------------------
    /**
     * Returns the id
     *
     * @return the id
     */
    public long 
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
    setId(long anId)
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
     * @param anName the name to set
     */
    public void 
    setName(String anName)
    {
        theName = anName;
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns the listPrice
     * 
     * @return the listPrice
     */
    public double 
    getListPrice()
    {
        return theListPrice;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the listPrice
     * 
     * @param aListPrice the listPrice to set
     */
    public void 
    setListPrice(double aListPrice)
    {
        theListPrice = aListPrice;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the orders
     * 
     * @return the orders
     */
    public Set<Order> 
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
    setOrders(Set<Order> anOrders)
    {
        theOrders = anOrders;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public final int 
    hashCode()
    {
        final int myPrime = 31;
        
        int myResult = 1;
        myResult = myPrime * myResult + ((theName == null) 
            ? 0 : theName.hashCode());
        
        return myResult;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public final boolean 
    equals(Object anAnother)
    {
        if (this == anAnother)
        {
            return true;
        }
        
        if (anAnother == null)
        {
            return false;
        }
        
        if (!(anAnother instanceof Product))
        {
            return false;
        }
        
        final Product myAnother = (Product) anAnother;
        return (getName() == null && myAnother.getName() == null) ||
               (getName() != null && myAnother.getName() != null 
             && getName().equals(myAnother.getName()));
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private long theId;
    private String theName;
    private double theListPrice;
    private Set<Order> theOrders;
}
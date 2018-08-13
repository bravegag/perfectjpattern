//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Order.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Order Model object
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 5, 2008 11:49:14 AM $
 */
public 
class Order
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    Order()
    {
        // do nothing
    }
    
    //------------------------------------------------------------------------
    public 
    Order(Customer aCustomer, Date aDate, Set<Product> aProducts)
    {
        theCustomer = aCustomer;
        theDate = aDate;
        theProducts = aProducts;
    }

    //------------------------------------------------------------------------
    /**
     * @return the id
     */
    public Long 
    getId()
    {
        return theId;
    }

    //------------------------------------------------------------------------
    /**
     * @param anId the id to set
     */
    public void 
    setId(Long anId)
    {
        theId = anId;
    }
    
    //------------------------------------------------------------------------
    /**
     * @return the date
     */
    public Date 
    getDate()
    {
        return theDate;
    }

    //------------------------------------------------------------------------
    /**
     * @param aDate the date to set
     */
    public void 
    setDate(Date aDate)
    {
        theDate = aDate;
    }

    //------------------------------------------------------------------------
    /**
     * @return the products
     */
    public Set<Product> 
    getProducts()
    {
        return theProducts;
    }

    //------------------------------------------------------------------------
    /**
     * @param aProducts the products to set
     */
    public void 
    setProducts(Set<Product> aProducts)
    {
        theProducts = aProducts;
    }
    
    //------------------------------------------------------------------------
    /**
     * @return the customer
     */
    public Customer 
    getCustomer()
    {
        return theCustomer;
    }

    //------------------------------------------------------------------------
    /**
     * @param anCustomer the customer to set
     */
    public void 
    setCustomer(Customer anCustomer)
    {
        theCustomer = anCustomer;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private Long theId;
    private Date theDate;
    private Customer theCustomer;
    private Set<Product> theProducts;
}

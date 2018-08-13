//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ICustomerDao.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.integration.dao;

import java.util.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.integration.dao.*;

/**
 * Abstract definition of the Customer DAO. This definition was introduced
 * to support the Spring Generic DAO example implementation.
 * <br/><br/>
 * Note that this interface is all the Java code needed to provide such 
 * custom finder capability. The {@link SpringGenericDao} implementation 
 * will take care of the rest that includes all the plumbing to Hibernate.
 * At this point it is only required the appropriate Hibernate mapping.
 *
 * @see SpringGenericDao
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 9, 2008 1:14:39 AM $
 */
public 
interface ICustomerDao
extends IGenericDao<Long, Customer>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the List of matching {@link Customer} instances. Finds the
     * customers that have completed a minimum number of orders over the given 
     * total amount within the given period.
     * @param aBegin Period begin date
     * @param anEnd Period end date
     * @param aMinimum Minimum number of orders to search for 
     * 
     * @return List of matching {@link Customer} instances
     */
    @QueryParameters(names = { "Begin", "End", "Minimum" })
    public List<Customer> 
    findByNumberOfOrdersBetween(Date aBegin, Date anEnd, long aMinimum);

    //------------------------------------------------------------------------
    /**
     * Returns the List of matching {@link Customer} instances. Finds the
     * top spenders/customers that have completed orders over the given total
     * amount within the given period.
     * @param aBegin Period begin date
     * @param anEnd Period end date
     * @param aTotal Total minimum spending to search for
     * 
     * @return List of matching {@link Customer} instances
     */
    @QueryParameters(names = { "Begin", "End", "Total" })
    public List<Customer> 
    findByTotalSpendingBetween(Date aBegin, Date anEnd, double aTotal);
}

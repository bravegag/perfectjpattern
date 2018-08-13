//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IBaseValueListHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.business.valuelisthandler;

import java.io.*;
import java.util.*;

import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * <b>Value List Handler Pattern</b>: The client requires a list of items 
 * from the service for presentation. The number of items in the list is 
 * unknown and can be quite large in many instances. Use a Value List 
 * Handler to control the search, cache the results, and provide the 
 * results to the client in a result set whose size and traversal 
 * meets the client's requirements.
 * <br/><br/>
 * 
 * <b>Responsibility</b> Abstract definition of the "Value List Handler":
 * <ul>
 * <li>executes the required query when requested by the client.</li> 
 * <li>obtains the query results, which it manages in a privately held 
 * collection represented by the ValueList object.</li> 
 * <li>creates and manipulates the ValueList collection.</li> 
 * <li>When the client requests the results, the ValueListHandler 
 * obtains the Transfer Objects from the cached ValueList, creates 
 * a new collection of Transfer Objects, serializes the collection, 
 * and sends it back to the client.</li>
 * <li>The ValueListHandler also tracks the current index and size 
 * of the list.</li>
 * </ul>
 * {@link IBaseValueListHandler} offers to clients the same level of 
 * functionality offered by {@link IBaseReadOnlyDao} only in a Stateful 
 * fashion so that the results are cached for remote clients. Implementations 
 * of {@link IBaseValueListHandler} should be only a thin wrapper around 
 * the underlying {@link IBaseReadOnlyDao} implementations
 * <br/>
 * <br/>
 * @param <Element> Model class type. Element types must be 
 *        {@link Serializable}. This is because the main use-case of
 *        this Pattern is implementing remote Stateful Session EJB 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 11, 2009 12:58:09 PM $
 */
public 
interface IBaseValueListHandler<Element extends Serializable>
extends IValueListIterator<Element>
{
    //------------------------------------------------------------------------
    // public    
    //------------------------------------------------------------------------
    /**
     * Executes a {@link IBaseReadOnlyDao#findAll(IOrder...)} search and 
     * cache the results. Optionally specify Order Criteria 
     * 
     * @param anOrder The optional Order Criteria
     * @throws IllegalArgumentException 'anOrder' must not be null
     * @throws ListHandlerException
     */
    public void 
    executeQueryAll(IOrder ... anOrder)
    throws IllegalArgumentException, ListHandlerException;

    //------------------------------------------------------------------------
    /**
     * Executes a {@link IBaseReadOnlyDao#findByNamedQuery(String, Object...)} 
     * search and cache the results. 
     * 
     * @param aQueryName The Query name
     * @param anArguments The arguments required to execute the Query
     * @throws IllegalArgumentException 'aQueryName' must not be null
     * @throws IllegalArgumentException 'aQueryName' was not found
     * @throws IllegalArgumentException 'anArguments' must not be null
     * @throws IllegalArgumentException 'anArguments' does not match the 
     *         requirements of 'aQueryName'
     * @throws ListHandlerException
     */
    public void 
    executeNamedQuery(String aQueryName, Object ... anArguments)
    throws IllegalArgumentException, ListHandlerException;

    //------------------------------------------------------------------------
    /**
     * Executes a {@link IBaseReadOnlyDao#findByNamedQuery(String, Object...)} 
     * search and cache the results. 
     * 
     * @param aQueryName The Query name
     * @param aNamedParameters The parameters required to execute the Query
     * @throws IllegalArgumentException 'aQueryName' must not be null
     * @throws IllegalArgumentException 'aQueryName' was not found
     * @throws IllegalArgumentException 'aNamedParameters' must not be null
     * @throws IllegalArgumentException 'aNamedParameters' does not match the 
     *         requirements of 'aQueryName'
     * @throws ListHandlerException
     */
    public void 
    executeNamedQuery(String aQueryName, Map<String, Object> aNamedParameters)
    throws IllegalArgumentException, ListHandlerException;
}

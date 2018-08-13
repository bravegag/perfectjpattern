//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IQuery.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.structural.adapter.*;

/**
 * Abstract definition of Query available for the DAO implementation.
 * The DAO API exposes this interface but it will be automatically adapted
 * from the underlying JPA-specific implementation (see {@link IAdapter}).
 * <br/><br/> 
 * JPA and specific implementations define similar abstractions e.g.
 * Query but despite their similarities, their types actually 
 * mismatch making it difficult to provide a generic unified DAO 
 * solution.
 * <br/><br/> 
 * Note that this is a subset of the functionality that the underlying 
 * implementation offers. This is the subset required to make the DAO 
 * portable. 
 * <br/>
 * <br/>
 * <b>Please note that this abstraction is intended to be used ONLY by 
 * the DAO framework and not by client code of the DAO framework</b>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 18, 2009 10:31:58 AM $
 */
public 
interface IQuery
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     *  Returns the query results as a List
     * 
     * @return query results as a List
     */
    public <E> List<E> 
    getResultList();

    //------------------------------------------------------------------------
    /**
     *  Returns the query result as a single result
     * 
     * @return query result as a single result
     */
    public Object 
    getSingleResult();

    //------------------------------------------------------------------------
    /**
     * Returns the number of entities updated or deleted. Executes an update 
     * or delete statement.
     * 
     * @return number of entities updated or deleted
     */
    public int 
    executeUpdate();
    
    //------------------------------------------------------------------------
    /**
     * Returns the underlying Query instance. Set the maximum number of 
     * results to retrieve.
     * 
     * @param aMaxResult maximum number of results 
     * @return underlying Query instance
     */
    public Object 
    setMaxResults(int aMaxResult);

    //------------------------------------------------------------------------
    /**
     * Returns the underlying Query instance. Set the position of the 
     * first result to retrieve.
     * 
     * @param aStartPosition position of the first result 
     * @return underlying Query instance
     */
    public Object
    setFirstResult(int aStartPosition);
    
    //------------------------------------------------------------------------
    /**
     * Returns the underlying Query instance. Assigns Query parameters by name.
     * 
     * @param aName Parameter name
     * @param aValue Parameter value
     * @return underlying Query instance
     */
    public IQuery 
    setParameter(String aName, Object aValue);

    //------------------------------------------------------------------------
    /**
     * Returns the underlying Query instance. Assigns Query parameters by 
     * position.
     * 
     * @param aPosition Parameter position
     * @param aValue Parameter value
     * @return underlying Query instance
     */
    public IQuery 
    setParameter(int aPosition, Object aValue);    
        
    //------------------------------------------------------------------------
    /**
     * Returns the names of all named parameters of the query, in no 
     * particular order
     * 
     * @return the parameter names, in no particular order
     */
    public String[] 
    getNamedParameters();
}

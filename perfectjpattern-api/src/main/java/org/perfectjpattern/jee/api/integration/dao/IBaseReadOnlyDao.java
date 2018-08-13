//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IBaseReadOnlyDao.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * <b>Data Access Object (DAO) Pattern</b>: Abstracts from any direct type of 
 * database or persistence mechanism. Provides specific operations without 
 * exposing details of the database. 
 * <br/><br/>
 * <b>Responsibility</b> : Partial base Data Access Object abstraction
 * providing Read-Only features: 
 * <br/>
 * <br/>
 * <ul>
 * <li>Find an Element by ID</li> 
 * <li>Find Elements using a Named Query with positional parameters</li> 
 * <li>Find Elements using a Named Query with named parameters</li> 
 * <li>Find all Elements and optionally specify an {@link OrderCriteria}</li> 
 * <li>Find by Page according to some {@link Order} i.e. Paging, Page-By-Page 
 * Iterator</li> 
 * <li>Ask if it contains a given Element</li>
 * <li>Ask for the total number of records</li>
 * </ul>
 * 
 * @param <Id> Identification type
 * @param <Element> Element type
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Data_Access_Object">Data Access 
 * Object wiki definition</a> 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 19, 2009 11:08:44 AM $
 */
public 
interface IBaseReadOnlyDao<Id, Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns true if the specified Element exists in the persistent storage,
     * false otherwise.
     * 
     * @param anElement Element to find in this persistent storage.
     * @return true if Element exists in the persistent storage, false otherwise
     * @throws DaoException
     * @throws IllegalArgumentException 'anId' must not be null.
     */
    public boolean
    contains(Element anElement)
    throws DaoException, IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Returns the total number of records. Provides a high-level reusable means
     * to do a <code>"SELECT * FROM ..."</code>. Noteworthy that every call to
     * this function will trigger a new execution of a statement. Therefore if 
     * the count value will be reused several times in the client code it is a 
     * good idea to cache it in a local variable.
     * 
     * @return the total number of records 
     * @throws DaoException
     */
    public int
    count()
    throws DaoException;

    //------------------------------------------------------------------------
    /**
     * Returns the Element matching the given ID. Retrieves an Element from
     * the persistent storage using as input its ID. 
     * 
     * @param anId ID of the Element to retrieve.
     * @return Element matching the given ID
     * @throws DaoException
     * @throws IllegalArgumentException 'anId' must not be null.
     */
    public Element
    findById(Id anId)
    throws DaoException, IllegalArgumentException;

    //------------------------------------------------------------------------
    /**
     * Returns all Elements from the persistent storage. Client code can 
     * optionally specify an ordering criteria e.g.
     * <pre><code>
     *      IBaseReadOnlyDao&lt;Person&gt; myBaseDao = // ...
     *      
     *      // will retrieve all persons ordered by name ascending 
     *      // and age descending 
     *      List&lt;Person&gt; myPersons = myBaseDao.findAll(
     *          new OrderCriteria("name"), 
     *          new OrderCriteria("age", AscDescEnum.DESC);
     * </code></pre>
     * 
     * @param anOrder Variable order criteria arguments 
     * @return all Elements from the persistent storage. 
     * @throws IllegalArgumentException 'anOrder' must not be null
     * @throws DaoException
     */
    public List<Element>
    findAll(IOrder ... anOrder)
    throws DaoException, IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Returns the list of Elements result of executing the given named Query.
     * The Query parameters are assigned by position according to the position 
     * of in the formal parameter variable Object array.
     * <br/>
     * <br/>
     * In general all finder methods offered are type-safe except this one, yet
     * it is a good trade-off between type-safety and flexibility. It allows 
     * clients to execute arbitrary Queries. PerfectJPattern's discourages the
     * use of SQL in Java code but to instead use externalized named Queries.
     * It is also encouraged to have named Queries in configuration files rather
     * than in annotations.
     * 
     * @param aQueryName The Query name
     * @param anArguments The arguments required to execute the Query
     * @return the list of Elements result of executing the given named Query. 
     * @throws DaoException 
     * @throws IllegalArgumentException 'aQueryName' must not be null
     * @throws IllegalArgumentException 'aQueryName' was not found
     * @throws IllegalArgumentException 'anArguments' must not be null
     * @throws IllegalArgumentException 'anArguments' does not match the 
     *         requirements of 'aQueryName'
     */
    public List<Element>
    findByNamedQuery(String aQueryName, Object ... anArguments)
    throws DaoException, IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Returns the list of Elements result of executing the given named Query.
     * The Query parameters are assigned by name.
     * <br/>
     * <br/>
     * In general all finder methods offered are type-safe except this one, yet
     * it is a good trade-off between type-safety and flexibility. It allows 
     * clients to execute arbitrary Queries. PerfectJPattern's discourages the
     * use of SQL in Java code but to instead use externalized named Queries.
     * It is also encouraged to have named Queries in configuration files rather
     * than in annotations.
     * 
     * @param aQueryName The Query name
     * @param aNamedParameters The arguments required to execute the Query
     * @return the list of Elements result of executing the given named Query. 
     * @throws DaoException 
     * @throws IllegalArgumentException 'aQueryName' must not be null
     * @throws IllegalArgumentException 'aQueryName' was not found
     * @throws IllegalArgumentException 'aNamedParameters' must not be null
     * @throws IllegalArgumentException 'aNamedParameters' does not match the 
     *         requirements of 'aQueryName'
     */
    public List<Element>
    findByNamedQuery(String aQueryName, Map<String, Object> aNamedParameters)
    throws DaoException, IllegalArgumentException; 

    //------------------------------------------------------------------------
    /**
     * Returns the Elements from the persistence storage that correspond to the
     * given page size and page index according to the specific order criteria.
     * <br/>
     * <br/>  
     * This method provides an efficient implementation of the Top N queries 
     * and in general the Page-by-Page iterator Pattern based on pure JPA and 
     * with variable performance depending on the underlying JPA implementation 
     * and Database Server e.g. I would expect better performance when 
     * using Hibernate + Oracle server.
     * <br/>
     * <br/>
     * This Page-By-Page iterator method is stateless. Being stateless is the 
     * main difference from the stateful JEE Value List Handler Pattern 
     * that can theoretically deliver better performance due to the caching 
     * of the large result sets bypassing the multiple database queries.
     * <pre><code>
     *      IBaseReadOnlyDao&lt;Person&gt; myBaseDao = // ...
     *      
     *      //
     *      // will retrieve all persons corresponding to the page 2 for 
     *      // pages of size 50 ordering Person by name ascending
     *      //
     *      int myPageSize = 50;
     *      int myPageIndex = 2;
     *      List&lt;Person&gt; myPage = myBaseDao.findByPage(myPageSize, 
     *          myPageIndex, new OrderCriteria("name"));
     * </code></pre>
     * In Oracle the operation above will translate into something like e.g.
     * <pre><code>
     *      SELECT *
     *      FROM ( 
     *             SELECT a.*, rownum rnum
     *             FROM ( 
     *                    SELECT p 
     *                    FROM Person p 
     *                    ORDER BY :orderCriteria
     *                  ) a
     *             WHERE rownum <= :endPosition 
     *           )
     *      WHERE rnum >= :startPosition; 
     * </code></pre>
     * 
     * @param aPageSize The page size 
     * @param aPageIndex The page index. Page index must be between:
     *        <ul>
     *        <li>If ( count(*) % aPageSize == 0 ) 
     *        then [1, count(*) / aPageSize]</li>
     *        <li>If ( count(*) % aPageSize  > 0 ) 
     *        then [1, count(*) / aPageSize + 1]</li>
     *        </ul>
     * @param anOrder The order criteria
     * @return Elements from the persistence storage that correspond to the
     *         given page size and index from the established order criteria
     * @throws DaoException
     * @throws IllegalArgumentException 'aPageSize' must be greater than zero
     * @throws IllegalArgumentException 'aPageIndex' is out of range
     * @throws IllegalArgumentException 'anOrderCriteria' must not be null
     */
    public List<Element>
    findByPage(int aPageSize, int aPageIndex, IOrder anOrder)
    throws DaoException, IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Returns the {@link ISession} adapted from the implementation-specific
     * 
     * @see IAdapter
     * @return {@link ISession} adapted from the implementation-specific
     */
    public ISession
    getSession();    
    
    //------------------------------------------------------------------------
    /**
     * Returns the {@link ITransaction} adapted from the implementation-specific
     * 
     * @see IAdapter
     * @return {@link ITransaction} adapted from the implementation-specific
     */
    public ITransaction
    getTransaction();
    
    //------------------------------------------------------------------------
    /**
     * Returns the persisted class type {@link Class&lt;Element&gt;}
     * 
     * @return persisted class type {@link Class&lt;Element&gt;}
     */
    public Class<Element>
    getPersistentClass();            
}

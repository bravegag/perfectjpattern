//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// GenericReadOnlyDao.java Copyright (c) 2012 Giovanni Azua Garcia
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

/**
 * <b>Data Access Object (DAO) Pattern</b>: Abstracts from any direct type of 
 * database or persistence mechanism. Provides specific operations without 
 * exposing details of the database. 
 * <br/><br/>
 * <b>Responsibility</b> : Partial Data Access Object abstraction
 * providing Read-Only features: 
 * <br/>
 * <br/>
 * <ul>
 * <li>Find an Element by ID</li> 
 * <li>Find all Elements and optionally specify an {@link OrderCriteria}</li> 
 * <li>Find by Page according to some {@link OrderCriteria} i.e. Paging, 
 * Page-By-Page Iterator</li> 
 * <li>Ask if it contains a given Element</li>
 * <li>Ask for the total number of records</li>
 * <li>Find all Elements in the persistent storage matching a given Example</li>
 * <li>Find by Page according to a given Example and {@link OrderCriteria} i.e. 
 * Paging, Page-By-Page Iterator</li> 
 * </ul>
 * 
 * @param <Id> Identification type
 * @param <Element> Element type
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Data_Access_Object">Data Access 
 * Object wiki definition</a> 
 * @see <a href="http://www.ibm.com/developerworks/java/library/j-genericdao.
 * html">Don't repeat the DAO!</a>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 26, 2007 8:23:22 PM $
 */
public 
interface IGenericReadOnlyDao<Id, Element>
extends IBaseReadOnlyDao<Id, Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the List of Elements that match the search criteria specified 
     * through the Example. Searches all Elements that match the properties set 
     * in the Example Element.
     * 
     * @param anExample Example Element to search for
     * @param anExcludeProperties Array of Java Bean property names to exclude
     * @return List of Elements that match the search criteria specified 
     *         through all properties set in the Example.
     * @throws DaoException
     * @throws IllegalArgumentException 'anElement' must not be null
     * @throws IllegalArgumentException 'anExcludeProperties' must not be null
     */
    public List<Element>
    findByExample(Element anExample, String ... anExcludeProperties)
    throws DaoException, IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Returns the List of Elements that match the search criteria specified 
     * through the Example. Searches all Elements that match the properties set 
     * in the Example Element.
     * 
     * @param anExample Example Element to search for
     * @param anOrder The order criteria
     * @param anExcludeProperties Array of Java Bean property names to exclude
     * @return List of Elements that match the search criteria specified 
     *         through all properties set in the Example.
     * @throws DaoException
     * @throws IllegalArgumentException 'anElement' must not be null
     * @throws IllegalArgumentException 'anOrder' must not be null
     * @throws IllegalArgumentException 'anExcludeProperties' must not be null
     */
    public List<Element>
    findByExample(Element anExample, IOrder anOrder, 
        String ... anExcludeProperties)
    throws DaoException, IllegalArgumentException;    
    
    //------------------------------------------------------------------------
    /**
     * Returns the Elements from the persistence storage that correspond to the
     * given page size and index with the specified order criteria that
     * match the provided example Element.
     * <br/>
     * <br/>  
     * This method provides an efficient implementation of the Top N queries 
     * and in general the Page-by-Page iterator Pattern based on pure JPA and 
     * with variable performance depending on the underlying JPA implementation 
     * and Database Server used e.g. I would expect better performance when 
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
     *      Person myExample = new Person();
     *      myExample.setAge(15);
     *      int myPageSize = 50;
     *      int myPageIndex = 2;
     *      List&lt;Person&gt; myPage = myBaseDao.findByPage(myPageSize, 
     *          myPageIndex, new OrderCriteria("name"), myExample);
     * </code></pre>
     * In Oracle the operation above will translate into something like e.g.
     * <pre><code>
     *      SELECT *
     *      FROM ( 
     *             SELECT a.*, rownum rnum
     *             FROM ( 
     *                    SELECT p FROM Person p 
     *                    WHERE p.age=15 
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
     * @param anExample Example Element to search for
     * @param anOrder The order criteria
     * @param anExcludeProperties Array of Java Bean property names to exclude
     * @return Elements from the persistence storage that correspond to the
     *         given page size and index with the specified order criteria that
     *         match the provided example Element
     * @throws DaoException
     * @throws IllegalArgumentException 'aPageSize' must be greater than zero
     * @throws IllegalArgumentException 'aPageIndex' is out of range
     * @throws IllegalArgumentException 'anOrderCriteria' must not be null
     * @throws IllegalArgumentException 'anElement' must not be null
     * @throws IllegalArgumentException 'anExcludeProperties' must not be null
     */
    public List<Element>
    findByPage(int aPageSize, int aPageIndex, Element anExample, 
        IOrder anOrder, String ... anExcludeProperties)
    throws DaoException, IllegalArgumentException;
}

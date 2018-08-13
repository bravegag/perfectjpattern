//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IValueListIterator.java Copyright (c) 2012 Giovanni Azua Garcia
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

/**
 * <b>Value List Handler Pattern</b>: The client requires a list of items 
 * from the service for presentation. The number of items in the list is 
 * unknown and can be quite large in many instances. Use a Value List 
 * Handler to control the search, cache the results, and provide the 
 * results to the client in a result set whose size and traversal 
 * meets the client's requirements.
 * <br/><br/>
 * 
 * <b>Responsibility</b> Abstract definition of the "Value List Iterator": <br/>
 * <br/>
 * <ul>
 * <li>provides iteration facilities</li>
 * <li>provides Paging</li>
 * </ul>
 * 
 * @param <Element> Model class type. Element types must be 
 *        {@link Serializable}. This is because the main use-case of
 *        this Pattern is implementing remote Stateful Session EJB 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 18, 2009 3:18:21 PM $
 */
public 
interface IValueListIterator<Element extends Serializable>
extends Iterator<Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the Elements that correspond to the given page size and page 
     * index according.
     * <br/>
     * <br/>  
     * This method provides implementation for the the Page-by-Page iterator 
     * Pattern.
     * 
     * @param aPageSize The page size 
     * @param aPageIndex The page index. Page index must be between:
     *        <ul>
     *        <li>If ( count(*) % aPageSize == 0 ) 
     *        then [1, count(*) / aPageSize]</li>
     *        <li>If ( count(*) % aPageSize  > 0 ) 
     *        then [1, count(*) / aPageSize + 1]</li>
     *        </ul>
     * @return the Elements that correspond to the given page size and index 
     * @throws IllegalArgumentException 'aPageSize' must be greater than zero
     * @throws IllegalArgumentException 'aPageIndex' is out of range
     * @throws IllegalStateException Handler has not yet been executed
     */
    public List<Element>
    getPage(int aPageSize, int aPageIndex)
    throws IllegalArgumentException, IllegalStateException;

    //------------------------------------------------------------------------
    /**
     * Returns the total number of Elements retrieved by the query
     * 
     * @return total number of Elements retrieved by the query
     * @throws IllegalStateException Handler has not yet been executed
     */
    public int 
    size()
    throws IllegalStateException;
    
    //------------------------------------------------------------------------
    /**
     * Returns the List of next N elements in the iteration.
     *
     * @return the List of next N elements in the iteration.
     * @throws IllegalArgumentException 'aNumberOfElements' must be greater 
     *         than zero
     * @throws IllegalArgumentException 'aNumberOfElements' plus Current 
     *         Position must be lower than size
     * @throws IllegalStateException Handler has not yet been executed
     */
    public List<Element> 
    next(int aNumberOfElements)
    throws IllegalArgumentException, IllegalStateException, 
        NoSuchElementException;
    
    //------------------------------------------------------------------------
    /**
     * Resets the Iterator 
     * 
     * @throws IllegalStateException
     */
    public void 
    reset()
    throws IllegalStateException;
    
    //------------------------------------------------------------------------
    /**
     * Returns a view of the portion of this {@link IBaseValueListHandler} 
     * between the specified <tt>aFromIndex</tt>, inclusive, and 
     * <tt>aToIndex</tt>, exclusive.  (If <tt>aFromIndex</tt> and 
     * <tt>aToIndex</tt> are equal, the returned list is empty.)  The returned 
     * list is a copy of this list.
     *
     * @param aFromIndex low end point (inclusive) of the subList
     * @param aToIndex high end point (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IllegalArgumentException 'aFromIndex' must be lower than 
     *         'aToIndex'
     * @throws IllegalArgumentException Illegal end point index value
     *         (<tt>aFromIndex &lt; 0 || aToIndex &gt; size ||
     *         aFromIndex &gt; aToIndex</tt>)
     * @throws IllegalStateException Handler has not yet been executed
     */
    public List<Element> 
    subList(int aFromIndex, int aToIndex)
    throws IllegalArgumentException, IllegalStateException;        
    
    //------------------------------------------------------------------------
    /** 
     * Optional method, relevant only when concrete instances of this interface
     * are Stateful Session EJB implementations. If the implementation is an
     * EJB then this method corresponds to the EJB method annotated as 
     * <code>Remove</code> signaling the container to safely dispose this EJB 
     * instance.  
     */
    public void
    dispose();
}

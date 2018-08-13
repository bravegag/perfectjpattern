//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractValueListHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.business.valuelisthandler;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.jee.api.business.valuelisthandler.*;

/**
 * Abstract base reusable implementation of {@link IValueListIterator}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 18, 2009 10:00:36 PM $
 */
public abstract 
class AbstractValueListHandler<Element extends Serializable>
implements IValueListIterator<Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    getPage(int aPageSize, int aPageIndex)
    throws IllegalArgumentException, IllegalStateException
    {
        checkExecuted();

        Validate.isTrue(aPageSize > 0, "'aPageSize' must be greater than zero");
        Validate.isTrue(aPageIndex >= 1, "'aPageIndex' is out of range");
        
        final int mySize = theElements.size();        
        Validate.isTrue(
            (mySize % aPageSize == 0 && aPageIndex <= (mySize / aPageSize)) || 
            (mySize % aPageSize > 0 && aPageIndex  <= (mySize / aPageSize + 1)),
                "'aPageIndex' is out of range");
        
        int myPageIndex  = aPageIndex - 1;
        int myStartIndex = (aPageSize * myPageIndex);            
        int myEndIndex   = Math.min(myStartIndex + aPageSize, mySize); 
        
        List<Element> myElements = new ArrayList<Element>(); 
        myElements.addAll(theElements.subList(myStartIndex, myEndIndex));
        
        return myElements;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public int 
    size() 
    throws IllegalStateException
    {
        checkExecuted();
        
        return theElements.size();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    next(int aNumberOfElements)
    throws IllegalArgumentException, IllegalStateException
    {
        checkExecuted();

        Validate.isTrue(aNumberOfElements > 0, "'aNumberOfElements' must be " +
            "greater than zero");
        
        int myIndex = theIterator.nextIndex();
        Validate.isTrue((myIndex + aNumberOfElements) <= size(), 
            "'aNumberOfElements' plus Current Position must be equal or " +
                "lower than size");
        
        List<Element> myElements = new ArrayList<Element>();
        for (int i = 0; i < aNumberOfElements; i++)
        {
            myElements.add(theIterator.next());
        }
        
        return myElements;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    reset() 
    throws IllegalStateException
    {
        checkExecuted();
        
        theIterator = theElements.listIterator();        
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    subList(int aFromIndex, int aToIndex)
    throws IllegalStateException, IndexOutOfBoundsException
    {
        checkExecuted();

        Validate.isTrue(0 <= aFromIndex && aToIndex < size() &&
            aFromIndex < aToIndex, "Illegal end point index value " +
                "('aFromIndex' < 0 || 'aToIndex' > size || 'aFromIndex' > " +
                    "'aToIndex')");
        
        return theElements.subList(aFromIndex, aToIndex);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    hasNext()
    {
        return theIterator.hasNext();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public Element 
    next()
    {
        checkExecuted();
        
        if (!hasNext())
        {
            throw new NoSuchElementException("There are no more Elements " +
                "available");
        }
               
        Element myNext = theIterator.next();
        
        return myNext;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    remove()
    {
        throw new UnsupportedOperationException("'remove' is not supported");
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Sets the elements
     * 
     * @param anElements the elements to set
     */
    protected final void 
    setElements(List<Element> anElements)
    {
        theElements = new ArrayList<Element>(anElements);
        
        reset();
    }   
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    /**
     * Checks whether the underlying handler has already been executed
     * 
     * @throws IllegalStateException Handler has not yet been executed
     */
    private void
    checkExecuted()
    throws IllegalStateException
    {
        if (theElements == null)
        {
            throw new IllegalStateException("Handler has not yet been " +
                "executed");
        }        
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private List<Element> theElements = null;
    private ListIterator<Element> theIterator = null;
}

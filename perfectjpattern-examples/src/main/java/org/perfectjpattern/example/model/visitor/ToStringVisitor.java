//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ToStringVisitor.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.example.model.visitor;

import java.text.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.behavioral.visitor.*;
import org.perfectjpattern.example.model.*;

/**
 * Visits any data model type and provides generating a String representation 
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 6, 2008 6:25:02 PM $
 */
public 
class ToStringVisitor
extends AbstractVisitor<Object>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    visitCustomer(Customer aCustomer)
    {
        Validate.notNull(aCustomer, "'aCustomer' must not be null");
        
        String myPattern = theBundle.getString(aCustomer.getClass().
            getSimpleName());
        
        StringBuilder myResult = new StringBuilder();
        
        StringBuilder myBuilder = new StringBuilder();
        for (Order myOrder : aCustomer.getOrders())
        {
            myBuilder.append(LINE_BREAK);

            visit(myOrder);
            myBuilder.append(getResult());
        }
        
        myResult.append(LINE_BREAK);
        myResult.append(
            "**************************************************************");
        myResult.append(LINE_BREAK);
        myResult.append(MessageFormat.format(myPattern, aCustomer.getId(), 
            aCustomer.getName()));
        myResult.append(myBuilder.toString());
        myResult.append(LINE_BREAK);
        myResult.append(
            "**************************************************************");
        
        setResult(myResult.toString());
    }
    
    //------------------------------------------------------------------------
    public void
    visitOrder(Order anOrder)
    {
        Validate.notNull(anOrder, "'anOrder' must not be null");

        String myPattern = theBundle.getString(anOrder.getClass().
            getSimpleName());
        
        StringBuilder myBuilder = new StringBuilder();
        for (Product myProduct : anOrder.getProducts())
        {
            myBuilder.append(LINE_BREAK);

            visit(myProduct);
            myBuilder.append(getResult());
        }

        StringBuilder myResult = new StringBuilder();
        myResult.append(MessageFormat.format(myPattern, anOrder.getId(), 
            anOrder.getDate())); 
        myResult.append(myBuilder.toString());
        
        setResult(myResult.toString());
    }

    //------------------------------------------------------------------------
    public void
    visitProduct(Product aProduct)
    {
        Validate.notNull(aProduct, "'aProduct' must not be null");

        String myPattern = theBundle.getString(aProduct.getClass().
            getSimpleName());
        
        String myResult = MessageFormat.format(myPattern, aProduct.getId(), 
            aProduct.getName(), aProduct.getListPrice()); 
        
        setResult(myResult);
    }

    //------------------------------------------------------------------------
    public void
    visitMovie(Movie aMovie)
    {
        Validate.notNull(aMovie, "'aMovie' must not be null");
        
        String myPattern = theBundle.getString(aMovie.getClass().
            getSimpleName());
        
        StringBuilder myResult = new StringBuilder();
        
        myResult.append(LINE_BREAK);
        myResult.append(
            "**************************************************************");
        myResult.append(LINE_BREAK);
        myResult.append(MessageFormat.format(myPattern, aMovie.getId(), 
            aMovie.getTitle(), aMovie.getDirector(), aMovie.getYear()));
        
        setResult(myResult.toString());
    }

    //------------------------------------------------------------------------
    public void
    visitSong(Song aSong)
    {
        Validate.notNull(aSong, "'aSong' must not be null");
        
        String myPattern = theBundle.getString(aSong.getClass().
            getSimpleName());
        
        StringBuilder myResult = new StringBuilder();
        
        myResult.append(LINE_BREAK);
        myResult.append(
            "**************************************************************");
        myResult.append(LINE_BREAK);
        myResult.append(MessageFormat.format(myPattern, aSong.getId(), 
            aSong.getName()));
        
        setResult(myResult.toString());
    }

    //------------------------------------------------------------------------
    /**
     * Returns the result
     *
     * @return the result
     */
    public final String 
    getResult()
    {
        return theResult;
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Sets the result
     *
     * @param anResult the result to set
     */
    protected final void 
    setResult(String anResult)
    {
        theResult = anResult;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final String LINE_BREAK = System.getProperty(
        "line.separator");
    private final ResourceBundle theBundle = ResourceBundle.getBundle(
        getClass().getSimpleName());
    private String theResult;
}

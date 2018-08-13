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
package org.perfectjpattern.jee.integration.dao;

import java.io.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Java Bean representing an Order, that is, a tuple of:
 * <ul>
 * <li>Property name</li> 
 * <li>AscDescEnum i.e. ASC or DESC</li>
 * </ul>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 16, 2009 2:31:03 PM $
 */
public final 
class Order 
implements IOrder, Serializable
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs an {@link Order} from the property name and the 
     * order Ascending or Descending
     *  
     * @param aPropertyName Property name
     * @param anAscDesc AscDesc Enum value
     * @throws IllegalArgumentException 'aPropertyName' must not be null
     * @throws IllegalArgumentException 'anAscDesc' must not be null
     */
    public 
    Order(String aPropertyName, AscDescEnum anAscDesc)
    {
        Validate.notNull(aPropertyName, "'aPropertyName' must not be null");
        Validate.notNull(anAscDesc, "'anAscDesc' must not be null");
        
        thePropertyName = aPropertyName;
        theAscDesc = anAscDesc;
    }        
    
    //------------------------------------------------------------------------
    /**
     * Constructs an {@link Order} from the property name with default 
     * order type
     *  
     * @param anPropertyName Property name
     * @throws IllegalArgumentException 'aPropertyName' must not be null
     */
    public 
    Order(String anPropertyName)
    {
        this(anPropertyName, DEFAULT_ASCDESC);
    }        

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public String 
    getPropertyName()
    {
        return thePropertyName;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public int 
    hashCode()
    {
        final int myPrime = 31;
        int myResult = 1;
        myResult = myPrime * myResult + theAscDesc.toString().hashCode();
        
        myResult = myPrime * myResult + thePropertyName.hashCode();
        
        return myResult;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean 
    equals(Object anObject)
    {
        if (this == anObject)
        {
            return true;
        }
        
        if (anObject == null || getClass() != anObject.getClass())
        {
            return false;
        }
        
        final Order myAnother = (Order) anObject;        
        return getPropertyName().equals(myAnother.getPropertyName()) &&
               getAscDesc() == myAnother.getAscDesc();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public AscDescEnum 
    getAscDesc()
    {
        return theAscDesc;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public String 
    toString()
    {
        StringBuilder myOrderByClause = new StringBuilder();
        
        myOrderByClause.append(JpaBaseDao.MODEL_ALIAS);
        myOrderByClause.append(".");
        myOrderByClause.append(getPropertyName());
        myOrderByClause.append(" ");
        myOrderByClause.append(getAscDesc().name().toLowerCase());
        
        return  myOrderByClause.toString();
    }

    //------------------------------------------------------------------------
    /**
     * Returns a new {@link Order} from the String representation
     * that this Object produced see {@link #toString()}
     * 
     * @param anOrder Input String e.g. "e.name desc"
     * @return new {@link Order} from the String representation
     * @throws IllegalArgumentException
     */
    public static Order
    valueOf(String anOrder)
    throws IllegalArgumentException
    {
        Validate.notNull(anOrder, "'anOrder' must not be null");
        
        try
        {
            String myString = anOrder;
            if (myString.indexOf(JpaBaseDao.MODEL_ALIAS) != -1)
            {
                myString = anOrder.replaceFirst(JpaBaseDao.MODEL_ALIAS + 
                    ".", "").trim();
            }
            
            int myPosition = myString.indexOf(" ");
            
            String myPropertyName = myString.substring(0, myPosition);
            AscDescEnum myAscDesc = AscDescEnum.valueOf(myString.substring(
                myPosition + 1, myString.length()).toUpperCase());
            
            Order myOrder = new Order(myPropertyName, 
                myAscDesc);
    
            return myOrder;
        }
        catch (IndexOutOfBoundsException anException)
        {
            throw new IllegalArgumentException("Illegal Order Criteria '" + 
                anOrder + "'");
        }
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final String thePropertyName;
    private final AscDescEnum theAscDesc;
    
    /**
     * Default {@link AscDescEnum} 
     */
    private static final AscDescEnum DEFAULT_ASCDESC = AscDescEnum.ASC;

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = 1228712099587556270L;
}

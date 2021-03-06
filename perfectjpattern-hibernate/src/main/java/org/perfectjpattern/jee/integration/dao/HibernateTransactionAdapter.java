//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// HibernateTransactionAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.lang.reflect.*;

import org.hibernate.*;
import org.perfectjpattern.core.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Adapts Hibernate's {@link Transaction} to the JPA implementation-free 
 * PerfectJPattern's {@link ITransaction} definition 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 10, 2009 8:26:06 PM $
 */ 
public final 
class HibernateTransactionAdapter
extends Adapter<ITransaction, Transaction>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link HibernateTransactionAdapter} from the Adaptee 
     * {@link Transaction} instance
     * 
     * @param anAdaptee
     * @throws IllegalArgumentException
     */
    public 
    HibernateTransactionAdapter(Transaction anAdaptee)
    throws IllegalArgumentException
    {
        super(ITransaction.class, anAdaptee);
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected Object 
    invokeUnderlying(Method aMethod, Object[] anArguments)
    throws Throwable
    {
        try
        {
            return super.invokeUnderlying(aMethod, anArguments);
        }
        catch (InvocationTargetException anException)
        {
            // make sure that Exceptions are properly chained
            throw new DaoException(anException.getCause());            
        }        
        // CHECKSTYLE:OFF
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            // make sure that Exceptions are properly chained
            throw new DaoException(anException);
        }
    }    
}

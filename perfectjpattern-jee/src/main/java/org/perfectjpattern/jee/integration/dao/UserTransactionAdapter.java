//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// UserTransactionAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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

import javax.transaction.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Adapts JTA's {@link UserTransaction} to the JPA implementation-free 
 * PerfectJPattern's {@link ITransaction}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: May 17, 2009 6:19:36 PM $
 */
public 
class UserTransactionAdapter
extends Adapter<ITransaction, UserTransaction>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs an {@link UserTransactionAdapter} from the Adaptee 
     * {@link UserTransaction} instance
     * 
     * @param anAdaptee The JTA {@link UserTransaction} adaptee
     * @param aStrategy The corresponding {@link ITransactionStrategy}
     * @throws IllegalArgumentException 'anAdaptee' must not be null
     * @throws IllegalArgumentException 'aStrategy' must not be null
     */
    public 
    UserTransactionAdapter(UserTransaction anAdaptee, 
        JtaTransactionStrategy aStrategy) 
    throws IllegalArgumentException
    {
        super(ITransaction.class, anAdaptee);
        
        Validate.notNull(aStrategy, "'aStrategy' must not be null");
        
        theStrategy = aStrategy;
    }  
    
    //------------------------------------------------------------------------
    /**
     * Returns true if the transaction currently Active, false otherwise
     * 
     * @return true if the transaction currently Active, false otherwise
     */
    public boolean
    isActive()
    {        
        UserTransaction myTransaction = getUnderlying();

        try
        {
            boolean myActive = myTransaction.getStatus() == Status.
                STATUS_ACTIVE;

            return myActive;        
        }
        catch (SystemException anException)
        {
            throw new DaoException(anException);
        }        
    }

    //------------------------------------------------------------------------
    /**
     * Rolls back the current Transaction
     * 
     * @throws DaoException
     * @throws UnsupportedOperationException Transactions are not supported by 
     *         the underlying persistent store.
     * @see #isSupported()
     */
    public void
    rollback()
    {
        try
        {
            getUnderlying().rollback();     
            
            theStrategy.reset();
        }
        // CHECKSTYLE:OFF        
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            throw new DaoException(anException);
        }        
    }

    //------------------------------------------------------------------------
    /**
     * Commits the current Transaction
     * 
     * @throws DaoException
     * @throws UnsupportedOperationException Transactions are not supported 
     *         by the underlying persistent store.
     * @see #isSupported()
     */
    public void
    commit()
    {
        try
        {
            getUnderlying().commit();        

            theStrategy.reset();
        }
        // CHECKSTYLE:OFF        
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            throw new DaoException(anException);
        }        
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final JtaTransactionStrategy theStrategy;
}

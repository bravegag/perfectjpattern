//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ITransaction.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.structural.adapter.*;

/**
 * Abstract definition of Transaction available for the DAO implementation.
 * The DAO API exposes this interface but it will be automatically adapted
 * from the underlying JPA-specific implementation (see {@link IAdapter}).
 * <br/><br/> 
 * JPA and specific implementations define similar abstractions e.g.
 * Transaction but despite their similarities, their types actually 
 * mismatch making it difficult to provide a generic unified DAO 
 * solution.
 * <br/><br/> 
 * Note that this is a subset of the functionality that the underlying 
 * implementation offers. This is the subset required to make the DAO 
 * portable. If required, it is possible to get the underlying 
 * implementation using {@link ISession#getDelegate()}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 10, 2009 5:56:17 PM $
 */
public 
interface ITransaction
{
    //------------------------------------------------------------------------
    // public    
    //------------------------------------------------------------------------
    /**
     * Demarcates the beginning of a Transaction
     * 
     * @throws DaoException
     * @throws UnsupportedOperationException Transactions are not supported by 
     *         the underlying persistent store. 
     * @see #isSupported()
     */
    public void
    begin()
    throws DaoException, UnsupportedOperationException;    

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
    throws DaoException, UnsupportedOperationException;    
    
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
    throws DaoException, UnsupportedOperationException;  
    
    //------------------------------------------------------------------------
    /**
     * Returns true if the transaction currently Active, false otherwise
     * 
     * @return true if the transaction currently Active, false otherwise
     */
    public boolean
    isActive();
}

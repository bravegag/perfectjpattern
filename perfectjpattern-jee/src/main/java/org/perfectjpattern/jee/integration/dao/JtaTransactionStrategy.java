//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// JtaTransactionStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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
import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.jee.api.business.servicelocator.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.business.servicelocator.*;

/**
 * Concrete implementation of {@link ITransactionStrategy} targeting the
 * use of {@link UserTransaction}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 19, 2009 1:51:31 PM $
 */
public final
class JtaTransactionStrategy
implements ITransactionStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link JtaTransactionStrategy} using the default 
     * UserTransaction service name "java:comp/UserTransaction"
     */
    public 
    JtaTransactionStrategy()
    {
        this("java:comp/UserTransaction");        
    }
    
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link JtaTransactionStrategy} from the UserTransaction
     * service name provided. It can be server-specific.
     * 
     * @param aServiceName The Service name
     * @throws IllegalArgumentException 'aServiceName' must not be null
     */
    public 
    JtaTransactionStrategy(String aServiceName)
    throws IllegalArgumentException
    {
        Validate.notNull(aServiceName, "'aServiceName' must not be null");
        
        theServiceName = aServiceName; 
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public ITransaction 
    getTransaction()
    {
        if (theTransactionAdapter == null)
        {
            IServiceLocator myServiceLocator = ServiceLocator.getInstance();
            UserTransaction myUserTransaction = myServiceLocator.locate(
                theServiceName);
            
            // allow the adapter to reset instance after rollback and commit
            theTransactionAdapter = new UserTransactionAdapter(
                myUserTransaction, this);
        }
        
        return theTransactionAdapter.getTarget();        
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    isManaged()
    {
        return false;
    }    
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    protected void
    reset()
    {
        theTransactionAdapter = null;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final String theServiceName;
    private IAdapter<ITransaction, UserTransaction> theTransactionAdapter;
} 

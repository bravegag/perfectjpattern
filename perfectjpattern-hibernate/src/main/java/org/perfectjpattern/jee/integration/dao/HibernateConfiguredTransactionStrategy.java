//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// DefaultJSEDaoTransactionStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.apache.commons.lang3.*;
import org.hibernate.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Concrete simple implementation of {@link ITransactionStrategy} corresponding 
 * to {@link Session#getTransaction()}
 * <br/>
 * <br/>
 * This strategy is mandated by the Hibernate configuration value specified in 
 * property <code>hibernate.transaction_factory</code>. If not specified, the 
 * default value is {@link JDBCTransactionFactory}.
 * 
 * Concrete simple implementation of {@link ITransactionStrategy} targeting 
 * the JSE environment
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 8, 2009 5:30:47 PM $
 */
public final
class HibernateConfiguredTransactionStrategy
implements ITransactionStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    HibernateConfiguredTransactionStrategy(ISessionStrategy aSessionStrategy)
    {
        Validate.notNull(aSessionStrategy, 
            "'aSessionStrategy' must not be null");
        
        theSessionStrategy = aSessionStrategy;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public ITransaction 
    getTransaction()
    {
        return theSessionStrategy.getSession().getTransaction();
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
    // members
    //------------------------------------------------------------------------
    private final ISessionStrategy theSessionStrategy;
}

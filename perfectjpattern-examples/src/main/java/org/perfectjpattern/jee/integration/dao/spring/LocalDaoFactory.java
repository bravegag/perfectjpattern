//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// LocalDaoFactory.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.integration.dao.spring;

import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.integration.dao.*;

/**
 * Extends {@link HibernateDaoFactory} exposing project-local specific 
 * implementations injected via IoC. This implementation was introduced
 * to support the Spring Generic DAO example implementation.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 8, 2008 3:35:51 AM $
 */
public final 
class LocalDaoFactory
extends HibernateDaoFactory
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of {@link LocalDaoFactory}
     * 
     * @return Singleton instance of {@link LocalDaoFactory}
     */
    public static LocalDaoFactory
    getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new LocalDaoFactory();            
        }
        
        return INSTANCE;
    }    
    
    //------------------------------------------------------------------------
    /**
     * Returns the {@link ICustomerDao}
     *
     * @return the {@link ICustomerDao}
     */
    public final ICustomerDao 
    createCustomerDao()
    {
        return theCustomerDao;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the {@link ICustomerDao}
     *
     * @param aCustomerDao the {@link ICustomerDao} to set
     */
    public final void 
    setCustomerDao(ICustomerDao aCustomerDao)
    {
        theCustomerDao = aCustomerDao;
    }
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    /**
     * Overrides the default {@link ISessionStrategy} and 
     * {@link ITransactionStrategy} to be the Null Object implementations. Not
     * doing so will default {@link HibernateDaoFactory} to JSE mode 
     * implementations. In this case, the implementations are set using 
     * Spring's dependency injection.
     */
    private 
    LocalDaoFactory()
    {
        super(new NullSessionStrategy(), new NullTransactionStrategy());
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Singleton instance
     */
    private static LocalDaoFactory INSTANCE = null;
    private ICustomerDao theCustomerDao;
}
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
package org.perfectjpattern.jee.integration.dao;

import org.perfectjpattern.core.api.creational.singleton.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.slf4j.*;
import org.springframework.transaction.annotation.*;

/**
 * Extends {@link HibernateDaoFactory} exposing project-local specific DAO
 * implementations injected via IoC
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 8, 2008 3:35:51 AM $
 */
@Transactional
public final 
class LocalDaoFactory
extends HibernateDaoFactory
implements ISingleton
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
     * Returns the personDao
     *
     * @return the personDao
     */
    public final IPersonDao 
    createPersonDao()
    {
        return thePersonDao;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the personDao
     *
     * @param aPersonDao the personDao to set
     */
    public final void 
    setPersonDao(IPersonDao aPersonDao)
    {
        thePersonDao = aPersonDao;
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

        theLogger.debug("constructor completed successfully.");
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging facilities for this class instance 
     */
    private static Logger theLogger = LoggerFactory.getLogger(LocalDaoFactory.class);
    private static LocalDaoFactory INSTANCE = null;
    private IPersonDao thePersonDao;
}

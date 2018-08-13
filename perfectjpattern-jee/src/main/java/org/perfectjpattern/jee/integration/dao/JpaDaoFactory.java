//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// HibernateDaoFactory.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.creational.singleton.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Concrete implementation for {@link IBaseDaoFactory}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 5, 2008 5:11:45 PM $
 */
public
class JpaDaoFactory
extends AbstractDaoFactory
implements ISingleton
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of {@link JpaDaoFactory}
     * 
     * @return Singleton instance of {@link JpaDaoFactory}
     */
    public static JpaDaoFactory
    getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new JpaDaoFactory();            
        }
        
        return INSTANCE;
    }    
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs {@link JpaDaoFactory} to consume Session instances 
     * from the given {@link ISessionStrategy} and Transaction instances from 
     * the {@link ITransactionStrategy}
     * 
     * @param aSessionStrategy {@link ISessionStrategy} to consume 
     *        Session instances from
     * @param aTransactionStrategy {@link ITransactionStrategy} to 
     *        consume Transaction instances from
     * @throws IllegalArgumentException 'aSessionStrategy' must not be null
     * @throws IllegalArgumentException 'aTransactionStrategy' must not be null
     */
    protected 
    JpaDaoFactory(ISessionStrategy aSessionStrategy, 
        ITransactionStrategy aTransactionStrategy)
    throws IllegalArgumentException
    {
        setSessionStrategy(aSessionStrategy);
        setTransactionStrategy(aTransactionStrategy);
    }           

    //------------------------------------------------------------------------
    /**
     * Defaults to JSE mode
     */
    protected
    JpaDaoFactory()
    {
        ISessionStrategy mySessionStrategy = new JpaSessionStrategy();
        ITransactionStrategy myTransactionStrategy = 
            new JpaTransactionStrategy(mySessionStrategy);

        setSessionStrategy(mySessionStrategy);
        setTransactionStrategy(myTransactionStrategy);
    }           
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected <Id extends Serializable, Element> IBaseDao<Id, Element>
    createDao(Class<Element> aPersistentClass, 
        ISessionStrategy aSessionStrategy, 
            ITransactionStrategy aTransactionStrategy)
    {
        assert aPersistentClass != null : "'aPersistentClass' must not be null";
        assert aSessionStrategy != null : "'aSessionStrategy' must not be null";
        assert aTransactionStrategy != null : 
            "'aTransactionStrategy' must not be null";
        
        return new JpaBaseDao<Id, Element>(aPersistentClass, 
            aSessionStrategy, aTransactionStrategy);
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static JpaDaoFactory INSTANCE;
}

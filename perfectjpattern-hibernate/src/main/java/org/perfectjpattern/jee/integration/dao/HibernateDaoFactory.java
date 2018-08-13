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
import org.slf4j.*;

/**
 * Concrete implementation for {@link IGenericDaoFactory}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 5, 2008 5:11:45 PM $
 */
public
class HibernateDaoFactory
extends AbstractDaoFactory
implements IGenericDaoFactory, ISingleton
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of {@link HibernateDaoFactory}
     * 
     * @return Singleton instance of {@link HibernateDaoFactory}
     */
    public static HibernateDaoFactory
    getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new HibernateDaoFactory();
        }
        
        return INSTANCE;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public <Id extends Serializable, Element> IGenericReadOnlyDao<Id, Element>
    createReadOnlyDao(Class<Element> aPersistentClass)
    throws IllegalArgumentException
    {
        IGenericReadOnlyDao<Id, Element> myGenericReadOnlyDao = createDao(
            aPersistentClass);
                
        assert myGenericReadOnlyDao != null : 
            "myGenericReadOnlyDao must not be null";
        
        return myGenericReadOnlyDao;
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <Id extends Serializable, Element> IGenericDao<Id, Element>
    createDao(Class<Element> aPersistentClass)
    throws IllegalArgumentException
    {
        IGenericDao<Id, Element> myGenericDao = (IGenericDao<Id, Element>) 
            super.createDao(aPersistentClass);
                
        assert myGenericDao != null : "myGenericDao must not be null";

        return myGenericDao;
    }    
        
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs {@link HibernateDaoFactory} to consume Session instances 
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
    HibernateDaoFactory(ISessionStrategy aSessionStrategy, 
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
    HibernateDaoFactory()
    {
        theLogger.debug("entered constructor ...");
        
        ISessionStrategy mySessionStrategy = 
            new HibernateCurrentSessionStrategy();
        ITransactionStrategy myTransactionStrategy = 
            new HibernateConfiguredTransactionStrategy(mySessionStrategy);
        
        setSessionStrategy(mySessionStrategy);
        setTransactionStrategy(myTransactionStrategy);
    }           
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    @Override
    protected <Id extends Serializable, Element> IGenericDao<Id, Element>
    createDao(Class<Element> aPersistentClass, 
        ISessionStrategy aSessionStrategy, 
            ITransactionStrategy aTransactionStrategy)
    {
        assert aPersistentClass != null : "'aPersistentClass' must not be null";
        assert aSessionStrategy != null : "'aSessionStrategy' must not be null";
        assert aTransactionStrategy != null : 
            "'aTransactionStrategy' must not be null";
        
        return new HibernateGenericDao<Id, Element>(aPersistentClass, 
            aSessionStrategy, aTransactionStrategy);
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging facilities for this class instance 
     */
    private static Logger theLogger = LoggerFactory.getLogger(HibernateDaoFactory.class);
    private static HibernateDaoFactory INSTANCE = null;
}

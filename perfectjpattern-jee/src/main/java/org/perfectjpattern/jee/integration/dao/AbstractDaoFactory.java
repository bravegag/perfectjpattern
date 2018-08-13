//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractDaoFactory.java Copyright (c) 2012 Giovanni Azua Garcia
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
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.slf4j.*;

/**
 * Abstract reusable base partial implementation for {@link IBaseDaoFactory} 
 * concrete implementations
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 19, 2009 12:16:49 PM $
 */
public abstract
class AbstractDaoFactory
implements IBaseDaoFactory
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    setSessionStrategy(ISessionStrategy aSessionStrategy)
    throws IllegalArgumentException
    {
        Validate.notNull(aSessionStrategy, 
            "'aDaoSessionStrategy' must not be null");
        
        // shutdown existing
        if (theSessionStrategy != null)
        {
            shutdown();
        }
        
        theSessionStrategy = aSessionStrategy;
        
        // clear cache
        theBaseDaoMap.clear();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    setTransactionStrategy(ITransactionStrategy aTransactionStrategy)
    throws IllegalArgumentException
    {
        Validate.notNull(aTransactionStrategy, 
            "'aTransactionFactory' must not be null");
        
        theTransactionStrategy = aTransactionStrategy;        

        // clear cache
        theBaseDaoMap.clear();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    shutdown() 
    throws DaoException
    {
        assert theSessionStrategy != null : 
            "'theSessionStrategy' must not be null";
        
        theSessionStrategy.shutdown();
        
        theSessionStrategy = null;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public <Id extends Serializable, Element> IBaseReadOnlyDao<Id, Element>
    createReadOnlyDao(Class<Element> aPersistentClass)
    throws IllegalArgumentException
    {
        Validate.notNull(aPersistentClass, 
            "'aPersistentClass' must not be null");
        Validate.isTrue(!aPersistentClass.isInterface(), 
            "'aPersistentClass' must be a class type");
        
        IBaseReadOnlyDao<Id, Element> myBaseReadOnlyDao = createDao(
            aPersistentClass);
                
        assert myBaseReadOnlyDao != null : 
            "myBaseReadOnlyDao must not be null";
        
        return myBaseReadOnlyDao;
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <Id extends Serializable, Element> IBaseDao<Id, Element>
    createDao(Class<Element> aPersistentClass)
    throws IllegalArgumentException
    {
        Validate.notNull(aPersistentClass, 
            "'aPersistentClass' must not be null");
        Validate.isTrue(!aPersistentClass.getClass().isInterface(), 
            "'aPersistentClass' must be a class type");
        
        IBaseDao<Id, Element> myBaseDao = null;
        
        if (theBaseDaoMap.containsKey(aPersistentClass))
        {
            // just reuse the existing instance
            myBaseDao = (IBaseDao<Id, Element>) theBaseDaoMap.get(
                aPersistentClass);            
        }        
        else
        {
            // create new instance and make it available for future calls
            myBaseDao = createDao(aPersistentClass, 
                theSessionStrategy, theTransactionStrategy);
            theBaseDaoMap.put(aPersistentClass, myBaseDao);
        }
                
        assert myBaseDao != null : "myBaseDao must not be null";

        return myBaseDao;
    }        
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    protected 
    AbstractDaoFactory()
    {
        theLogger.debug("entered constructor ...");

        theBaseDaoMap = new HashMap<Class<?>, IBaseDao<?, ?>>();
    }           
    
    //------------------------------------------------------------------------
    protected abstract <Id extends Serializable, Element> IBaseDao<Id, Element>
    createDao(Class<Element> aPersistentClass, 
        ISessionStrategy aSessionStrategy, 
            ITransactionStrategy aTransactionStrategy);
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging facilities for this class instance 
     */
    private static Logger theLogger = LoggerFactory.getLogger(AbstractDaoFactory.class);
    private ISessionStrategy theSessionStrategy;
    private ITransactionStrategy theTransactionStrategy;    
    private final Map<Class<?>, IBaseDao<?, ?>> theBaseDaoMap;    
}

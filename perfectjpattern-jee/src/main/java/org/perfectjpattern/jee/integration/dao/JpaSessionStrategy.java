//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// JpaSessionStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Concrete simple implementation of {@link ISessionStrategy} targeting 
 * JPA on JSE environment
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 18, 2009 1:27:19 PM $
 */
public 
class JpaSessionStrategy
implements ISessionStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Creates {@link JpaSessionStrategy} from default Persistence Unit
     */
    public 
    JpaSessionStrategy()
    {
        this(DEFAULT_PERSISTENCE_UNIT_NAME, new Properties());
    }
    
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link JpaSessionStrategy} from Persistence Unit name and  
     * properties
     * 
     * @param aName Persistence unit name
     * @param aProperties Properties
     */
    public 
    JpaSessionStrategy(String aName, Properties aProperties)
    {
        try
        {
            theEntityManagerFactory = Persistence.createEntityManagerFactory(
                aName, aProperties);
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
     * Constructs a {@link JpaSessionStrategy} from an existing 
     * {@link EntityManager} instance
     * 
     * @param anEntityManagerFactory {@link EntityManager} instance
     */
    public 
    JpaSessionStrategy(EntityManagerFactory anEntityManagerFactory)
    {
        Validate.notNull(anEntityManagerFactory, 
            "'anEntityManagerFactory' must not be null");
        
        theEntityManagerFactory = anEntityManagerFactory;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public ISession 
    getSession()
    {
        try
        {                    
            if (theSessionAdapter == null)
            {
                EntityManager myEntityManager = theEntityManagerFactory.
                    createEntityManager();
                
                theSessionAdapter = new EntityManagerAdapter(myEntityManager);
            }
        
            assert theSessionAdapter != null : 
                "'theSessionAdapter' must not be null";
            
            return theSessionAdapter.getTarget();
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException)
        // CHECKSTYLE:ON
        {
            throw new DaoException(anException);            
        }
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    shutdown()
    {
        theEntityManagerFactory.close();
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Returns the entityManagerFactory
     *
     * @return the entityManagerFactory
     */
    protected final EntityManagerFactory 
    getEntityManagerFactory()
    {
        return theEntityManagerFactory;
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final String DEFAULT_PERSISTENCE_UNIT_NAME = "default";
    private final EntityManagerFactory theEntityManagerFactory;
    private IAdapter<ISession, EntityManager> theSessionAdapter;
}

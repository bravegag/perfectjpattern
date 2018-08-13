//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// EclipseLinkSessionStrategy.java Copyright (c) 2012 
// Giovanni Azua Garcia bravegag@hotmail.com
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

import javax.persistence.*;

import org.apache.commons.lang3.*;
import org.eclipse.persistence.internal.jpa.*;
import org.eclipse.persistence.sessions.*;
import org.eclipse.persistence.sessions.factories.*;
import org.eclipse.persistence.sessions.server.*;
import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Concrete implementation of {@link ISessionStrategy}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 8, 2009 5:24:51 PM $
 */
public final
class EclipseLinkSessionStrategy
extends JpaSessionStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs {@link EclipseLinkSessionStrategy} from default 
     * {@link ServerSession} defined in <code>sessions.xml</code>
     */
    public 
    EclipseLinkSessionStrategy()
    {
        this(SessionManager.getManager());                     
    }
    
    //------------------------------------------------------------------------
    /**
     * Constructs {@link EclipseLinkSessionStrategy} from existing 
     * {@link SessionFactory}
     * 
     * @param aSessionManager {@link SessionManager} instance
     * @throws IllegalArgumentException 'aSessionManager' must not be null 
     * @throws IllegalArgumentException 'aSessionManager' must provide Session 
     *                                  instances of type ServerSession
     */
    public 
    EclipseLinkSessionStrategy(SessionManager aSessionManager)
    throws IllegalArgumentException
    {
        super(toEntityManagerFactory(aSessionManager));
    }

    //------------------------------------------------------------------------
    /**
     * Constructs {@link EclipseLinkSessionStrategy} from the Session name
     * 
     * @param aSessionName Name of the Session in <code>sessions.xml</code>
     * @throws IllegalArgumentException 'aSessionName' must not be null 
     */
    public 
    EclipseLinkSessionStrategy(String aSessionName)
    throws IllegalArgumentException
    {
        super(toEntityManagerFactory(aSessionName));
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public ISession 
    getSession()
    {
        try
        {                    
            if (theSessionAdapter == null)
            {
                EntityManager myEntityManager = getEntityManagerFactory().
                    createEntityManager();
                
                theSessionAdapter = new ExtendedEntityManagerAdapter(
                    myEntityManager);
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
    // private
    //------------------------------------------------------------------------
    /**
     * Returns {@link EntityManagerFactory} out of a TopLink 
     * {@link SessionManager} instance
     * 
     * @param aSessionManager {@link SessionManager} instance
     * @return {@link EntityManagerFactory} out of a TopLink 
     *         {@link SessionManager} instance
     * @throws IllegalArgumentException 'aSessionManager' must not be null 
     * @throws IllegalArgumentException 'aSessionManager' must provide Session 
     *                                  instances of type ServerSession
     */
    private static EntityManagerFactory 
    toEntityManagerFactory(SessionManager aSessionManager)
    {
        Validate.notNull(aSessionManager, "'aSessionManager' must not be null");
        
        Session myDefaultSession = aSessionManager.getDefaultSession(); 
        Validate.isTrue(myDefaultSession.getClass().isAssignableFrom(
            ServerSession.class), "'aSessionManager' must provide Session " +
                "instances of type ServerSession");
        
        EntityManagerFactory myFactory = new EntityManagerFactoryImpl(
            (ServerSession) myDefaultSession);
        
        return myFactory;
    }        

    //------------------------------------------------------------------------
    /**
     * Returns {@link EntityManagerFactory} out of a TopLink 
     * Session name
     * 
     * @param aSessionName Name of the Session in <code>sessions.xml</code>
     * @return {@link EntityManagerFactory} out of a TopLink 
     *         {@link SessionManager} instance
     * @throws IllegalArgumentException 'aSessionName' must not be null 
     * @throws IllegalArgumentException 'aSessionName' must correspond to 
     *                                  Session instances of type ServerSession
     */
    private static EntityManagerFactory 
    toEntityManagerFactory(String aSessionName)
    {
        Validate.notNull(aSessionName, "'aSessionName' must not be null");
        
        Session mySession = SessionManager.getManager().getSession(
            aSessionName);

        Validate.isTrue(mySession.getClass().isAssignableFrom(
            ServerSession.class), "'aSessionName' must provide Session " +
                "instances of type ServerSession");
        
        EntityManagerFactory myFactory = new EntityManagerFactoryImpl(
            (ServerSession) mySession);
        
        return myFactory;
    }        
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private IAdapter<ISession, EntityManager> theSessionAdapter;    
}

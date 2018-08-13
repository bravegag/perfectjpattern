//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// HibernateCurrentSessionStrategy.java Copyright (c) 2012 
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

import org.apache.commons.lang3.*;
import org.hibernate.*;
import org.hibernate.boot.registry.*;
import org.hibernate.cfg.*;
import org.hibernate.service.*;
import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Concrete simple implementation of {@link ISessionStrategy} corresponding to
 * {@link SessionFactory#getCurrentSession()}
 * <br/>
 * <br/>
 * This strategy is mandated by the Hibernate configuration value specified in 
 * property <code>current_session_context_class</code> e.g. <code>thread</code> 
 * value will default to using {@link org.hibernate.context.
 * ThreadLocalSessionContext} which is the default and preferred method in 
 * Java SE environments.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 8, 2009 5:24:51 PM $
 */
public final
class HibernateCurrentSessionStrategy
implements ISessionStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    HibernateCurrentSessionStrategy()
    {
        try
        {            
            Configuration myConfiguration = new Configuration();
            myConfiguration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                applySettings(myConfiguration.getProperties()).build();            
            theSessionFactory = myConfiguration.buildSessionFactory(serviceRegistry);
        }
        // CHECKSTYLE:OFF
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            throw new DaoException(anException);
        }        
    }
    
    //------------------------------------------------------------------------
    public 
    HibernateCurrentSessionStrategy(SessionFactory aSessionFactory)
    {
        Validate.notNull(aSessionFactory, "'aSessionFactory' must not be null");
        
        theSessionFactory = aSessionFactory;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    shutdown()
    {        
        theSessionFactory.close();
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
            Session mySession = theSessionFactory.getCurrentSession();
            
            if (theSessionAdapter == null || theSessionAdapter.getAdaptee() 
                != mySession)
            {
                theSessionAdapter = new HibernateSessionAdapter(mySession);
            }
            
            return theSessionAdapter.getTarget();
        }
        catch (HibernateException anException)
        {
            throw new DaoException(anException);
        }
    }
        
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final SessionFactory theSessionFactory;
    private IAdapter<ISession, Session> theSessionAdapter;
}

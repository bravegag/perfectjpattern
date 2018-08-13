//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ExtendedEntityManagerAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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

import javax.persistence.*;

import org.apache.commons.lang3.*;
import org.eclipse.persistence.jpa.*;
import org.eclipse.persistence.queries.*;
import org.eclipse.persistence.sessions.*;
import org.eclipse.persistence.sessions.server.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Adapts EclipseLink's {@link ServerSession} to PerfectJPattern's 
 * {@link ISession}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jul 18, 2009 1:15:08 PM $
 */
public 
class ExtendedEntityManagerAdapter
extends EntityManagerAdapter
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @Override
    public IQuery
    createNamedQuery(String aQueryName, Class<?> aPersistentClass)
    {
        Validate.notNull(aPersistentClass, 
            "'aPersistentClass' must not be null");
        
        Session mySession = JpaHelper.getEntityManager((EntityManager) 
            getUnderlying().getDelegate()).getServerSession();
     
        // first try to retrieve the Query from the Session 
        DatabaseQuery myDatabaseQuery = mySession.getQuery(aQueryName);
        if (myDatabaseQuery == null)
        {
            // otherwise try from the Descriptor
            myDatabaseQuery = mySession.getDescriptor(aPersistentClass).
                getQueryManager().getQuery(aQueryName);
        }
        
        Validate.notNull(myDatabaseQuery, "'aQueryName' does not exist " +
            "neither on Session level nor on the Descriptor");
        
        IQuery myQuery = null;
        if (ReadQuery.class.isAssignableFrom(myDatabaseQuery.getClass()))
        {
            // adapt ReadQuery            
            myQuery = new ReadQueryAdapter((ReadQuery) myDatabaseQuery, 
                mySession, aPersistentClass).getTarget();
        }
        
        return myQuery;
    }        

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link EntityManagerAdapter} from the Adaptee 
     * {@link EntityManager} instance
     * 
     * @param anAdaptee The Adaptee {@link EntityManager}
     * @throws IllegalArgumentException 'anAdaptee' must not be null
     */
    protected 
    ExtendedEntityManagerAdapter(EntityManager anAdaptee)
    throws IllegalArgumentException
    {
        super(anAdaptee);
    }
}

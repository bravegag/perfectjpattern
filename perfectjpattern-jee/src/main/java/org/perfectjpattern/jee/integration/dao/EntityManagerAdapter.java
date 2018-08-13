//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// EntityManagerAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.lang.reflect.*;

import javax.persistence.*;

import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.core.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Adapts JPA's {@link EntityManager} to PerfectJPattern's {@link ISession} 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 10, 2009 8:26:06 PM $
 */ 
public 
class EntityManagerAdapter
extends Adapter<ISession, EntityManager>
{
    //------------------------------------------------------------------------
    // public	
    //------------------------------------------------------------------------	
    /**
     * Updates the Object in the persistent store
     * 
     * @param anObject The Object to update
     */    public void
    update(Object anObject)
    {
        getUnderlying().merge(anObject);
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns instance of JPA implementation-specific Query for executing
     * JPA-specific Queries e.g. HQL queries. 
     * 
     * @param aSqlString SQL query input
     * @return instance of JPA implementation-specific Query
     */
    public IQuery
    createQuery(String aSqlString)
    {
        // adapts the Query
        IQuery myQuery = new QueryAdapter(getUnderlying().createQuery(
            aSqlString)).getTarget();
        
        return myQuery;
    }        
    
    //------------------------------------------------------------------------
    /**
     * Returns instance of JPA implementation-specific Query that matches the
     * given Query name. 
     * 
     * @param aQueryName Query name
     * @param aPersistentClass Persistent class type
     * @return instance of JPA implementation-specific Query
     */
    public IQuery
    createNamedQuery(String aQueryName, Class<?> aPersistentClass)
    {
        // adapts the Query
        IQuery myQuery = new QueryAdapter(getUnderlying().createNamedQuery(
            aQueryName)).getTarget();
        
        return myQuery;
    }        

    //------------------------------------------------------------------------
    /**
     * Returns instance of JPA implementation-specific Query for executing
     * native SQL queries. 
     * 
     * @param aSqlString SQL query input
     * @return instance of JPA implementation-specific Query
     */
    public IQuery
    createNativeQuery(String aSqlString, Class<?> aPersistentClass)
    {
        // adapts the Query
        IQuery myQuery = new QueryAdapter(getUnderlying().
            createNativeQuery(aSqlString, aPersistentClass)).getTarget();
        
        return myQuery;
    }        
    
    //------------------------------------------------------------------------
    public ITransaction
    getTransaction()
    {
        // adapts the EntityTransaction
        EntityTransaction myAdaptee = getUnderlying().getTransaction();

        if (theTransactionAdapter == null || theTransactionAdapter.
            getAdaptee() != myAdaptee)
        {
            theTransactionAdapter = new EntityTransactionAdapter(myAdaptee);
        }
        
        assert theTransactionAdapter != null : 
            "Transaction Adapter must not be null";
        
        return theTransactionAdapter.getTarget();
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
    EntityManagerAdapter(EntityManager anAdaptee)
    throws IllegalArgumentException
    {
        super(ISession.class, anAdaptee);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected Object 
    invokeUnderlying(Method aMethod, Object[] anArguments)
    throws Throwable
    {
        try
        {
            return super.invokeUnderlying(aMethod, anArguments);
        }
        catch (InvocationTargetException anException)
        {
            anException.printStackTrace();
            // make sure that Exceptions are properly chained
            throw new DaoException(anException.getCause());            
        }
        // CHECKSTYLE:OFF
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            // make sure that Exceptions are properly chained
            throw new DaoException(anException);
        }
    }
        
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private IAdapter<ITransaction, EntityTransaction> theTransactionAdapter;
}

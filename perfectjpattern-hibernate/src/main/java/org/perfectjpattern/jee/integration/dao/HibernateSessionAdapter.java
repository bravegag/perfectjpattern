//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// HibernateSessionAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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
import java.lang.reflect.*;

import org.hibernate.*;
import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.core.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Adapts Hibernate's {@link Session} to the JPA implementation-free 
 * PerfectJPattern's {@link ISession} definition 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 10, 2009 8:26:06 PM $
 */ 
public final 
class HibernateSessionAdapter
extends Adapter<ISession, Session>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link HibernateSessionAdapter} from the Adaptee 
     * {@link Session} instance
     * 
     * @param anAdaptee Adaptee Hibernate Transaction
     * @throws IllegalArgumentException
     */
    public 
    HibernateSessionAdapter(Session anAdaptee)
    throws IllegalArgumentException
    {
        super(ISession.class, anAdaptee);
    }
    
    //------------------------------------------------------------------------
    public ITransaction
    getTransaction()
    {
        Transaction myAdaptee = getUnderlying().getTransaction();

        if (theTransactionAdapter == null || theTransactionAdapter.
            getAdaptee() != myAdaptee)
        {
            theTransactionAdapter = new HibernateTransactionAdapter(myAdaptee);
        }
        
        return theTransactionAdapter.getTarget();
    }
    
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public <E> E
    find(Class<E> aPersistentClass, Object anId)
    {
        assert anId instanceof Serializable : "'anId' must be Serializable";
        
        E myElement = (E) getUnderlying().load(aPersistentClass, 
            (Serializable) anId);
        
        return myElement;
    }

    //------------------------------------------------------------------------
    public Session
    getDelegate()
    {
        return getUnderlying();
    }
    
    //------------------------------------------------------------------------
    public IQuery
    createQuery(String aSqlString)
    {
        IQuery myQuery = new HibernateQueryAdapter(getUnderlying().createQuery(
            aSqlString)).getTarget();
        
        return myQuery;
    }
    
    //------------------------------------------------------------------------
    public IQuery
    createNativeQuery(String aSqlString, Class<?> aPersistentClass)
    {
        IQuery myQuery = new HibernateQueryAdapter(getUnderlying().
            createSQLQuery(aSqlString)).getTarget();
        
        return myQuery;
    }        
    
    //------------------------------------------------------------------------
    public IQuery
    createNamedQuery(String aQueryName, Class<?> aPersistentClass)
    {
        // adapts the Query
        IQuery myQuery = new HibernateQueryAdapter(getUnderlying().
            getNamedQuery(aQueryName)).getTarget();
        
        return myQuery;
    }            

    //------------------------------------------------------------------------  
    public void
    remove(Object anObject)
    {
        getUnderlying().delete(anObject);
    }
    
    //------------------------------------------------------------------------  
    public void
    update(Object anObject)
    {
        getUnderlying().saveOrUpdate(anObject);
    }

    //------------------------------------------------------------------------  
    @SuppressWarnings("unchecked")
    public <Id> Id
    persist(Object anObject)
    {
        return (Id) getUnderlying().save(anObject);
    }
    
    //------------------------------------------------------------------------
    public void
    joinTransaction()
    {
        // there is no facility for this
    }
    
    //------------------------------------------------------------------------
    // protected
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
        // CHECKSTYLE:OFF
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            // make sure that Exceptions are properly chained
            throw new DaoException(anException);
        }
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private IAdapter<ITransaction, Transaction> theTransactionAdapter;
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ISession.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.integration.dao;

import org.perfectjpattern.core.api.structural.adapter.*;

/**
 * Abstract definition of Session available for the DAO implementation.
 * The DAO API exposes this interface but it will be automatically adapted
 * from the underlying JPA-specific implementation (see {@link IAdapter}).
 * <br/><br/> 
 * JPA and specific implementations define similar abstractions e.g.
 * Session vs EntityManager but despite their similarities, their types 
 * actually mismatch making it difficult to provide a generic unified DAO 
 * solution.
 * <br/><br/>
 * Note that this is a subset of the functionality that the underlying 
 * implementation offers. This is the subset required to make the DAO 
 * portable. If required, it is possible to get the underlying 
 * implementation using {@link ISession#getDelegate()}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 10, 2009 5:47:08 PM $
 */
public 
interface ISession
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Clears the {@link Session}, removing all cached changes without 
     * applying them. The underlying implementation is JPA implementation- 
     * specific.
     * 
     * @throws DaoException 
     */
    public void 
    clear() 
    throws DaoException;

    //------------------------------------------------------------------------
    /**
     * Closes the {@link Session}. The underlying implementation is JPA 
     * implementation-specific.
     * 
     * @throws DaoException 
     */
    public void 
    close() 
    throws DaoException;

    //------------------------------------------------------------------------
    /**
     * Flushes the {@link Session} by applying all cached changes to the 
     * persistent store. The underlying implementation is JPA implementation- 
     * specific.
     * 
     * @throws DaoException 
     */
    public void 
    flush() 
    throws DaoException;    
    
    //------------------------------------------------------------------------
    /**
     * Returns true if the instance belongs to the current persistence context, 
     * false otherwise
     * 
     * @param anEntity The entity to check
     * @return true if the instance belongs to the current persistence context, 
     *         false otherwise
     */
    public boolean
    contains(Object anEntity);

    //------------------------------------------------------------------------
    /**
     * Returns the Entity instance if found, null if the entity does not exist
     * 
     * @param <E>
     * @param aPersistentClass Persistent class
     * @param anId The Id to look for
     * @return Entity instance if found, null if the entity does not exist
     */
    public <E> E
    find(Class<E> aPersistentClass, Object anId);
    
    //------------------------------------------------------------------------
    /**
     * Returns the {@link ITransaction} adapted from the implementation-specific
     * 
     * @see IAdapter
     * @return {@link ITransaction} adapted from the implementation-specific
     */
    public ITransaction
    getTransaction();        

    //------------------------------------------------------------------------
    /**
     * Removes Object from the persistent store
     * 
     * @param anObject The Object to remove
     */
    public void
    remove(Object anObject);
    
    //------------------------------------------------------------------------
    /**
     * Returns the Id of the newly persisted Object. Creates the Object in 
     * the persistent store. Note that not all adapted implementations will
     * return an Id e.g. adapted Hibernate does but using pure JPA will not.
     * 
     * @param anObject The Object to persist
     * @return Id of the newly persisted Object
     */
    public <Id> Id
    persist(Object anObject);

    //------------------------------------------------------------------------
    /**
     * Updates the Object in the persistent store
     * 
     * @param anObject The Object to update
     */
    public void
    update(Object anObject);
    
    //------------------------------------------------------------------------
    /**
     * Refresh the state of the instance from the database, overwriting 
     * changes made to the entity, if any
     * 
     * @param anObject The Object to refresh
     */
    public void
    refresh(Object anObject);

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
    createNamedQuery(String aQueryName, Class<?> aPersistentClass);

    //------------------------------------------------------------------------
    /**
     * Returns instance of JPA implementation-specific Query for executing
     * JPA-specific Queries e.g. HQL queries. 
     * 
     * @param aSqlString SQL query input
     * @return instance of JPA implementation-specific Query
     */
    public IQuery
    createQuery(String aSqlString);

    //------------------------------------------------------------------------
    /**
     * Returns instance of JPA implementation-specific Query for executing
     * native SQL queries. 
     * 
     * @param aSql SQL query input
     * @return instance of JPA implementation-specific Query
     */
    public IQuery 
    createNativeQuery(String aSqlString, Class<?> aPersistentClass);

    //------------------------------------------------------------------------
    /**
     * Indicate to the EntityManager that a JTA transaction is active.
     */
    public void
    joinTransaction();
    
    //------------------------------------------------------------------------
    /**
     * Returns the implementation-specific Session
     * 
     * @return implementation-specific Session
     */
    public <S> S
    getDelegate();        
}

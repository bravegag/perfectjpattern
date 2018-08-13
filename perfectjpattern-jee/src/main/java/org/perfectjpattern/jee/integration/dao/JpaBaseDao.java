//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// JpaBaseDao.java Copyright (c) 2012 Giovanni Azua Garcia
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
import java.text.*;
import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Abstract reusable pure JPA-based implementation of {@link IBaseDao}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 11, 2009 10:17:46 AM $
 */
@SuppressWarnings("hiding")
public
class JpaBaseDao<Id extends Serializable, Element>
implements IBaseDao<Id, Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    contains(Element anElement)
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(anElement, "'anElement' must not be null");
        
        boolean myExists = false;        
        try 
        {
            ISession mySession = getSession();
            
            myExists = mySession.contains(anElement);

            return myExists;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();

            throw new DaoException(anException);
        }
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public int
    count()
    throws DaoException
    {
        int myCount = 0;
        
        try 
        {
            ISession mySession = getSession();
            
            IQuery myQuery = mySession.createQuery(MessageFormat.format(
                COUNT_STAR_PATTERN, thePersistentClass.getSimpleName()));
            
            myCount = ((Long) myQuery.getSingleResult()).intValue();

            return myCount;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            throw new DaoException(anException);
        }
    }        
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public Element 
    findById(Id anId) 
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(anId, "'anId' must not be null");
        
        Element myElement = null;
        try 
        {
            ISession mySession = getSession();
            
            myElement = mySession.find(getPersistentClass(), anId);
            
            return myElement;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            throw new DaoException(anException);
        }
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findByNamedQuery(String aQueryName, Object... anArguments)
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(aQueryName, "'aQueryName' must not be null");
        Validate.notNull(anArguments, "'anArguments' must not be null");

        try
        {
            ISession mySession = getSession();
            
            IQuery myQuery = mySession.createNamedQuery(aQueryName, 
                getPersistentClass());
            Validate.notNull(myQuery, "'aQueryName' was not found");
            
            final int myNumberOfArguments = anArguments.length;
            for (int i = 0; i < myNumberOfArguments; i++)
            {
                myQuery.setParameter(i + 1, anArguments[i]);
            }
            
            List<Element> myElements = myQuery.getResultList();
            
            return myElements;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            throw new DaoException(anException);
        }        
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findByNamedQuery(String aQueryName, Map<String, Object> aNamedArguments)
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(aQueryName, "'aQueryName' must not be null");
        Validate.notNull(aNamedArguments, "'aNamedArguments' must not be null");

        try
        {
            ISession mySession = getSession();
            
            IQuery myQuery = mySession.createNamedQuery(aQueryName, 
                getPersistentClass());            
            Validate.notNull(myQuery, "'aQueryName' was not found");
            
            for (Map.Entry<String, Object> myEntry : aNamedArguments.entrySet())
            {
                myQuery.setParameter(myEntry.getKey(), myEntry.getValue());
            }
            
            List<Element> myElements = myQuery.getResultList();
            
            return myElements;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
 
            throw new DaoException(anException);
        }        
    }    

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findAll(IOrder ... anOrder) 
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(anOrder, "'anOrder' must not be null");
        
        List<Element> myElements = null;
        
        try
        {
            ISession mySession = getSession();   
            
            String myOrderByClause = "";
            if (anOrder.length > 0)
            {
                StringBuilder myBuilder = new StringBuilder();
                myBuilder.append(ORDER_BY);
                for (IOrder myOrder : anOrder)
                {
                    myBuilder.append(myOrder);
                    myBuilder.append(", ");
                }                
                myBuilder.delete(myBuilder.length() - 2, myBuilder.length());
                
                myOrderByClause = myBuilder.toString();
            }            
            
            IQuery myQuery = mySession.createQuery(MessageFormat.
                format(FIND_ALL_PATTERN, getPersistentClass().getSimpleName(), 
                    myOrderByClause));
            
            myElements = myQuery.getResultList();
            
            return myElements;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            throw new DaoException(anException);
        }        
    }    

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findByPage(int aPageSize, int aPageIndex, IOrder anOrder)
    throws DaoException, IllegalArgumentException
    {
        Validate.isTrue(aPageSize > 0, "'aPageSize' must be greater than zero");
        Validate.isTrue(aPageIndex >= 1, "'aPageIndex' is out of range");
        Validate.notNull(anOrder, "'anOrderCriteria' must not be null");
        
        List<Element> myElements = null;
        
        try
        {
            ISession mySession = getSession();            
            
            StringBuilder myOrderByClause = new StringBuilder();
            myOrderByClause.append(anOrder);
            
            String myQueryString = MessageFormat.format(FIND_BY_PAGE_PATTERN, 
                getPersistentClass().getSimpleName(), myOrderByClause.
                    toString());
            IQuery myQuery = mySession.createQuery(myQueryString);
            
            int myPageIndex = aPageIndex - 1;
            int myStartIndex = (aPageSize * myPageIndex);            
            myQuery.setFirstResult(myStartIndex);
            myQuery.setMaxResults(aPageSize);
            
            myElements = myQuery.getResultList(); 
            
            // this precondition is currently checked at the end in order to 
            // avoid calling an extra count(*) at the beginning. It is a 
            // trade-off between performance and fail earlier 
            Validate.isTrue(!myElements.isEmpty() || aPageIndex == 1, 
                "'aPageIndex' is out of range");
                        
            return myElements;
        }
        catch (IllegalArgumentException anException)
        {
            throw anException;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            throw new DaoException(anException);
        }        
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Id
    create(Element anElement) 
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(anElement, "'anElement' must not be null");

        Id myId = null;
        
        try 
        {
            ISession mySession = getSession();
            
            myId = (Id) mySession.persist(anElement);
            
            return myId;
        } 
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            throw new DaoException(anException);
        }        
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    update(Element anElement) 
    throws DaoException
    {
        Validate.notNull(anElement, "'anElement' must not be null");

        try 
        {
            ISession mySession = getSession();
            
            mySession.update(anElement);
            
            return true;            
        } 
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();

            throw new DaoException(anException);
        }        
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    delete(Element anElement) 
    throws DaoException
    {
        Validate.notNull(anElement, "'anElement' must not be null");

        try 
        {
            ISession mySession = getSession();
            
            mySession.remove(anElement);
            
            return true;            
        } 
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            throw new DaoException(anException);
        }        
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    deleteAll() 
    throws DaoException
    {
        try 
        {
            ISession mySession = getSession();
            
            mySession.createQuery("delete from " + getPersistentClass().
                getName()).executeUpdate();
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException) 
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            throw new DaoException(anException);
        }
    }        
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public ITransaction
    getTransaction()
    {
        return theTransactionStrategy.getTransaction();
    }              

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public ISession 
    getSession()
    {
        if (!theTransactionStrategy.isManaged())
        {
            ITransaction myTransaction = getTransaction();
            if (!myTransaction.isActive())
            {
                myTransaction.begin();
            }
        }
        
        return theSessionStrategy.getSession();
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link HibernateGenericReadOnlyDao} instance from the 
     * persistent class type, the {@link ISessionStrategy} that creates 
     * {@link ISession} instances and the {@link ITransactionStrategy} that 
     * creates {@link ITransaction} instances 
     * 
     * @param aPersistentClass The persistent Java Bean class
     * @param aSessionStrategy Factory that creates Sessions
     * @param aTransactionStrategy Factory that creates Transaction
     */
    protected
    JpaBaseDao(Class<Element> aPersistentClass, 
        ISessionStrategy aSessionStrategy, 
            ITransactionStrategy aTransactionStrategy)
    throws IllegalArgumentException
    {
        assert aPersistentClass != null : "'aPersistentClass' must not be null";
        assert !aPersistentClass.isInterface() : 
            "'aPersistentClass' must be a class type";
        assert aSessionStrategy != null : 
            "'aDaoSessionStrategy' must not be null";
        
        thePersistentClass = aPersistentClass;
        theSessionStrategy = aSessionStrategy;
        theTransactionStrategy = aTransactionStrategy;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the {@link EntityManager}, called from a managed EJB that exposes
     * {@link IGenericReadOnlyDao} or {@link IGenericDao} remotely. 
     * 
     * @param anEntityManager The {@link EntityManager} to set
     * @throws IllegalArgumentException 'anEntityManager' must not be null
     */
    protected void
    setEntityManager(EntityManager anEntityManager)
    throws IllegalArgumentException
    {
        assert anEntityManager != null : "'anEntityManager' must not be null";
        
        assert theSessionStrategy instanceof ManagedSessionStrategy : 
            "SessionStrategy must be set to ManagedSessionStrategy";
        
        ManagedSessionStrategy mySessionStrategy = 
            (ManagedSessionStrategy) theSessionStrategy;
        
        mySessionStrategy.setEntityManager(anEntityManager);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final Class<Element> 
    getPersistentClass()
    {
        return thePersistentClass;
    }        
    
    //------------------------------------------------------------------------
    /**
     * Returns the sessionStrategy
     *
     * @return the sessionStrategy
     */
    protected final ISessionStrategy 
    getSessionStrategy()
    {
        return theSessionStrategy;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the transactionStrategy
     *
     * @return the transactionStrategy
     */
    protected final ITransactionStrategy 
    getTransactionStrategy()
    {
        return theTransactionStrategy;
    }
   
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Class<Element> thePersistentClass;
    private final ISessionStrategy theSessionStrategy;
    private final ITransactionStrategy theTransactionStrategy;    
    
    protected static final String MODEL_ALIAS = "e";
    protected static final String ORDER_BY = "order by ";
    protected static final String COUNT_STAR_PATTERN = "select count(" + 
        MODEL_ALIAS + ") from {0} " + MODEL_ALIAS;        
    protected static final String FIND_ALL_PATTERN = "select " + MODEL_ALIAS + 
        " from {0} " + MODEL_ALIAS + " {1}";
    protected static final String FIND_BY_PAGE_PATTERN = "select " + 
        MODEL_ALIAS + " from {0} " + MODEL_ALIAS + " " + ORDER_BY + " {1}";    
}

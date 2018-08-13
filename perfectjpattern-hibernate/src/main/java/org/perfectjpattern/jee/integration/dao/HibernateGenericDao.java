//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// HibernateGenericDao.java Copyright (c) 2012 Giovanni Azua Garcia
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

import javax.persistence.*;

import org.apache.commons.lang3.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Hibernate-based implementation of {@link IGenericReadOnlyDao}
 * 
 * @param <Id> Identification type
 * @param <Element> Element type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 26, 2007 9:15:14 PM $
 */
@SuppressWarnings("hiding")
public 
class HibernateGenericDao<Id extends Serializable, Element>
extends JpaBaseDao<Id, Element>
implements IGenericDao<Id, Element>
{
    //------------------------------------------------------------------------
    // public    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Element>
    findByExample(Element anExample, String ... anExcludeProperties)
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(anExample, "'anElement' must not be null");        
        Validate.notNull(anExcludeProperties, 
            "'anExcludeProperties' must not be null");
        
        List<Element> myElements = null;
        
        try
        {
            Session mySession = getActualSession();            
            
            Criteria myCriteria = mySession.createCriteria(
                getPersistentClass());
            Example myExample = Example.create(anExample);
            for (String myPropertyName : anExcludeProperties)
            {
                myExample.excludeProperty(myPropertyName);
            }
            
            myElements = myCriteria.add(myExample).list();
            
            return myElements;            
        }
        catch (HibernateException anException) 
        {
            throw new DaoException(anException);
        }        
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Element>
    findByExample(Element anExample, IOrder anOrder,
        String ... anExcludeProperties)
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(anExample, "'anElement' must not be null");        
        Validate.notNull(anOrder, "'anOrder' must not be null");
        Validate.notNull(anExcludeProperties, 
            "'anExcludeProperties' must not be null");
        
        List<Element> myElements = null;
        
        try
        {
            Session mySession = getActualSession();            
            
            // add Example
            Criteria myCriteria = mySession.createCriteria(
                getPersistentClass());
            Example myExample = Example.create(anExample);
            for (String myPropertyName : anExcludeProperties)
            {
                myExample.excludeProperty(myPropertyName);
            }            
            myCriteria.add(myExample);
            
            // specify Order criteria
            StringTokenizer myTokenizer = new StringTokenizer(anOrder.
                toString(), ",");
            while (myTokenizer.hasMoreTokens())
            {
                IOrder myOrder = Order.valueOf(myTokenizer.nextToken());
                
                // strip the direction of the order criteria
                if (myOrder.getAscDesc() == AscDescEnum.ASC)
                {
                    myCriteria.addOrder(org.hibernate.criterion.Order.
                        asc(myOrder.getPropertyName()));
                }
                else
                {
                    myCriteria.addOrder(org.hibernate.criterion.Order.
                        desc(myOrder.getPropertyName()));
                }                
            }            
            
            // execute the Criteria Query
            myElements = myCriteria.list();
            
            return myElements;            
        }
        catch (HibernateException anException) 
        {
            throw new DaoException(anException);
        }        
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Element> 
    findByPage(int aPageSize, int aPageIndex, Element anExample, 
        IOrder anOrder, String... anExcludeProperties)
    throws DaoException, IllegalArgumentException
    {
        Validate.isTrue(aPageSize > 0, "'aPageSize' must be greater than zero");
        Validate.isTrue(aPageIndex >= 1, "'aPageIndex' is out of range");
        Validate.notNull(anExample, "'anExample' must not be null");
        Validate.notNull(anOrder, "'anOrderCriteria' must not be null");
        Validate.notNull(anExcludeProperties, 
            "'anExcludeProperties' must not be null");
        
        List<Element> myElements = null;
        
        try
        {
            Session mySession = getActualSession();            
            
            // add Example
            Criteria myCriteria = mySession.createCriteria(
                getPersistentClass());
            Example myExample = Example.create(anExample);
            for (String myPropertyName : anExcludeProperties)
            {
                myExample.excludeProperty(myPropertyName);
            }
            
            myCriteria.add(myExample);
            
            // specify Order criteria
            StringTokenizer myTokenizer = new StringTokenizer(anOrder.
                toString(), ",");
            while (myTokenizer.hasMoreTokens())
            {
                IOrder myOrder = Order.valueOf(myTokenizer.nextToken());
                
                // strip the direction of the order criteria
                if (myOrder.getAscDesc() == AscDescEnum.ASC)
                {
                    myCriteria.addOrder(org.hibernate.criterion.Order.
                        asc(myOrder.getPropertyName()));
                }
                else
                {
                    myCriteria.addOrder(org.hibernate.criterion.Order.
                        desc(myOrder.getPropertyName()));
                }                
            }            
            
            int myPageIndex = aPageIndex - 1;
            int myStartIndex = (aPageSize * myPageIndex);            
            myCriteria.setFirstResult(myStartIndex);
            myCriteria.setMaxResults(aPageSize);
            
            // execute the Criteria Query
            myElements = myCriteria.list();
            
            // this precondition is currently checked at the end in order to 
            // avoid calling an overhead count(*) at the beginning. It is a 
            // trade-off between performance and fail earlier 
            Validate.isTrue(!myElements.isEmpty() || aPageIndex == 1, 
                "'aPageIndex' is out of range");
                        
            return myElements;
        }
        catch (IllegalArgumentException anException)
        {
            throw anException;
        }
        catch (HibernateException anException) 
        {
            throw new DaoException(anException);
        }        
    }                
    
    //------------------------------------------------------------------------
    // protected    
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link HibernateGenericDao} instance from the 
     * persistent class type, the {@link ISessionStrategy} that creates 
     * {@link Session} instances and the {@link ITransactionStrategy} that 
     * creates {@link Transaction} instances 
     * 
     * @param aPersistentClass The persistent Java Bean class
     * @param aSessionStrategy Factory that creates Sessions
     * @param aTransactionStrategy Factory that creates Transaction
     */
    protected
    HibernateGenericDao(Class<Element> aPersistentClass, 
        ISessionStrategy aSessionStrategy, 
            ITransactionStrategy aTransactionStrategy)
    throws IllegalArgumentException
    {
        super(aPersistentClass, aSessionStrategy, aTransactionStrategy);
    }

    //------------------------------------------------------------------------
    /**
     * Returns the underlying Session
     * 
     * @return the underlying Session
     */
    protected Session
    getActualSession()
    throws DaoException    
    {
        Object mySession = getSession().getDelegate();
        while (mySession != null && mySession instanceof EntityManager)
        {
            mySession = ((EntityManager) mySession).getDelegate();
        }
        
        assert mySession instanceof Session : 
            "Actual Session must be a Hibernate Session";
        
        return (Session) mySession;
    }
}

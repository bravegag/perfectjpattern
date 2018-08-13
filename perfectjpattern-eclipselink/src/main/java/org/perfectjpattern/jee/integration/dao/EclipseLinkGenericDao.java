//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// EclipseLinkGenericDao.java Copyright (c) 2012 Giovanni Azua Garcia
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
import javax.transaction.*;

import org.apache.commons.lang3.*;
import org.eclipse.persistence.exceptions.*;
import org.eclipse.persistence.jpa.*;
import org.eclipse.persistence.queries.*;
import org.eclipse.persistence.sessions.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * EclipseLink-based implementation of {@link IGenericReadOnlyDao}
 *
 * @param <Id> Identification type
 * @param <Element> Element type
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 26, 2007 9:15:14 PM $
 */
@SuppressWarnings("hiding")
public
class EclipseLinkGenericDao<Id extends Serializable, Element>
extends JpaBaseDao<Id, Element>
implements IGenericDao<Id, Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked" })
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
            JpaEntityManager mySession = getActualSession();

            ReadAllQuery myQuery = new ReadAllQuery();
            myQuery.setExampleObject(anExample);

            AttributeExclusionQueryByExamplePolicy myQueryByExamplePolicy =
                new AttributeExclusionQueryByExamplePolicy();
            for (String myPropertyName : anExcludeProperties)
            {
                myQueryByExamplePolicy.excludeProperty(myPropertyName);
            }

            myQuery.setQueryByExamplePolicy(myQueryByExamplePolicy);

            Query myJpaQuery = mySession.createQuery(myQuery);

            // execute the Query
            myElements = myJpaQuery.getResultList();

            return myElements;
        }
        catch (EclipseLinkException anException)
        {
            anException.printStackTrace();

            throw new DaoException(anException);
        }
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked" })
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
            JpaEntityManager mySession = getActualSession();

            // add Example
            ReadAllQuery myQuery = new ReadAllQuery();
            myQuery.setExampleObject(anExample);

            AttributeExclusionQueryByExamplePolicy myQueryByExamplePolicy =
                new AttributeExclusionQueryByExamplePolicy();
            for (String myPropertyName : anExcludeProperties)
            {
                myQueryByExamplePolicy.excludeProperty(myPropertyName);
            }

            myQuery.setQueryByExamplePolicy(myQueryByExamplePolicy);

            // specify Order criteria
            StringTokenizer myTokenizer = new StringTokenizer(anOrder.
                toString(), ",");
            while (myTokenizer.hasMoreTokens())
            {
                IOrder myOrder = Order.valueOf(myTokenizer.nextToken());

                // strip the direction of the order criteria
                if (myOrder.getAscDesc() == AscDescEnum.ASC)
                {
                    myQuery.addAscendingOrdering(myOrder.getPropertyName());
                }
                else
                {
                    myQuery.addDescendingOrdering(myOrder.getPropertyName());
                }
            }

            Query myJpaQuery = mySession.createQuery(myQuery);

            // execute the Query
            myElements = myJpaQuery.getResultList();

            return myElements;
        }
        catch (EclipseLinkException anException)
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
            JpaEntityManager mySession = getActualSession();

            // add Example
            ReadAllQuery myQuery = new ReadAllQuery();
            myQuery.setExampleObject(anExample);

            AttributeExclusionQueryByExamplePolicy myQueryByExamplePolicy =
                new AttributeExclusionQueryByExamplePolicy();
            for (String myPropertyName : anExcludeProperties)
            {
                myQueryByExamplePolicy.excludeProperty(myPropertyName);
            }

            myQuery.setQueryByExamplePolicy(myQueryByExamplePolicy);

            // specify Order criteria
            StringTokenizer myTokenizer = new StringTokenizer(anOrder.
                toString(), ",");
            while (myTokenizer.hasMoreTokens())
            {
                IOrder myOrder = Order.valueOf(myTokenizer.nextToken());

                // strip the direction of the order criteria
                if (myOrder.getAscDesc() == AscDescEnum.ASC)
                {
                    myQuery.addAscendingOrdering(myOrder.getPropertyName());
                }
                else
                {
                    myQuery.addDescendingOrdering(myOrder.getPropertyName());
                }
            }

            int myPageIndex = aPageIndex - 1;
            int myStartIndex = (aPageSize * myPageIndex);
            myQuery.setFirstResult(myStartIndex);
            myQuery.setMaxRows(aPageSize);

            Query myJpaQuery = mySession.createQuery(myQuery);

            // execute the Query
            myElements = myJpaQuery.getResultList();

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
        catch (EclipseLinkException anException)
        {
            throw new DaoException(anException);
        }
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Element
    findById(Id anId)
    throws DaoException, IllegalArgumentException
    {
        Validate.notNull(anId, "'anId' must not be null");

        Element myElement = null;

        try
        {
            ReadObjectQuery myQuery = new ReadObjectQuery(getPersistentClass());
            myQuery.setSelectionId(anId);

            Query myJpaQuery = getActualSession().createQuery(myQuery);

            myElement = (Element) myJpaQuery.getSingleResult();

            return myElement;
        }
        catch (NoResultException anException)
        {
            return myElement;
        }
        catch (EclipseLinkException anException)
        {
            throw new DaoException(anException.getCause());
        }
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link EclipseLinkGenericDao} instance from the
     * persistent class type, the {@link ISessionStrategy} that creates
     * {@link UnitOfWork} instances and the {@link ITransactionStrategy} that
     * creates {@link Transaction} instances
     *
     * @param aPersistentClass The persistent Java Bean class
     * @param aSessionStrategy Factory that creates Sessions
     * @param aTransactionStrategy Factory that creates Transaction
     */
    protected
    EclipseLinkGenericDao(Class<Element> aPersistentClass,
        ISessionStrategy aSessionStrategy,
            ITransactionStrategy aTransactionStrategy)
    throws IllegalArgumentException
    {
        super(aPersistentClass, aSessionStrategy, aTransactionStrategy);
    }

    //------------------------------------------------------------------------
    /**
     * Returns the underlying JpaEntityManager
     *
     * @return the underlying JpaEntityManager
     */
    protected JpaEntityManager
    getActualSession()
    throws DaoException
    {
        JpaEntityManager mySession = JpaHelper.getEntityManager((EntityManager)
            getSession().getDelegate());

        return mySession;
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Subclasses {@link QueryByExamplePolicy} featuring exclusion by property
     * name rather than only values.
     */
    private static
    class AttributeExclusionQueryByExamplePolicy
    extends QueryByExamplePolicy
    {
        //--------------------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("rawtypes")
        @Override
        public boolean
        shouldIncludeInQuery(Class aClass, String anAttributeName,
            Object anAttributeValue)
        {
            boolean myShouldInclude = true;
            if (theExcludedAttributes.contains(anAttributeName))
            {
                myShouldInclude = false;
            }
            else
            {
                myShouldInclude = super.shouldIncludeInQuery(aClass,
                    anAttributeName, anAttributeValue);
            }

            return myShouldInclude;
        }

        //--------------------------------------------------------------------
        /**
         * Adds new attribute to exclude
         *
         * @param anAttributeName The attribute name to exclude
         */
        public void
        excludeProperty(String anAttributeName)
        {
            theExcludedAttributes.add(anAttributeName);
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private static final long serialVersionUID = 1L;
        private final Set<String> theExcludedAttributes = new HashSet<String>();
    }
}

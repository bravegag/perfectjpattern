//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// SpringGenericDao.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.springframework.transaction.annotation.*;

/**
 * Spring-based decorator implementation of {@link IGenericDao} that can be 
 * parameterized with any concrete underlying implementation of 
 * {@link IGenericDao}. 
 * 
 * @param <Element> Element type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Nov 5, 2008 11:40:00 AM $
 */
@Transactional
public 
class SpringGenericDao<Id, Element>
implements IFinderExecutor<Element>, IGenericDao<Id, Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link SpringGenericDao} from the Component 
     * &lt;ComponentDao&gt; instance to decorate.
     * <br/></br>
     * @param aComponent The Component instance to Decorate
     * @throws IllegalArgumentException 'aComponent' must not be null
     */
    public 
    SpringGenericDao(IGenericDao<Id, Element> aComponent)
    {
        Validate.notNull(aComponent, "'aComponent' must not be null");
        
        theComponent = aComponent;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    execute(Method aMethod, Object... anArguments)
    {
        final IQuery myQuery = prepareQuery(aMethod, anArguments);
        
        return myQuery.getResultList();
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns the namingStrategy
     * 
     * @return the namingStrategy
     */
    public final IFinderNamingStrategy 
    getNamingStrategy()
    {
        return theNamingStrategy;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the namingStrategy
     * 
     * @param aNamingStrategy the namingStrategy to set
     */
    public final void 
    setNamingStrategy(IFinderNamingStrategy aNamingStrategy)
    {
        theNamingStrategy = aNamingStrategy;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findByExample(Element anExample, String... anExcludeProperties)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.findByExample(anExample, anExcludeProperties);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findByExample(Element anExample, IOrder anOrder, 
        String... anExcludeProperties)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.findByExample(anExample, anOrder, 
            anExcludeProperties);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> findByPage(int aPageSize, int aPageIndex, 
        Element anExample, IOrder anOrder, String... anExcludeProperties)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.findByPage(aPageSize, aPageIndex, anExample, 
            anOrder, anExcludeProperties);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    contains(Element anElement)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.contains(anElement);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public int 
    count() 
    throws DaoException
    {
        return theComponent.count();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findAll(IOrder... anOrder)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.findAll(anOrder);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public Element 
    findById(Id anId)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.findById(anId);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findByNamedQuery(String aQueryName, Object... anArguments)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.findByNamedQuery(aQueryName, anArguments);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findByNamedQuery(String aQueryName, Map<String, Object> aNamedParameters)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.findByNamedQuery(aQueryName, aNamedParameters);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Element> 
    findByPage(int aPageSize, int aPageIndex, IOrder anOrder)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.findByPage(aPageSize, aPageIndex, anOrder);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public Class<Element> 
    getPersistentClass()
    {
        return theComponent.getPersistentClass();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public ISession 
    getSession()
    {
        return theComponent.getSession();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public ITransaction 
    getTransaction()
    {
        return theComponent.getTransaction();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public Id 
    create(Element anElement)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.create(anElement);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    delete(Element anElement)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.delete(anElement);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    deleteAll() throws DaoException
    {
        theComponent.deleteAll();        
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    update(Element anElement)
    throws DaoException, IllegalArgumentException
    {
        return theComponent.update(anElement);
    }    
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    private IQuery 
    prepareQuery(Method aMethod, Object... anArguments) 
    {
        assert aMethod != null : "'aMethod' must not be null";
        assert anArguments != null : "'anArguments' must not be null";
        
        final String myQueryName = getNamingStrategy().getQueryName(
            getPersistentClass(), aMethod);
        
        final IQuery myQuery = getSession().createNamedQuery(myQueryName, 
            getPersistentClass());
        
        String[] myQueryParameters = myQuery.getNamedParameters();
        if (myQueryParameters.length == 0) 
        {
            setPositionalParams(myQuery, anArguments);
        } 
        else 
        {
            Annotation myAnnotation = null;
            final int myNumberOfAnnotations = aMethod.getAnnotations().length;
            for (int i = 0; i < myNumberOfAnnotations; ++i)
            {
                if (aMethod.isAnnotationPresent(QueryParameters.class))
                {
                    myAnnotation = aMethod.getAnnotations()[i];
                    
                    break;
                }
            }
            
            assert myAnnotation != null : "'" + aMethod.getName() + 
                "' does not include the required QueryParameters annotation";
            
            String[] myAnnotatedParameters = ((QueryParameters) 
                myAnnotation).names();
            
            final int myNumberOfAnnotated = myAnnotatedParameters.length;
            final int myNumberOfParameters = myQueryParameters.length;

            assert myNumberOfAnnotated == myNumberOfParameters : "Arguments " +
                "mismatch, all query parameters must be annotated"; 
            
            
            setNamedParams(myQuery, anArguments, myQueryParameters, 
                myAnnotatedParameters);
        }
        
        return myQuery;
    }    
    
    //------------------------------------------------------------------------
    /**
     * Assigns the parameters according to their order in the finder method 
     * signature. This method is called when the query parameters are defined
     * as ? and not named.
     * 
     * @param aNamedQuery The named query defined in the mapping
     * @param aMethodArguments The finder method argument values
     */
    private void 
    setPositionalParams(IQuery aNamedQuery, Object[] aMethodArguments) 
    {
        assert aNamedQuery != null : "'aNamedQuery' must not be null";
        assert aMethodArguments != null : "'anArguments' must not be null";
        
        // set parameter
        for (int i = 0; i < aMethodArguments.length; i++) 
        {
            Object myArgument = aMethodArguments[i];
            
            aNamedQuery.setParameter(i + 1, myArgument);
        }
    }
    
    //------------------------------------------------------------------------
    /**
     * Assigns the parameters of the interface finder method to the named 
     * query. The parameters are assigned based on the name of the finder 
     * method arguments to match those of the named query. The arguments 
     * are not assumed to be in any specific order but will be matched 
     * according to the name. In case that there is a mismatch i.e. missing 
     * or outstanding parameter in either side a {@link IllegalAccessError} 
     * will be thrown. 
     * 
     * @param aNamedQuery The named query defined in the mapping
     * @param aMethodArguments The finder method argument values
     * @param aQueryParameters The named query parameter place holders
     * @param aMethodParameters The ordered parameter names as defined in 
     *        the finder method signature
     * @throws IllegalAccessError "Mismatching number of arguments" 
     */
    private void 
    setNamedParams(IQuery aNamedQuery, Object[] aMethodArguments, 
        String[] aQueryParameters, String[] aMethodParameters) 
    {        
        assert aNamedQuery != null : "'aNamedQuery' must not be null";
        assert aMethodArguments != null : "'anArguments' must not be null";
        assert aQueryParameters != null : "'aNamedParameters' must not be null";
        assert aMethodParameters != null : 
            "'anOrderedArgumentNames' must not be null";

        if (aMethodArguments.length != aQueryParameters.length || 
            aQueryParameters.length != aMethodParameters.length)
        {
            throw new IllegalAccessError("Mismatching number of arguments");
        }

        // populate a Map of Method parameter values keyed by parameter name
        Map<String, Object> myArgumentsMap = new HashMap<String, Object>();
        final int myNumberOfParameters = aMethodArguments.length;
        for (int i = 0; i < myNumberOfParameters; i++)
        {
            myArgumentsMap.put(aMethodParameters[i], aMethodArguments[i]);
        }
                
        // finally assign the parameters
        for (String myQueryParameter : aQueryParameters)
        {
            // do the parameter matching taking into account naming conventions
            Object myArgument = null;
            if (myArgumentsMap.containsKey(myQueryParameter))
            {
                myArgument = myArgumentsMap.get(myQueryParameter); 
            }
            
            assert myArgument != null : "Query parameter '" + 
                myQueryParameter + "' was not found, check for misspelling";
            
            aNamedQuery.setParameter(myQueryParameter, myArgument);
        }        
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Defines an strategy for discovering the finder methods
     */
    private IFinderNamingStrategy theNamingStrategy = 
        new SimpleFinderNamingStrategy();    
    private final IGenericDao<Id, Element> theComponent;
}

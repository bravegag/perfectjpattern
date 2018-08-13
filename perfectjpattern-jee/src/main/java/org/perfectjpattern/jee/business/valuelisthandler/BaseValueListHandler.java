//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// BaseValueListHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.business.valuelisthandler;

import java.io.*;
import java.util.*;

import javax.ejb.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.jee.api.business.valuelisthandler.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Reusable concrete implementation of {@link IBaseValueListHandler}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 18, 2009 11:13:57 PM $
 */
public 
class BaseValueListHandler<Element extends Serializable>
extends AbstractValueListHandler<Element>
implements IBaseValueListHandler<Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Default constructor needed for compliance with EJB Session 
     * Bean specification
     */
    public 
    BaseValueListHandler()
    {
        // empty
    }
    
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link BaseValueListHandler} from a {@link IBaseReadOnlyDao}
     */
    public 
    BaseValueListHandler(IBaseReadOnlyDao<?, Element> aReadOnlyDao)
    {
        Validate.notNull(aReadOnlyDao, "'aReadOnlyDao' must not be null");
        
        setReadOnlyDao(aReadOnlyDao);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    executeQueryAll(IOrder... anOrder)
    throws IllegalArgumentException, ListHandlerException
    {
        try
        {
            List<Element> myElements = getReadOnlyDao().findAll(anOrder);
            
            super.setElements(myElements);
        }
        catch (DaoException anException)
        {
            throw new ListHandlerException(anException);
        }
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    executeNamedQuery(String aQueryName, Object... anArguments)
    throws IllegalArgumentException, ListHandlerException
    {
        try
        {
            List<Element> myElements = getReadOnlyDao().findByNamedQuery(
                aQueryName, anArguments);
            
            super.setElements(myElements);
        }
        catch (DaoException anException)
        {
            throw new ListHandlerException(anException);
        }
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    executeNamedQuery(String aQueryName, 
        Map<String, Object> aNamedParameters)
    throws IllegalArgumentException, ListHandlerException
    {
        try
        {
            List<Element> myElements = getReadOnlyDao().findByNamedQuery(
                aQueryName, aNamedParameters);
            
            super.setElements(myElements);
        }
        catch (DaoException anException)
        {
            throw new ListHandlerException(anException);
        }
    }    

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Remove
    public void 
    dispose()
    {
       // empty        
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Returns the readOnlyDao
     *
     * @return the readOnlyDao
     */
    protected IBaseReadOnlyDao<?, Element> 
    getReadOnlyDao()
    {
        return theReadOnlyDao;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the readOnlyDao
     * 
     * @param aReadOnlyDao the readOnlyDao to set
     */
    protected final void 
    setReadOnlyDao(IBaseReadOnlyDao<?, Element> aReadOnlyDao)
    {
        assert aReadOnlyDao != null : "'aReadOnlyDao' must not be null";
        
        theReadOnlyDao = aReadOnlyDao;
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private IBaseReadOnlyDao<?, Element> theReadOnlyDao;
}

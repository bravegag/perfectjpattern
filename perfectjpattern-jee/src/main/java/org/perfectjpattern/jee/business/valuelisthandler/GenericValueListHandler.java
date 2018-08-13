//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// GenericValueListHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.jee.api.business.valuelisthandler.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Reusable concrete implementation of {@link IGenericValueListHandler}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 18, 2009 11:15:29 PM $
 */
public 
class GenericValueListHandler<Element extends Serializable>
extends BaseValueListHandler<Element>
implements IGenericValueListHandler<Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Default constructor needed for compliance with EJB Session 
     * Bean specification
     */
    public 
    GenericValueListHandler()
    {
        // empty
    }
    
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link GenericValueListHandler} from a 
     * {@link IGenericReadOnlyDao}
     */
    public 
    GenericValueListHandler(IGenericReadOnlyDao<?, Element> aReadOnlyDao)
    {
        super(aReadOnlyDao);
        
        setReadOnlyDao(aReadOnlyDao);
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    executeQueryByExample(Element anExample, String... anExcludeProperties)
    throws IllegalArgumentException, ListHandlerException
    {
        try
        {
            List<Element> myElements = getReadOnlyDao().findByExample(
                anExample, anExcludeProperties);
            
            super.setElements(myElements);
        }
        catch (DaoException anException)
        {
            throw new ListHandlerException(anException.getMessage());
        }
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    executeQueryByExample(Element anExample, IOrder anOrder, 
        String... anExcludeProperties)
    throws IllegalArgumentException, ListHandlerException
    {
        try
        {
            List<Element> myElements = getReadOnlyDao().findByExample(
                anExample, anOrder, anExcludeProperties);
            
            super.setElements(myElements);
        }
        catch (DaoException anException)
        {
            throw new ListHandlerException(anException.getMessage());
        }
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Returns the readOnlyDao
     *
     * @return the readOnlyDao
     */
    @Override
    protected IGenericReadOnlyDao<?, Element> 
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
    setReadOnlyDao(IGenericReadOnlyDao<?, Element> aReadOnlyDao)
    {
        assert aReadOnlyDao != null : "'aReadOnlyDao' must not be null";

        super.setReadOnlyDao(aReadOnlyDao);
        
        theReadOnlyDao = aReadOnlyDao;
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private IGenericReadOnlyDao<?, Element> theReadOnlyDao;
}

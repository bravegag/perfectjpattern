//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// HibernateQueryAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Adapts JPA {@link Query} to PerfectJPattern's {@link IQuery} 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 18, 2009 11:54:49 AM $
 */
public 
class QueryAdapter
extends Adapter<IQuery, Query>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public IQuery 
    setParameter(int aPosition, Object aValue)
    {
        getUnderlying().setParameter(aPosition, aValue);
        
        return getTarget();
    }    
    
    //------------------------------------------------------------------------
    public IQuery 
    setParameter(String aName, Object aValue)
    {
        getUnderlying().setParameter(aName, aValue);
        
        return getTarget();
    }    
    
    //------------------------------------------------------------------------
    public String[]
    getNamedParameters() 
    {
        throw new UnsupportedOperationException("Pure JPA implementation " +
            "does not yet support 'getNamedParameters()'");
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link QueryAdapter} from the Adaptee {@link Query} instance
     * 
     * @param anAdaptee Adaptee JPA Query
     * @throws IllegalArgumentException 'anAdaptee' must not be null
     */
    protected
    QueryAdapter(Query anAdaptee)
    throws IllegalArgumentException
    {
        super(IQuery.class, anAdaptee);
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
            // make sure that Exceptions are properly chained
            throw new DaoException(anException.getCause());            
        }                
        // CHECKSTYLE:OFF
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            // make sure that Exceptions are properly chained
            throw new DaoException(anException);
        }
    }    
}

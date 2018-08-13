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

import java.util.*;

import org.hibernate.*;
import org.perfectjpattern.core.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Adapts Hibernate's {@link Query} to the JPA implementation-free 
 * PerfectJPattern's {@link IQuery} definition 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 18, 2009 11:54:49 AM $
 */
public 
class HibernateQueryAdapter
extends Adapter<IQuery, Query>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public <E> List<E> 
    getResultList()
    {
        return getUnderlying().list();
    }

    //------------------------------------------------------------------------
    public Object 
    getSingleResult()
    {
        return getUnderlying().uniqueResult();
    }
    
    //------------------------------------------------------------------------
    public IQuery 
    setParameter(int aPosition, Object aValue)
    {
        // adapt the position value to be aPosition - 1 because hibernate 
        // QueryImpl will automatically add one
        getUnderlying().setParameter(aPosition - 1, aValue);
        
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
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link HibernateQueryAdapter} from the Adaptee 
     * {@link Query} instance
     * 
     * @param anAdaptee Adaptee Hibernate Query
     * @throws IllegalArgumentException
     */
    protected
    HibernateQueryAdapter(Query anAdaptee)
    throws IllegalArgumentException
    {
        super(IQuery.class, anAdaptee);
    }    
}

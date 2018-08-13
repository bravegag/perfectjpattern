//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ReadQueryAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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
import java.util.*;

import org.apache.commons.lang3.*;
import org.eclipse.persistence.queries.*;
import org.eclipse.persistence.sessions.*;
import org.eclipse.persistence.sessions.server.*;
import org.perfectjpattern.core.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Adapts EclipseLink {@link DatabaseQuery} to the JPA implementation-free 
 * PerfectJPattern's {@link IQuery} definition 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jul 18, 2009 12:53:32 PM $
 */
public 
class ReadQueryAdapter
extends Adapter<IQuery, ReadQuery>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public <Element> List<Element> 
    getResultList()
    {
        ReadQuery myReadQuery = getUnderlying();

        if (myReadQuery.getArgumentValues().size() > 0 && 
            theNamedParameters.size() > 0) 
        {
            throw new IllegalArgumentException("ReadQueryAdapter must be in " +
                "one mode only, either with positional or named parameters");
        }
        
        List<Object> myArguments = new ArrayList<Object>();        
        if (myReadQuery.getArgumentValues().size() > 0)
        {
            // positional mode
            myArguments.addAll(myReadQuery.getArgumentValues());
        }
        else        
        {
            // named mode
            List<String> myArgumentNames = myReadQuery.getArguments();
            for (String myArgumentName : myArgumentNames)
            {
                Validate.isTrue(theNamedParameters.containsKey(myArgumentName), 
                    "'aNamedArguments' does not contain required parameter '" + 
                        myArgumentName + "'");
                
                myArguments.add(theNamedParameters.get(myArgumentName));
            }                        
        }
        
        List<Element> myElements = (List<Element>) theSession.executeQuery(
            myReadQuery.getName(), thePersistentClass, myArguments);
        
        return myElements;
    }

    //------------------------------------------------------------------------
    public Object 
    getSingleResult()
    {
        return getUnderlying().getFirstResult();
    }
    
    //------------------------------------------------------------------------
    public IQuery 
    setParameter(int aPosition, Object aValue)
    {
        getUnderlying().addArgumentValue(aValue);
        
        return getTarget();
    }    

    //------------------------------------------------------------------------
    public IQuery 
    setParameter(String aName, Object aValue)
    {
        theNamedParameters.put(aName, aValue);
        
        return getTarget();
    }
    
    //------------------------------------------------------------------------
    public Object 
    setMaxResults(int aMaxRows)
    {
        getUnderlying().setMaxRows(aMaxRows);
        
        return getTarget();
    }    
    
    //------------------------------------------------------------------------
    public int 
    executeUpdate()
    {
        throw new UnsupportedOperationException(
            "'executeUpdate' not supported");
    }

    //------------------------------------------------------------------------
    public String[]
    getNamedParameters() 
    {
        DatabaseQuery myQuery = super.getUnderlying();
        
        final int myLength = myQuery.getArguments().size();
        String[] myNamedParameters = new String[myLength];
        for (int i = 0; i < myLength; i++)
        {
            myNamedParameters[i] = myQuery.getArguments().get(i).
                toString();
        }
        
        return myNamedParameters;
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link QueryAdapter} from the Adaptee 
     * {@link ReadQuery} instance
     * 
     * @param anAdaptee Adaptee {@link ReadQuery} instance
     * @param aSession Holding {@link ServerSession} instance
     * @throws IllegalArgumentException
     */
    protected
    ReadQueryAdapter(ReadQuery anAdaptee, Session aSession, 
        Class<?> aPersistentClass)
    throws IllegalArgumentException
    {
        super(IQuery.class, anAdaptee);
        
        Validate.notNull(aSession, "'aSession' must not be null");
        Validate.notNull(aSession, "'aPersistentClass' must not be null");
        
        theSession = aSession;
        thePersistentClass = aPersistentClass;
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
            throw new DaoException(anException);            
        }                
        // CHECKSTYLE:OFF
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            anException.printStackTrace();
            
            // make sure that Exceptions are properly chained
            throw new DaoException(anException);
        }
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Session theSession;
    private final Class<?> thePersistentClass;
    private final Map<String, Object> theNamedParameters = 
        new HashMap<String, Object>();
}

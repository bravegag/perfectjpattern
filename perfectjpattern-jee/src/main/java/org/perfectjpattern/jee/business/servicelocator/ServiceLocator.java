//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ServiceLocator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.business.servicelocator;

import java.text.*;
import java.util.*;

import javax.naming.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.creational.singleton.*;
import org.perfectjpattern.jee.api.business.servicelocator.*;

/**
 * Generic componentized implementation of {@link IServiceLocator}. Provides 
 * concrete {@link IServiceLocator} implementations with reusable JNDI service 
 * lookup and Services caching.
 * <br/><br/>
 * Note that all initial properties are loaded from the <code>jndi.properties
 * </code> file
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 7, 2009 2:35:20 PM $
 */
public final
class ServiceLocator
implements IServiceLocator, ISingleton
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of {@link IServiceLocator}
     * 
     * @return Singleton instance of {@link IServiceLocator}
     */
    public static ServiceLocator
    getInstance()
    {
        return INSTANCE;
    }    

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <S> S 
    locate(Class<S> aServiceClass, String aName) 
    throws IllegalArgumentException, ServiceLocatorException
    {
        Validate.notNull(aServiceClass, "'aServiceClass' must not be null");
        Validate.notNull(aName, "'aName' must not be null");
        
        S myService = null;

        if (theServiceCache.containsKey(aName))
        {
            myService = (S) theServiceCache.get(aName);                
        }
        else
        {
            try
            {
                myService = (S) theContext.lookup(aName);
                
                assert myService != null : "'myService' must not be null";
                
                // save in cache
                theServiceCache.put(aName, myService);
            }
            catch (NamingException anException)
            {
                // propagate the exception
                throw new ServiceLocatorException(anException);
            }            
        }
        
        if (!(aServiceClass.isAssignableFrom((myService.getClass()))))
        {
            String myMessage = MessageFormat.format(MISMATCH_PATTERN, 
                aServiceClass.getSimpleName(), myService.getClass().
                    getSimpleName());
            
            throw new ServiceLocatorException(myMessage);
        }        
        
        return myService;
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <S> S 
    locate(String aName) 
    throws IllegalArgumentException, ServiceLocatorException
    {                
        try
        {
            return (S) locate(Object.class, aName);
        }
        catch (ClassCastException anException)
        {
            // propagate the exception
            throw new ServiceLocatorException(anException);
        }                            
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    shutdown()
    throws ServiceLocatorException
    {
        try
        {
            theContext.close();
        }
        catch (NamingException anException)
        {
            // propagate the exception
            throw new ServiceLocatorException(anException);
        }
    }

    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    private 
    ServiceLocator()
    {
        try
        {
            theContext = new InitialContext();            
        }
        catch (NamingException anException)
        {
            // propagate the exception
            throw new ServiceLocatorException(anException);
        }        
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final ServiceLocator INSTANCE = new ServiceLocator();
    private final Context theContext;
    private static final String MISMATCH_PATTERN = "Service type mismatch: " +
        "expected=''{0}'', found=''{1}''";
    private final Map<String, Object> theServiceCache = 
        new HashMap<String, Object>();
}

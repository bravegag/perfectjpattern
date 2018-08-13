//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ExactMatchAdaptingStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.adapter;

import java.lang.reflect.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.structural.adapter.*;

/**
 * Concrete implementation of {@link IAdaptingStrategy}. Implements the exact 
 * matching strategy. The Adaptee and Target methods (name and parameters) must 
 * match or at least the Adaptee methods must be a subset of Target methods.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jan 28, 2009 2:20:39 PM $
 */
public  
class ExactMatchAdaptingStrategy
implements IAdaptingStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    validate(Class<?> aTargetClass, Object anAdaptee, Object anAdapter)
    throws IllegalArgumentException
    {
        Validate.notNull(aTargetClass, "'aTargetClass' must not be null");
        Validate.notNull(anAdaptee, "'anAdaptee' must not be null");
        Validate.notNull(anAdapter, "'anAdapter' must not be null");
        
        Class<?> myAdapteeClass = anAdaptee.getClass();
        
        Set<String> myTargetMethods = new HashSet<String>();
        
        // match all Adaptee publicly defined methods
        for (Method myTargetMethod : aTargetClass.getMethods())
        {
            String myTargetMethodName = myTargetMethod.getName();
            
            try 
            {
                // ensure the existance of Method in anAdaptee
                Method myAdapteeMethod = myAdapteeClass.getMethod(
                    myTargetMethodName, myTargetMethod.getParameterTypes());
                
                assert myAdapteeMethod != null : 
                    "'myAdapteeMethod' must not be null";                
            }
            catch (NoSuchMethodException anException)
            {
                // not found, we can still look in the Adapter
                myTargetMethods.add(myTargetMethodName);                
            }
        }
        
        if (myTargetMethods.size() > 0)
        {
            validate(aTargetClass, anAdapter, myTargetMethods);
            
            if (myTargetMethods.size() > 0)
            {
                handleMismatch(myTargetMethods);
            }
        }                
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public Method 
    resolve(Class<?> aTargetClass, Object anAdaptee, Object anAdapter, 
        Method aTargetMethod)
    throws IllegalArgumentException
    {
        Validate.notNull(aTargetClass, "'aTargetClass' must not be null");
        Validate.notNull(anAdaptee, "'anAdaptee' must not be null");
        Validate.notNull(anAdapter, "'anAdapter' must not be null");
        Validate.notNull(aTargetMethod, "'aTargetMethod' must not be null");

        Method myMethod = null;
        try
        {
            // first try with the Adapter
            myMethod = resolve(anAdapter, aTargetMethod);            
        }
        catch (IllegalArgumentException anException)
        {
            // retry by looking into the Adaptee
            myMethod = resolve(anAdaptee, aTargetMethod);
        }
        
        return myMethod;
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    public void
    handleMismatch(Set<String> aTargetMethods)
    {
        throw new IllegalArgumentException("Adaptee does not " +
            "implement all target methods: '" + Arrays.toString(
                aTargetMethods.toArray()) + "'");        
    }
    
    //------------------------------------------------------------------------
    /**
     * Removes from aTargetMethods all methods found in anInterface that are
     * actually implemented by anImplementor
     * 
     * @param anInterface The target interface
     * @param anImplementor The underlying implementor
     * @param aTargetMethods The methods to search for
     */
    protected final void 
    validate(Class<?> anInterface, Object anImplementor, 
        Set<String> aTargetMethods)
    { 
        assert anInterface != null : "'anInterface' must not be null";
        assert anImplementor != null : "'anImplementor' must not be null";
        assert aTargetMethods != null : "'aTargetMethods' must not be null";
        
        for (Method myAdapterMethod : anImplementor.getClass().getMethods())
        {
            String myAdapterMethodName = myAdapterMethod.getName();
            if (aTargetMethods.contains(myAdapterMethodName))
            {
                Class<?>[] myParameterTypes = myAdapterMethod.
                    getParameterTypes();
                
                try
                {
                    anInterface.getMethod(myAdapterMethodName, 
                        myParameterTypes);
                    
                    aTargetMethods.remove(myAdapterMethodName);
                }
                // CHECKSTYLE:OFF
                catch (Exception anException)
                // CHECKSTYLE:ON
                {
                    // not found
                }
            }                    
        }
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns the found matching {@link Method} if exists, null or 
     * {@link IllegalArgumentException} otherwise
     * 
     * @param anObject
     * @param aMethodPrototype
     * @param aMethodName
     * @return found matching {@link Method} if exists, null or 
     *         {@link IllegalArgumentException} otherwise
     * @throws IllegalArgumentException
     */
    protected final Method
    resolve(Object anObject, Method aMethodPrototype, String aMethodName)
    throws IllegalArgumentException
    {   
        assert anObject != null : "'anObject' must not be null";
        assert aMethodPrototype != null : "'aMethod' must not be null";
        
        Method myMethod = null;
        
        try        
        {
            Class<?> myClass = anObject.getClass();

            // probe whether the Method exists in the Object
            myMethod = myClass.getMethod(aMethodName, aMethodPrototype.
                getParameterTypes());
            
            assert myMethod != null : "'myMethod' must not be null";
            
            return myMethod;
        }
        catch (NoSuchMethodException anException)
        {
            throw new IllegalArgumentException("Method mismatch between " +
                "Target and Adaptee/Adapter '" + aMethodPrototype.getName() + 
                    "'");
        }
    }

    //------------------------------------------------------------------------
    /**
     * Returns the found matching {@link Method} if exists, null or 
     * {@link IllegalArgumentException} otherwise
     * 
     * @param anObject
     * @param aMethodPrototype
     * @return found matching {@link Method} if exists, null or 
     *         {@link IllegalArgumentException} otherwise
     * @throws IllegalArgumentException
     */
    protected final Method
    resolve(Object anObject, Method aMethodPrototype)
    throws IllegalArgumentException
    {
        String myMethodName = aMethodPrototype.getName();
        
        return resolve(anObject, aMethodPrototype, myMethodName);
    }
}

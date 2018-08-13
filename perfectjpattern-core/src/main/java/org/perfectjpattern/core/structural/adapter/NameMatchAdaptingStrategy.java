//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// NameMatchAdaptingStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Concrete implementation of {@link IAdaptingStrategy}. Implements the name
 * matching strategy. The <code>Adaptee</code> and <code>Target</code> methods 
 * are matched by name. The method correspondence is provided by the user as 
 * a {@link Map} of <code>Target</code> method names keyed by <code>
 * Adaptee</code> method names. The mapping does not have to be exhaustive, 
 * those methods not explicitly mapped will be resolved against the Adaptee 
 * and Adapter using exact match.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jan 28, 2009 3:42:25 PM $
 */
public final 
class NameMatchAdaptingStrategy
extends ExactMatchAdaptingStrategy
implements IAdaptingStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link NameMatchAdaptingStrategy} from a map that defines
     * the correspondence between Target and Adaptee i.e. Adaptee method names
     * keyed by Target method names.
     * 
     * @param aMethodsMapping Map of Target method names keyed by Target 
     *        method names
     * @throws IllegalArgumentException 'aMethodsMap' must not be null
     */
    public 
    NameMatchAdaptingStrategy(Map<String, String> aMethodsMapping)
    throws IllegalArgumentException
    {
        Validate.notNull(aMethodsMapping, "'aMethodsMap' must not be null");
        
        theMethodsMapping = aMethodsMapping;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public void 
    validate(Class<?> aTargetClass, Object anAdaptee, Object anAdapter)
    throws IllegalArgumentException
    {
        Validate.notNull(aTargetClass, "'aTargetClass' must not be null");
        Validate.notNull(anAdaptee, "'anAdaptee' must not be null");
        Validate.notNull(anAdapter, "'anAdapter' must not be null");
        
        Class<?> myAdapteeClass = anAdaptee.getClass();
        
        // check that all aMethodsMap keys exist in the Target interface and 
        // that they match the corresponding definitions in the Adaptee 
        // interface                
        Set<String> myTargetMethods = new HashSet<String>();
        for (Method myTargetMethod : aTargetClass.getMethods())
        {
            myTargetMethods.add(myTargetMethod.getName());
        }
        
        for (Map.Entry<String, String> myEntry : theMethodsMapping.entrySet())
        {
            String myTargetMethodName = myEntry.getKey();
            String myAdapteeMethodName = myEntry.getValue();
            
            boolean myFound = false;
            for (Method myAdapteeMethod : myAdapteeClass.getMethods())
            {
                if (myAdapteeMethodName.equals(myAdapteeMethod.getName()))
                {
                    Class<?>[] myParameterTypes = myAdapteeMethod.
                        getParameterTypes();
                    
                    try
                    {
                        aTargetClass.getMethod(myTargetMethodName, 
                            myParameterTypes);
                        
                        myTargetMethods.remove(myTargetMethodName);
                        
                        myFound = true;
                        
                        break;
                    }
                    // CHECKSTYLE:OFF
                    catch (NoSuchMethodException anException)
                    // CHECKSTYLE:ON
                    {
                        // not found
                    }
                }                    
            }
            
            if (!myFound)
            {
                throw new IllegalArgumentException("Method mismatch between " +
                    "Target and Adaptee '" + myTargetMethodName + "'");
            }
        }        
        
        // check that if the mapping is not exhaustive then the adapter 
        // implements the remaining methods 
        if (myTargetMethods.size() > 0)
        {
            validate(aTargetClass, anAdaptee, myTargetMethods);
            validate(aTargetClass, anAdapter, myTargetMethods);
            
            if (myTargetMethods.size() > 0)
            {
                throw new IllegalArgumentException("Adaptee does not " +
                    "implement all target methods: '" + Arrays.toString(
                        myTargetMethods.toArray()) + "'");
            }
        }
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public Method 
    resolve(Class<?> aTargetClass, Object anAdaptee, Object anAdapter, 
        Method aTargetMethod)
    throws IllegalArgumentException
    {
        Validate.notNull(aTargetClass, "'aTargetClass' must not be null");
        Validate.notNull(anAdaptee, "'anAdaptee' must not be null");
        Validate.notNull(anAdapter, "'anAdapter' must not be null");
        Validate.notNull(aTargetMethod, "'aTargetMethod' must not be null");
        
        String myAdapteeMethodName = theMethodsMapping.get(aTargetMethod.
            getName());
        
        Method myResultMethod = null; 
        if (myAdapteeMethodName != null)
        {
            myResultMethod = super.resolve(anAdaptee, aTargetMethod, 
                myAdapteeMethodName);
        }
        else
        {
            myResultMethod = super.resolve(aTargetClass, anAdaptee, anAdapter, 
                aTargetMethod);
        }
        
        return myResultMethod;
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Map<String, String> theMethodsMapping;
}

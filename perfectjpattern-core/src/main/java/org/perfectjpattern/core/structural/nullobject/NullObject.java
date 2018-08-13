//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// NullObject.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.nullobject;

import java.lang.reflect.*;
import java.util.*;

import org.apache.commons.lang3.*;

/**
 * Componentized implementation of the 
 * <a href="http://en.wikipedia.org/wiki/Null_Object_pattern">Null Object pattern</a>.
 * The <code>NullObject</code> is a structural pattern that offers a null default 
 * behavior and minimizes the chance of errors arising from returning and passing
 * <code>null</code> values.
 * 
 * @param <C> Component element type.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 18, 2007 10:01:11 PM $
 */
public
class NullObject<C>
implements InvocationHandler
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Creates a NullObject&lt;C&gt; from the Component type interface.
     * 
     * @param anInterface The Component interface to create Composite types of.
     * @throws IllegalArgumentException 'anInterface' must not be null.
     * @throws IllegalArgumentException 'anInterface' must be an interface type.
     */
    @SuppressWarnings("unchecked")
    public 
    NullObject(Class<C> anInterface)
    throws IllegalArgumentException
    {
        Validate.notNull(anInterface, "'anInterface' must not be null");
        Validate.isTrue(anInterface.isInterface(), 
            "'anInterface' must be an interface type.");
        
        final ClassLoader myClassLoader = anInterface.getClassLoader(); 
        theComponent = (C) Proxy.newProxyInstance(myClassLoader, new Class[] {
            anInterface }, this);        
    }

    //------------------------------------------------------------------------
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object 
    invoke(Object aProxy, Method aMethod, Object[] anArguments)
    throws Throwable
    {
        Class<?> clazz = aMethod.getReturnType();
        
        Object result = null;
        if (byte.class.equals(clazz))
        {
            result = 0;
            
        } else
        if (short.class.equals(clazz)) 
        {
            result = 0;

        } else
        if (int.class.equals(clazz)) 
        {
            result = 0;
        
        } else
        if (long.class.equals(clazz)) 
        {
            result = 0;
        
        } else 
        if (float.class.equals(clazz)) 
        {
            result = 0.0f;
            
        } else 
        if (double.class.equals(clazz)) 
        {
            result = 0.0d;
        
        } else 
        if (char.class.equals(clazz)) 
        {
            result = '\u0000';
            
        } else 
        if (boolean.class.equals(clazz)) 
        {
            result = false;
        
        } else 
        if (String.class.equals(clazz)) 
        {
            result = "";
            
        } else 
        if (List.class.equals(clazz)) 
        {
            result = Collections.EMPTY_LIST;
            
        } else 
        if (Map.class.equals(clazz)) 
        {
            result = Collections.EMPTY_MAP;
            
        } else 
        if (Set.class.equals(clazz)) 
        {
            result = Collections.EMPTY_SET;
            
        } else 
        if (Array.class.equals(clazz)) 
        {
            result = Array.newInstance(clazz.getComponentType(), 0);
            
        } else {
            if (clazz.isInterface()) 
            {
                result = new NullObject(clazz).getComponent();
            }            
        }
        
        return result;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public C 
    getComponent()
    {
        return theComponent;
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Reference to the Component type implemented by this Composite instance.
     */
    private final C theComponent;
}    

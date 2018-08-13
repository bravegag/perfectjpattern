//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractSurrogate.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural;

import java.lang.reflect.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.structural.*;

/**
 * Concrete implementation of {@link ISurrogate} that groups common 
 * functionality for single instance surrogates.
 *
 * @param <C> <code>Component</code> or wrapper element type
 * @param <U> <code>Underlying</code> wrapped element type
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 5, 2008 4:04:47 PM $
 */
public abstract 
class AbstractSurrogate<C, U>
implements ISurrogate<C>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Creates an AbstractSurrogate&lt;C&gt; given a <code>Component</code> 
     * interface type and an instance of the underlying wrapped object.
     * 
     * @param aSurrogateClass The desired <code>Surrogate</code> interface type
     * @param anUnderlying <code>Underlying</code> instance to surrogate
     * @throws IllegalArgumentException 'anInterface' must not be null
     * @throws IllegalArgumentException 'anInterface' must be an interface type
     * @throws IllegalArgumentException 'aComponent' must not be null
     */
    @SuppressWarnings("unchecked")
    public 
    AbstractSurrogate(Class<C> aSurrogateClass, U anUnderlying)
    throws IllegalArgumentException
    {
        Validate.notNull(aSurrogateClass, "'anInterface' must not be null");
        Validate.isTrue(aSurrogateClass.isInterface(), 
            "'aSurrogateClass' must be an interface type");
        Validate.notNull(anUnderlying, "'anUnderlying' must not be null");
        
        theComponentClass = aSurrogateClass;
        theUnderlying = anUnderlying;
        
        final ClassLoader myClassLoader = aSurrogateClass.getClassLoader();
        theComponent = (C) Proxy.newProxyInstance(myClassLoader, new Class[] {
            aSurrogateClass }, this);        
    }        

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final C 
    getComponent()
    {
        return theComponent;
    }
    
    //------------------------------------------------------------------------
    public final Object 
    invoke(Object aProxy, Method aMethod, Object[] anArguments)
    throws Throwable
    {   
        Object myResult = null;
        
        Method myMethod = null;
        if (theLookup.containsKey(aMethod))
        {
            myMethod = theLookup.get(aMethod);
        }
        else
        {
            try
            {
                // check whether this method is implemented by the 
                // surrogate i.e. this
                myMethod = getClass().getMethod(aMethod.getName(), aMethod.
                    getParameterTypes());
                theLookup.put(aMethod, myMethod);
            }
            catch (NoSuchMethodException anException)
            {
                // pass it on to the underlying then
            }
        }
        
        // if the method is decorated then pass control to the surrogate
        // otherwise forward to the underlying to execute it
        if (myMethod != null)
        {
            myResult = myMethod.invoke(this, anArguments);
        }
        else
        {
            // first make sure that the underlying implements such method
            try
            {
                // invoke underlying
                myResult = invokeUnderlying(aMethod, anArguments);

                // cache the association
                theLookup.put(aMethod, myMethod);
            }
            catch (NoSuchMethodException anException)
            {
                throw new IllegalArgumentException("Underlying component " +
                    "does not implement '" + aMethod.getName() + "'");
            }                        
        }
        
        return myResult;
    }        
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public final boolean 
    equals(Object anObject)
    {
        boolean myResult = false;
        
        // FindBugs successfully identifies this equals implementation as 
        // unusual, the reason is that a Surrogate impersonates the real 
        // instance so this equals implementation is an attempt to provide 
        // the same identity view of the Surrogate
        if (anObject instanceof Proxy)
        {
            Proxy myProxy = (Proxy) anObject;
            
            myResult = myProxy.equals(theUnderlying);
        }
        else
        {
            myResult = theUnderlying.equals(anObject);
        }
        
        return myResult;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public final int 
    hashCode()
    {        
        return theUnderlying.hashCode();
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public final String
    toString()
    {        
        return theUnderlying.toString();
    }    

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    protected final U
    getUnderlying()
    {
        return theUnderlying;
    }        
    
    //------------------------------------------------------------------------
    /**
     * Returns the result of the Method invocation. This method has two 
     * main purposes:
     * 
     * <ul>
     *  <li>Provides facility to invoke a method on the actual Underlying</li>
     *  <li>Concrete surrogate implementations that override this method have 
     *  a single point to control access to the actual Underlying method 
     *  invokation.</li>
     * </ul>
     * 
     * @param aMethod Method to invoke
     * @param anArguments Array of input arguments for the Method to invoke
     * @return result of the Method invocation
     * @throws Throwable
     */
    protected Object
    invokeUnderlying(Method aMethod, Object[] anArguments)
    throws Throwable
    {
        return aMethod.invoke(theUnderlying, anArguments);
    }    
    
    //------------------------------------------------------------------------
    /**
     * Returns the Component class
     *
     * @return the Component class
     */
    protected final Class<C> 
    getComponentClass()
    {
        return theComponentClass;
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * <code>Component</code> instance
     */
    private final C theComponent;    

    //------------------------------------------------------------------------
    /**
     * <code>Component</code> class type 
     */
    private final Class<C> theComponentClass;    

    /**
     * Reference to the actual <code>Underlying</code> type wrapped by this 
     * surrogate instance.
     */
    private final U theUnderlying;  

    /**
     * <code>Map&lt;Method, Method&gt;<code> that contains all methods  
     * implemented by the <code>Component</code> type
     */
    private final Map<Method, Method> theLookup = new HashMap<Method, Method>();
}

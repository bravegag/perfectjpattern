//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Composite.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.composite;

import java.lang.reflect.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.structural.composite.*;


/**
 * Componentized implementation of <code>IComposite</code> interface. Creates
 * composition of strongly type interfaces giving the user the dual view of this
 * instance:<br/>
 * <ul>
 * <li><code>IComposite&lt;E&gt;</code> where the user may manipulate the 
 * composition e.g. add, remove, sort <code>Component</code> elements, etc.</li>
 * <li><code>&lt;E&gt;</code> type view of this composite 
 * {@link #getComponent()} so all <code>Component</code> operations will be 
 * transparently applied to the entire composition</li>
 * </ul>
 * <br/>
 * Composite type also provides an overridable method {@link #aggregate(Method, 
 * Object[])} that defines how to aggregate multiple result for a function 
 * call i.e. it defines how to aggregate the results providuced from a function
 * call for the complete composition.
 * <br/><br/>
 * @see IComposite
 * 
 * @param <C> Component element type.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 18, 2007 10:01:11 PM $
 */
public
class Composite<C>
extends ArrayList<C>
implements IComposite<C>, InvocationHandler
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Creates a Composite&lt;E&gt; from the Component type interface.
     * 
     * @param anInterface The Component interface to create Composite types of.
     * @throws IllegalArgumentException 'anInterface' must not be null.
     * @throws IllegalArgumentException 'anInterface' must be an interface type.
     */
    @SuppressWarnings("unchecked")
    public 
    Composite(Class<C> anInterface)
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
    public Object 
    invoke(Object aProxy, Method aMethod, Object[] anArguments)
    throws Throwable
    {        
        // nothing to do?
        if (size() == 0)
        {
            return null;
        }

        Object[] myResults = new Object[this.size()];
        
        // execute target method over the collection of Elements
        int i = 0;
        for (C myElement : this)
        {
            myResults[i++] = aMethod.invoke(myElement, anArguments);
        }
        
        return aggregate(aMethod, myResults);
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
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean
    addAll(C ... anElements)
    throws IllegalArgumentException
    {
        Validate.notNull(anElements, "'anElements' must not be null");
        Validate.notEmpty(anElements, "'anElements' must not be empty.");
        Validate.noNullElements(anElements, 
            "'anElements' must not contain null Elements.");
        
        return super.addAll(Arrays.asList(anElements));
    }    
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Returns aggregation of all results returned by each of the Component in 
     * this composition. Default implementation just returns null but provides
     * a means to define specialized ways to aggregate results.
     * 
     * @param aMethod Method that was called, relevant to discern and aggregate
     *        differently for different methods. 
     * @param aResults Array of all results produced by all Components.
     * @return aggregation of all results returned by each of the Component in 
     *         this composition.
     */
    protected Object 
    aggregate(Method aMethod, Object[] aResults)
    {
        Object myResult = null;
        
        return myResult;
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Reference to the Component type implemented by this Composite instance.
     */
    private final C theComponent;
    
    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;    
}    

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Adapter.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.core.structural.*;

/**
 * Concrete implementation of {@link IAdapter}
 * 
 * @param <T> <code>Target</code> element type
 * @param <A> <code>Adaptee</code> element type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jan 28, 2009 1:43:35 PM $
 */
public 
class Adapter<T, A>
extends AbstractSurrogate<T, A>
implements IAdapter<T, A>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs an {@link Adapter} taking as input the <code>Target</code> 
     * interface type, an <code>Adaptee</code> instance and the 
     * {@link IAdaptingStrategy} for matching those interfaces.
     * 
     * @param aTargetInterface Target interface class type 
     * @param anAdaptee Adaptee instance
     * @param anAdaptingStrategy Strategy for matching the Target and Adaptee 
     *        interfaces
     * @throws IllegalArgumentException 'aTargetInterface' must not be null
     * @throws IllegalArgumentException 'anAdaptee' must not be null
     * @throws IllegalArgumentException 'anAdaptingStrategy' must not be null
     */
    public 
    Adapter(Class<T> aTargetInterface, A anAdaptee, IAdaptingStrategy 
        anAdaptingStrategy)
    throws IllegalArgumentException
    {
        super(aTargetInterface, anAdaptee);
        
        setAdaptingStrategy(anAdaptingStrategy);
    }

    //------------------------------------------------------------------------
    /**
     * Constructs an {@link Adapter} instance having the target interface, the
     * adaptee instance and assigning the default adapting strategy
     * 
     * @param aTargetInterface Target interface type 
     * @param anAdaptee Adaptee instance
     * @throws IllegalArgumentException 'aTargetInterface' must not be null
     * @throws IllegalArgumentException 'anAdaptee' must not be null
     */
    public 
    Adapter(Class<T> anInterface, A anAdaptee)
    throws IllegalArgumentException
    {
        this(anInterface, anAdaptee, DEFAULT_ADAPTING_STRATEGY);
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public A 
    getAdaptee()
    {
        return super.getUnderlying();
    }    

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public T
    getTarget()
    {
        return super.getComponent();
    }    

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final void 
    setAdaptingStrategy(IAdaptingStrategy anAdaptingStrategy)
    throws IllegalArgumentException
    {
        Validate.notNull(anAdaptingStrategy,    
            "'anAdaptingStrategy' must not be null");
        
        theAdaptingStrategy = anAdaptingStrategy;
        
        Class<T> myTarget = getComponentClass();
        A myAdaptee = getUnderlying();
        
        Object myAdapter = this;        
        theAdaptingStrategy.validate(myTarget, myAdaptee, myAdapter);        
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected Object 
    invokeUnderlying(Method aMethod, Object[] anArguments)
    throws Throwable
    {
        Class<T> myTarget = getComponentClass();
        A myAdaptee = super.getUnderlying();
        
        Object myAdapter = this;        
        Method myTargetMethod = theAdaptingStrategy.resolve(myTarget, 
            myAdaptee, myAdapter, aMethod);
        
        Object myResult = null;
        if (myTargetMethod != null)
        {
            myResult = myTargetMethod.invoke(myAdaptee, anArguments);
        }
        
        return myResult;
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Reference to {@link IAdaptingStrategy} implementation that defines how
     * the Adapter should match the Adaptee interface
     */
    private IAdaptingStrategy theAdaptingStrategy;
    
    /**
     * Default concrete {@link IAdaptingStrategy} implementation
     */
    private static final IAdaptingStrategy DEFAULT_ADAPTING_STRATEGY = 
        new ExactMatchAdaptingStrategy();
}

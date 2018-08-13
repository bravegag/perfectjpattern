//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// SynchronizedDecorator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.proxy;

import java.lang.reflect.*;
import java.util.concurrent.locks.*;

/**
 * Concrete componentized implementation of <code>IDecorator&lt;C&gt;</code> 
 * that provides synchronized protection to any class type.
 * 
 * @param <C> Decorated Component type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 25, 2007 4:11:47 PM $
 */
public 
class SynchronizedProxy<C>
extends AbstractProxy<C>
{
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Creates a <code>SynchronizedDecorator&lt;E&gt;</code> from a Component 
     * interface type and instance.
     * 
     * @param anInterface The Component interface type.
     * @param aComponent The Component instance to Decorate.
     * @throws IllegalArgumentException 'anInterface' must not be null.
     * @throws IllegalArgumentException 'anInterface' must be an interface type.
     * @throws IllegalArgumentException 'aComponent' must not be null.
     */
    public 
    SynchronizedProxy(Class<C> anInterface, C aComponent)
    throws IllegalArgumentException
    {
        super(anInterface, aComponent);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected Object 
    invokeUnderlying(Method anMethod, Object[] anArguments)
    throws Throwable
    {
        try
        {
            theLock.lock();
            
            return super.invokeUnderlying(anMethod, anArguments);
        }
        finally
        {
            theLock.unlock();
        }
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Lock theLock = new ReentrantLock();
}

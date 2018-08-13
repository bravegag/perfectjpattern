//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Decorator.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.structural.proxy.*;
import org.perfectjpattern.core.structural.*;


/**
 * Componentized implementation of <code>IProxy</code> interface. Provides
 * special access to the underlying Subject.<br/>
 * 
 * @param <S> <code>Subject</code> element type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 25, 2007 3:15:23 PM $
 */
public abstract
class AbstractProxy<S>
extends AbstractSurrogate<S, S>
implements IProxy<S>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Creates a <code>Proxy&lt;E&gt;</code> from a Component interface 
     * type and instance.
     * 
     * @param anInterface The Component interface type.
     * @param aComponent The Component instance to Decorate.
     * @throws IllegalArgumentException 'anInterface' must not be null.
     * @throws IllegalArgumentException 'anInterface' must be an interface type.
     * @throws IllegalArgumentException 'aComponent' must not be null.
     */
    public 
    AbstractProxy(Class<S> anInterface, S aComponent)
    throws IllegalArgumentException
    {
        super(anInterface, aComponent);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final S
    getSubject()
    {
        return getComponent();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final S
    getRealSubject()
    {
        return getUnderlying();
    }
}

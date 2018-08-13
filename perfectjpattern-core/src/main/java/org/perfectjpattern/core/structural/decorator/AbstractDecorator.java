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
package org.perfectjpattern.core.structural.decorator;

import org.perfectjpattern.core.api.structural.decorator.*;
import org.perfectjpattern.core.structural.*;


/**
 * Abstract reusable implementation of {@link IDecorator}.
 * <br/>
 * 
 * @param <C> <code>Component</code> element type. Interface type 
 *        being decorated. 
 * @param <D> <code>Decorator</code> element type. This type covers the 
 *        following two use-cases: 
 *        <ul>
 *        <li>If the Decorator does not offer added functionality then 
 *        this type is the same as &lt;C&gt;</li>
 *        <li>Otherwise &lt;D&gt; will be a subclass of &lt;C&gt; that 
 *        is, it will comply to &lt;C&gt; and offer extra functionality</li>
 *        </ul>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 25, 2007 3:15:23 PM $
 */
public abstract
class AbstractDecorator<C, D extends C>
extends AbstractSurrogate<D, C>
implements IDecorator<C, D>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs a Decorator from the class type of the Decorator <code>
     * Class&lt;D&gt;</code> and the Component &lt;C&gt; instance to decorate.
     * <br/></br>
     * Depending on whether the user wants to add functionality to &lt;C&gt; 
     * or not, the type &lt;D&gt; will be a subtype of &lt;C&gt; or be the same 
     * respectively.
     * <br/><br/>
     * @param aDecoratorType Class type of the <code>Decorator</code> interface
     * @param aComponent The Component instance to Decorate
     * @throws IllegalArgumentException 'anInterface' must not be null
     * @throws IllegalArgumentException 'anInterface' must be an interface type
     * @throws IllegalArgumentException 'aComponent' must not be null
     */
    public 
    AbstractDecorator(Class<D> aDecoratorType, C aComponent)
    {
        super(aDecoratorType, aComponent);
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final C
    getDecorated()
    {
        return super.getUnderlying();
    }
}

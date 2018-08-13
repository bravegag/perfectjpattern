//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// Delegator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.extras.delegate;

import java.lang.reflect.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.extras.delegate.*;


/**
 * Implementation of <code>IDelegator</code> interface for creating
 * Delegate instances based user-defined interface types.
 * <br/><br/>
 * <b>Notes</b>: Base source code implemented by Steve Lewis and Wilhelm
 * Fitzpatrick and adapted to fit PerfectJPattern componentization
 * criteria and code conventions.
 *
 * @param <I> Interface type built by this <code>Delegator</code> instance.
 *
 * @author <a href="mailto:smlewis@lordjoe.com">Steve Lewis</a>
 * @author <a href="mailto:wilhelmf@agileinformatics.com">Wilhelm
 * Fitzpatrick</a>
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 25, 2007 7:20:28 AM $
 */
@SuppressWarnings("unchecked")
public
class Delegator<I>
extends AbstractDelegator<I>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Creates a <code>Delegator&lt;E&gt;</code> from the type interface.
     *
     * @param anInterface The interface to build references from.
     * @throws IllegalArgumentException 'anInterface' must not be null.
     * @throws IllegalArgumentException 'anInterface' must be an interface type.
     */
    public
    Delegator(Class<I> anInterface)
    {
        super(anInterface);

        Validate.notNull(anInterface, "'anInterface' must not be null");
        Validate.isTrue(anInterface.isInterface(),
            "'anInterface' must be an interface type.");

        theInterface = anInterface;
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public I
    build(Class<?> aTarget, String aMethodName)
    throws IllegalArgumentException, UnsupportedOperationException
    {
        Validate.notNull(aTarget, "'aTarget' must not be null");
        Validate.notNull(aMethodName, "'aMethodName' must not be null");

        DelegateProxy myProxy = new DelegateProxy(null, aTarget, aMethodName,
            this);

        Class<?> myInterface = theInterface;

        // build dynamic proxy
        Class<?>[] myInterfaces = {myInterface, IDelegate.class };
        I myDelegate = null;
        try
        {
            // try with the target
            myDelegate = (I) Proxy.newProxyInstance(aTarget.getClassLoader(),
                myInterfaces, myProxy);
        }
        catch (IllegalArgumentException anException)
        {
            // retry with the Interface class
            myDelegate = (I) Proxy.newProxyInstance(theInterface.
                getClassLoader(), myInterfaces, myProxy);
        }

        return myDelegate;
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public I
    build(Object aTarget, String aMethodName)
    throws IllegalArgumentException, UnsupportedOperationException
    {
        Validate.notNull(aTarget, "'aTarget' must not be null");
        Validate.notNull(aMethodName, "'aMethodName' must not be null");

        DelegateProxy myProxy = new DelegateProxy(aTarget,
            aTarget.getClass(), aMethodName, this);

        Class<?> myInterface = theInterface;

        // build dynamic proxy
        Class<?>[] myInterfaces = {myInterface, IDelegate.class };

        IDelegate myDelegate = null;
        try
        {
            // try with the target
            myDelegate = (IDelegate) java.lang.reflect.Proxy.newProxyInstance(
                aTarget.getClass().getClassLoader(), myInterfaces, myProxy);
        }
        catch (IllegalArgumentException anException)
        {
            // retry with the Interface class
            myDelegate = (IDelegate) java.lang.reflect.Proxy.newProxyInstance(
                theInterface.getClassLoader(), myInterfaces, myProxy);
        }

        return (I) myDelegate;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Class<?> theInterface;
}

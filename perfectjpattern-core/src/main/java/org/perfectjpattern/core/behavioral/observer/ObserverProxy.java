//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// Observer.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.observer;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.observer.*;
import org.perfectjpattern.core.api.extras.delegate.*;
import org.perfectjpattern.core.extras.delegate.*;


/**
 * Implementation of <code>IObserver</code> interface that acts as a Proxy to
 * any plain non-Observer class type. Makes possible to have any given class
 * type to Observe more than one type of Subject without <code>update</code>
 * method name and signature clash.
 *
 * @see IObserver
 *
 * @param <E> Type of event data that this <code>IObserver</code> subscribes
 * to.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jul 1, 2007 5:20:12 AM $
 */
public final
class ObserverProxy<E>
implements IObserver<E>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs <code>ObserverProxy</code> from a target instance
     *
     * @param anObject Target Object that will handle the notification message
     * @param aMethodName Target method implemented in anObject that will
     *        handle notification messages
     * @param aParameters Type of parameters passed in notification events e.g.
     *        changed state etc
     */
    public
    ObserverProxy(Object anObject, String aMethodName, Class<?>... aParameters)
    {
        Validate.notNull(anObject, "'anObject' must not be null");
        Validate.notNull(aMethodName, "'aMethodName' must not be null");
        Validate.isTrue(aMethodName.length() > 0,
            "'aMethodName' must not be empty");
        Validate.notNull(aParameters, "'aParameters' must not be null");

        // assumes that return type is void
        Class<?> myReturnType = void.class;

        // create the delegate interface based on the DynamicDelegator
        DynamicDelegator myDelegator = new DynamicDelegator(myReturnType,
            aParameters);

        theDelegate = myDelegator.build(anObject, aMethodName);
    }

    //------------------------------------------------------------------------
    /**
     * Constructs <code>ObserverProxy</code> from a target static class
     *
     * @param aClass Target helper static class that will handle the
     *        notification message
     * @param aMethodName Target static method implemented in aClass that will
     *        handle notification messages
     * @param aParameters Type of parameters passed in notification events e.g.
     *        changed state etc
     */
    public
    ObserverProxy(Class<?> aClass, String aMethodName, Class<?>... aParameters)
    {
        Validate.notNull(aClass, "'aClass' must not be null");
        Validate.notNull(aMethodName, "'aMethodName' must not be null");
        Validate.isTrue(aMethodName.length() > 0,
            "'aMethodName' must not be empty");
        Validate.notNull(aParameters, "'aParameters' must not be null");

        // assumes that return type is void
        Class<?> myReturnType = void.class;

        // create the delegate interface based on the DynamicDelegator
        DynamicDelegator myDelegator = new DynamicDelegator(myReturnType,
            aParameters);

        theDelegate = myDelegator.build(aClass, aMethodName);
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void
    update(E anEventData)
    {
        theDelegate.invoke(anEventData);
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final IDelegate theDelegate;
}

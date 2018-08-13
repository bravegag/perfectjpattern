//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// Subject.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.observer.*;
import org.slf4j.*;

/**
 * Base core implementation of {@link ISubject} interface.
 * <br/>
 *
 * @see ISubject
 *
 * @param <E> Type of event data this <code>ISubject</code>
 *            notifies with.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 18, 2007 9:06:39 PM $
 */
public
class Subject<E>
implements ISubject<E>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void
    attach(IObserver<E>... anObservers)
    throws IllegalArgumentException
    {
        Validate.notNull(anObservers, "'anObservers' must not be null");
        Validate.noNullElements(anObservers, "'anObservers' must not be null");

        theLogger.debug("Attaching Observer(s) '" + Arrays.toString(
            anObservers) + "'");

        theObservers.addAll(Arrays.asList(anObservers));
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void
    clear()
    {
        theObservers.clear();
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void
    detach(IObserver<E>... anObservers)
    throws IllegalArgumentException
    {
        Validate.notNull(anObservers, "'anObservers' must not be null");
        Validate.noNullElements(anObservers, "'anObservers' must not be null");

        theLogger.debug("Detaching Observer(s) '" + Arrays.toString(
            anObservers) + "'");

        theObservers.removeAll(Arrays.asList(anObservers));
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void
    notifyObservers(E anEventData)
    {
        Validate.notNull(anEventData, "'anEventData' must not be null");

        List<IObserver<E>> myFaultyObservers = new LinkedList<IObserver<E>>();
        for (IObserver<E> myObserver : theObservers)
        {
            try
            {
                myObserver.update(anEventData);
            }
            //CHECKSTYLE:OFF
            catch (Throwable anException)
            //CHECKSTYLE:ON
            {
                // faulty Observer instances are automatically detached
                myFaultyObservers.add(myObserver);

                theLogger.error("Observer '" + System.identityHashCode(
                    myObserver) + "' has thrown an unchecked exception on " +
                        "update and will be automatically detached.",
                            anException);
            }
        }

        if (myFaultyObservers.size() > 0)
        {
            detach(myFaultyObservers.toArray(OBSERVER_EMPTY_ARRAY));
        }
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public int
    size()
    {
        return theObservers.size();
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Returns the Observer instances
     *
     * @return the Observer instances
     */
    protected Collection<IObserver<E>>
    getObservers()
    {
        return Collections.unmodifiableCollection(theObservers);
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Collection of <code>IObserver</code> instances.
     */
     private final Collection<IObserver<E>> theObservers = new ArrayList<
         IObserver<E>>();

     /**
      * Reusable empty array of <code>IObserver</code> so it does not need
      * to be recreated each time there is a call to
      * <code>notifyObservers</code>
      */
     @SuppressWarnings("rawtypes")
	protected static final IObserver[] OBSERVER_EMPTY_ARRAY = new IObserver[0];

     /**
      * Provides logging services for this class
      */
     private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

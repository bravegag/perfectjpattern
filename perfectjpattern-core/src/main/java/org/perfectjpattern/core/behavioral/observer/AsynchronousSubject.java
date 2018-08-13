//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AsynchronousSubject.java Copyright (c) 2012 Giovanni Azua Garcia
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
import java.util.concurrent.locks.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.observer.*;
import org.slf4j.*;

/**
 * Implementation of <code>ISubject</code> that notifies Observer instances 
 * from separate threads of execution.
 * <br/>
 * 
 * @see Subject
 * 
 * @param <E> Type of event data this <code>ISubject</code> 
 *            notifies with.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 13, 2007 10:28:42 PM $
 */
public final
class AsynchronousSubject<E>
extends Subject<E>
implements Thread.UncaughtExceptionHandler
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public void 
    notifyObservers(final E anEventData)
    {
        Validate.notNull(anEventData, "'anEventData' must not be null");
        
        LOGGER.debug("notifyObservers with EventData '" + anEventData.
            toString() + "'");
        
        Collection<IObserver<E>> myObservers = getObservers();  
        List<Thread> myThreads = new ArrayList<Thread>(myObservers.size());
        for (IObserver<E> myObserver : myObservers) 
        {
            Thread myThread = new SubjectThread<E>(myObserver, anEventData);
            myThread.setUncaughtExceptionHandler(this);
            myThreads.add(myThread);
        }
        
        for (Thread myThread : myThreads)
        {
            myThread.start();
        }
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void 
    uncaughtException(Thread aThread, Throwable anException)
    {               
        LOGGER.debug("uncaughtException callback received from thread '" + 
            aThread.getName() + "'");

        SubjectThread<E> mySubjectThread = (SubjectThread<E>) aThread;        
        
        detach(mySubjectThread.getObserver());
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void 
    detach(IObserver<E>... anObservers)
    {
        LOGGER.debug("Detaching '" + Arrays.toString(anObservers) + "'");
        
        try
        {
            theDetachLock.lock();
            
            super.detach(anObservers);            
        }
        finally
        {
            theDetachLock.unlock();            
        }
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Runs the {@link IObserver#update(Object)} method in a separate 
     * {@link Thread}
     */
    private static
    class SubjectThread<E>
    extends Thread
    {
        //--------------------------------------------------------------------
        public 
        SubjectThread(IObserver<E> anObserver, E anEventData)
        {
            assert anObserver != null : "'anObserver' must not be null";
            assert anEventData != null : "'anEventData' must not be null";
            
            theObserver = anObserver;
            theEventData = anEventData;
        }
                
        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        @Override
        public void 
        run()
        {
            LOGGER.debug("Running Observer update for '" + theObserver + "'");
            
            theObserver.update(theEventData);
        }

        //--------------------------------------------------------------------
        /**
         * Returns the observer
         *
         * @return the observer
         */
        public final IObserver<E> 
        getObserver()
        {
            return theObserver;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private final IObserver<E> theObserver;
        private final E theEventData;
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging facilities for this class 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
        AsynchronousSubject.class);
    
    private final Lock theDetachLock = new ReentrantLock();    
}

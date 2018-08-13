//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// TestAsynchronousSubject.java Copyright (c) 2012 Giovanni Azua Garcia
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
import java.util.concurrent.*;

import junit.framework.*;

import org.perfectjpattern.core.api.behavioral.observer.*;
import org.perfectjpattern.core.behavioral.observer.data.*;
import org.perfectjpattern.core.structural.proxy.*;
import org.slf4j.*;

/**
 * Test suite for {@link AsynchronousSubject} implementation.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 17, 2007 9:57:59 PM $
 */
public
class TestAsynchronousSubject
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void
    testAsynchronousNotification()
    {
        // test references
        TestStatusObserver myRealObserver1 = new TestStatusObserver();
        TestStatusObserver myRealObserver2 = new TestStatusObserver();
        FaultyStatusObserver myRealFaultyObserver = new FaultyStatusObserver();

        // create several Observer instances
        IObserver<StatusData> myObserver1 = new SynchronizedProxy<
            IObserver>(IObserver.class, myRealObserver1).getComponent();
        LOGGER.debug("Observer1 is '" + myObserver1 + "'");

        IObserver<StatusData> myObserver2 = new SynchronizedProxy<
            IObserver>(IObserver.class, myRealObserver2).getComponent();
        LOGGER.debug("Observer2 is '" + myObserver2 + "'");

        IObserver<StatusData> myFaultyObserver = new SynchronizedProxy<
            IObserver>(IObserver.class, myRealFaultyObserver).getComponent();
        LOGGER.debug("FaultyObserver is '" + myFaultyObserver + "'");

        // create the asynchronous subject
        ISubject<StatusData> mySubject = new AsynchronousSubject<
            StatusData>();

        // attach all observers
        mySubject.attach(myFaultyObserver, myObserver1, myObserver2);

        theCountDownLatch = new CountDownLatch(3);

        // notify all observers with STARTED
        mySubject.notifyObservers(StatusData.STARTED);

        // wait for all Observer instances to finish processing the
        // update event
        try
        {
            theCountDownLatch.await();

            // unavoidable without changing the AsynchronousSubject
            // implementation... there is a time needed to modify the
            // internal Subject list that is out of the Barrier time
            Thread.sleep(2000);
        }
        catch (InterruptedException anException)
        {
            LOGGER.error(anException.getMessage());

            fail(anException.getMessage());
        }

        // by now the Faulty Observer must have been automatically detached
        assertEquals("Faulty Observer must have been automatically detached",
            2, mySubject.size());

        // detach one observer
        mySubject.detach(myObserver1);

        // re-create CountDownLatch
        theCountDownLatch = new CountDownLatch(1);

        // notify remaining observers with COMPLETED
        mySubject.notifyObservers(StatusData.COMPLETED);

        // wait for all Observer instances to finish processing the
        // update event
        try
        {
            theCountDownLatch.await();
        }
        catch (InterruptedException anException)
        {
            LOGGER.error(anException.getMessage());

            fail(anException.getMessage());
        }

        // assert that Event data received is correct
        assertEquals("myObserver1 did not receive the right events.", 1,
            myRealObserver1.getStatus().size());
        assertEquals("myObserver1 did not receive the right events.",
            StatusData.STARTED, myRealObserver1.getStatus().get(0));

        assertEquals("myObserver2 did not receive the right events.", 2,
            myRealObserver2.getStatus().size());
        assertEquals("myObserver2 did not receive the right events.",
            StatusData.STARTED, myRealObserver2.getStatus().get(0));
        assertEquals("myObserver2 did not receive the right events.",
            StatusData.COMPLETED, myRealObserver2.getStatus().get(1));

        // assert that all notification threads are different
        Set<String> myThreads = new TreeSet<String>();
        myThreads.addAll(myRealObserver1.getThreads());
        myThreads.addAll(myRealObserver2.getThreads());

        assertEquals("The number of different threads being used was " +
            "different than expected.", 3, myThreads.size());
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Example of concrete <code>IObserver&lt;StatusData&gt;</code>
     * implementation.
     */
    private static final
    class TestStatusObserver
    implements IObserver<StatusData>
    {
        //---------------------------------------------------------------------
        public void
        update(StatusData aStatusData)
        {
            try
            {
                LOGGER.debug("update called for: TestStatusObserver");

                theStatus.add(aStatusData);
                theThreads.add(Thread.currentThread().getName());
            }
            finally
            {
                theCountDownLatch.countDown();
            }
        }

        //---------------------------------------------------------------------
        public final List<StatusData>
        getStatus()
        {
            return theStatus;
        }

        //---------------------------------------------------------------------
        public final List<String>
        getThreads()
        {
            return theThreads;
        }

        //---------------------------------------------------------------------
        // members
        //---------------------------------------------------------------------
        private final List<String> theThreads = new ArrayList<String>();
        private final List<StatusData> theStatus = new ArrayList<StatusData>();
    }

    //------------------------------------------------------------------------
    /**
     * Example of concrete <code>IObserver&lt;StatusData&gt;</code>
     * implementation.
     */
    private static final
    class FaultyStatusObserver
    implements IObserver<StatusData>
    {
        //---------------------------------------------------------------------
        public void
        update(StatusData aStatusData)
        {
            try
            {
                LOGGER.debug("update called for: FaultyStatusObserver");

                throw new RuntimeException("I am faulty, remember?");
            }
            finally
            {
                theCountDownLatch.countDown();
            }
        }
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
        TestAsynchronousSubject.class);
    private static CountDownLatch theCountDownLatch;
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestSubject.java Copyright (c) 2012 Giovanni Azua Garcia
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

import junit.framework.*;

import org.perfectjpattern.core.api.behavioral.observer.*;
import org.perfectjpattern.core.api.behavioral.observer.data.*;
import org.perfectjpattern.core.behavioral.observer.data.*;
import org.slf4j.*;

/**
 * Test Suite for <code>Subject</code> implementation.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 18, 2007 11:31:53 PM $
 */
public final
class TestSubject 
extends TestCase 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public void 
    testFaultyObserverDetached()
    {
        // create a faulty Observer instance
        IObserver<NullEventData> myObserver = 
            new IObserver<NullEventData>()
        {
            //----------------------------------------------------------------
            public void 
            update(NullEventData anEventData)
            {
                throw new RuntimeException("Sorry! I am faulty!");
            }            
        };
        
        // create Subject instance, attach and notify
        ISubject<NullEventData> mySubject = new Subject<NullEventData>();
        mySubject.attach(myObserver);
        
        try 
        {
            mySubject.notifyObservers(NullEventData.getInstance());
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException)
        // CHECKSTYLE:ON
        {
            fail("Subject is not protected from faulty Observer instances");
        }
        
        assertEquals("Subject did not automatically detach faulty Observers", 
            0, mySubject.size());
    }
    
    //------------------------------------------------------------------------
    /**
     * Test that <code>IStatusData</code> notifications were properly sent to
     * all registered <code>IObserver</code> instances.
     */
    public void 
    testStatusObserver() 
    {
        theLogger.debug("Running assertions ... ");

        assertEquals(
                "First observer incorrectly received completion notifications.",
                1, theFirstObserver.getSuccessCount());
        assertEquals(
                "First observer incorrectly received failure notifications.",
                1, theFirstObserver.getFailureCount());

        assertEquals(
                "Second observer incorrectly missed completion notifications.",
                2, theSecondObserver.getSuccessCount());
        assertEquals(
                "Second observer incorrectly missed failure notifications.", 2,
                theSecondObserver.getFailureCount());

        assertEquals(
                "Third observer incorrectly missed completion notifications.",
                2, theThirdObserver.getSuccessCount());
        assertEquals(
                "Third observer incorrectly missed failure notifications.", 2,
                theThirdObserver.getFailureCount());

        theLogger.debug("Completed test");
    }

    //------------------------------------------------------------------------
    /**
     * Test that <code>IProgressData</code> notifications were properly sent
     * to all registered <code>IObserver</code> instances.
     */
    public void 
    testProgressObserver() 
    {
        theLogger.debug("Running assertions ... ");

        List<ProgressData> myExpectedProgress = Arrays
                .asList(new ProgressData[] {
                        ProgressData.STARTED,
                        new ProgressData(Status.IN_PROGRESS,
                                "Testing in progress ...", 20),
                        new ProgressData(Status.IN_PROGRESS,
                                "Testing in progress ...", 40),
                        new ProgressData(Status.IN_PROGRESS,
                                "Testing in progress ...", 60),
                        new ProgressData(Status.IN_PROGRESS,
                                "Testing in progress ...", 80),
                        ProgressData.COMPLETED });

        int i = 0;
        for (ProgressData myProgress : theFourthObserver.getProgress()) 
        {
            assertEquals("Invalid progress notification sequence.",
                    myExpectedProgress.get(i++), myProgress);
        }

        theLogger.debug("Completed test");
    }
    
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public void
    testClear()
    {
        theLogger.debug("Running assertions ... ");
        
        ISubject<NullEventData> mySubject = new Subject<NullEventData>();
        
        // attach an observer
        mySubject.attach(new IObserver<NullEventData>()
        {
            //----------------------------------------------------------------
            public void 
            update(NullEventData anEventData)
            {
                // nothing to do
            }            
        });
        
        assertEquals("Size method implemented incorrectly.", 1, 
            mySubject.size());

        // should empty the collection of observers
        mySubject.clear();
        
        assertEquals("Clear method implemented incorrectly.", 0, 
            mySubject.size());

        theLogger.debug("Completed test");
    }

    //------------------------------------------------------------------------
    /**
     * Test that <code>ISubject</code> keeps a correct number of
     * <code>IObserver</code> attached.
     */
    public void 
    testSize() 
    {
        theLogger.debug("Running assertions ... ");

        assertEquals("Status Subject contains invalid amount of observers", 2,
                theStatusSubject.size());
        assertEquals("Progress Subject contains invalid amount of observers",
                1, theProgressSubject.size());

        theLogger.debug("Completed test");
    }
    
    //------------------------------------------------------------------------
    /**
     * Simple test of the Observer pattern
     */
    @SuppressWarnings("unchecked")
    public void 
    testSimple() 
    {
        theLogger.debug("Started simple Observer usage test");
        
        theLogger.debug("Step #1 := Define Event data type");
        class MyEventData implements IEventData 
        {
            //----------------------------------------------------------------
            public 
            MyEventData(final String aValue)
            {
                theValue = aValue;
            }
            
            //----------------------------------------------------------------
            public final String 
            getValue() 
            {
                return theValue;
            }            
            
            //----------------------------------------------------------------
            private final String theValue;
        }
        
        theLogger.debug("Step #2 := Define Observer instance");
        IObserver<MyEventData> myObserver = new IObserver<MyEventData>() 
        {
            //----------------------------------------------------------------
            public void 
            update(MyEventData anEventData) 
            {
                theLogger.debug("update call received with data: " + 
                    anEventData.getValue());
            }
        };
        
        theLogger.debug("Step #3 := Create ISubject instance");
        ISubject<MyEventData> mySubject = new Subject<MyEventData>();
                
        theLogger.debug("Step #4 := Attach Observer to Subject");
        mySubject.attach(myObserver);
        
        theLogger.debug("Step #5 := Notify observers");
        mySubject.notifyObservers(new MyEventData("Hello Observer!"));
        
        theLogger.debug("Completed test");
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    @Override
    protected void 
    setUp() 
    throws Exception 
    {
        super.setUp();

        theLogger.debug("Creating Observer pattern test fixture ... ");

        // fixture for the usage of the Observer Pattern
        fixture();

        theLogger.debug("Completed Observer pattern test fixture.");
    }

    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    /**
     * Example usage of the Observer Pattern.
     */
    @SuppressWarnings("unchecked")
    private void
    fixture() 
    {
        //
        // Status notification example
        //
        theLogger.debug("Step #1 := Create ISubject instance.");
        theStatusSubject = new Subject<StatusData>();

        theLogger.debug("Step #2 := Create IObserver instances.");
        theFirstObserver = new TestStatusObserver();
        theSecondObserver = new TestStatusObserver();
        theThirdObserver = new TestStatusObserver();

        theLogger.debug("Step #3 := Attach IObserver to ISubject.");
        theStatusSubject.attach(theFirstObserver, theSecondObserver, 
            theThirdObserver);

        theLogger.debug("Step #4 := Send notifications.");
        theStatusSubject.notifyObservers(StatusData.FAILED);
        theStatusSubject.notifyObservers(StatusData.COMPLETED);

        theLogger.debug("Eventually detach observers.");
        theStatusSubject.detach(theFirstObserver);

        theLogger.debug("Send more notifications.");
        theStatusSubject.notifyObservers(StatusData.FAILED);
        theStatusSubject.notifyObservers(StatusData.COMPLETED);

        //
        // Progress notification example
        //
        theLogger.debug("Step #1 := Create ISubject instance.");
        theProgressSubject = new Subject<ProgressData>();

        theLogger.debug("Step #2 := Create IObserver instances.");
        theFourthObserver = new TestProgressObserver();

        theLogger.debug("Step #3 := Attach IObserver to ISubject.");
        theProgressSubject.attach(theFourthObserver);

        theLogger.debug("Step #4 := Send notifications.");
        theProgressSubject.notifyObservers(ProgressData.STARTED);
        for (int myI = 1; myI < 5; myI++) 
        {
            theProgressSubject.notifyObservers(new ProgressData(
                Status.IN_PROGRESS, "Testing in progress ...", 5, myI));
        }
        theProgressSubject.notifyObservers(ProgressData.COMPLETED);
    }    
    
    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Concrete <code>IObserver&lt;StatusData&gt;</code> implementation.
     */
    private static final 
    class TestStatusObserver 
    implements IObserver<StatusData> 
    {
        //---------------------------------------------------------------------
        public final int 
        getSuccessCount() 
        {
            return theSuccessCount;
        }

        //---------------------------------------------------------------------
        public final int 
        getFailureCount() 
        {
            return theFailureCount;
        }

        //---------------------------------------------------------------------
        public void 
        update(StatusData aStatusData) 
        {
            if (Status.COMPLETED == aStatusData.getStatus()) 
            {
                theSuccessCount++;
            } 
            else 
            if (Status.FAILED == aStatusData.getStatus()) 
            {
                theFailureCount++;
            }
        }
        
        //---------------------------------------------------------------------
        // members
        //---------------------------------------------------------------------
        private int theFailureCount = 0;
        private int theSuccessCount = 0;
    }    

    //------------------------------------------------------------------------
    /**
     * Example of concrete <code>IObserver&lt;ProgressData&gt;</code>
     * implementation.
     */
    private static final 
    class TestProgressObserver 
    implements IObserver<ProgressData> 
    {
        //---------------------------------------------------------------------
        public void 
        update(ProgressData aProgressData) 
        {
            theProgress.add(aProgressData);
        }

        //---------------------------------------------------------------------
        public final 
        Collection<ProgressData> 
        getProgress() 
        {
            return theProgress;
        }

        //---------------------------------------------------------------------
        // members
        //---------------------------------------------------------------------
        private Collection<ProgressData> theProgress = 
            new ArrayList<ProgressData>();
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private ISubject<StatusData> theStatusSubject = null;
    private ISubject<ProgressData> theProgressSubject = null;
    private TestStatusObserver theFirstObserver = null;
    private TestStatusObserver theSecondObserver = null;
    private TestStatusObserver theThirdObserver = null;
    private TestProgressObserver theFourthObserver = null;
    
    /**
     * Provides logging services for this class.
     */
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

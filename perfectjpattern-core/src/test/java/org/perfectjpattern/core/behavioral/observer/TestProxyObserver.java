//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestFancyObserver.java Copyright (c) 2012 Giovanni Azua Garcia
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
import org.slf4j.*;


/**
 * Test suite for <code>ObserverProxy</code> implementation.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 30, 2007 10:17:51 PM $
 */
public 
class TestProxyObserver 
extends TestCase 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public void
    testObserverPOJO()
    {
        // create the subjects
        ISubject<EventDataOne> mySubjectOne = new Subject<EventDataOne>();
        ISubject<EventDataTwo> mySubjectTwo = new Subject<EventDataTwo>();
        ISubject<EventDataThree> mySubjectThree = 
            new Subject<EventDataThree>();
        
        // create a plain agnostic POJO that does not have any abstract
        // coupling to org.perfectjpattern's Observer implementation.
        MyObserverPOJO myPOJO = new MyObserverPOJO();
        
        mySubjectOne.attach(new ObserverProxy<EventDataOne>(myPOJO, 
            "onEventOne", EventDataOne.class));
        
        mySubjectTwo.attach(new ObserverProxy<EventDataTwo>(myPOJO, 
            "onEventTwo", EventDataTwo.class));
        
        mySubjectThree.attach(new ObserverProxy<EventDataThree>(myPOJO, 
            "onEventThree", EventDataThree.class));
        
        mySubjectOne.notifyObservers(new EventDataOne());
        mySubjectTwo.notifyObservers(new EventDataTwo());
        mySubjectThree.notifyObservers(new EventDataThree());
        
        String[] myExpected = new String[] 
            {
                "onEventOne",
                "onEventTwo",
                "onEventThree"                
            };
        
        assertTrue("ObserverProxy did not work as expected, not all methods " +
                "were successfully called.", Arrays.deepEquals(myExpected, 
                    myPOJO.getCalledMethods().toArray()));
    }
    
    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    public static
    class MyObserverPOJO
    {
        //--------------------------------------------------------------------
        public void 
        onEventOne(EventDataOne anEventData)
        {
            theLogger.debug("Received event " + anEventData.toString());
            
            theCalledMethods.add("onEventOne");
        }
        
        //--------------------------------------------------------------------
        public void 
        onEventTwo(EventDataTwo anEventData)
        {
            theLogger.debug("Received event " + anEventData.toString());
            
            theCalledMethods.add("onEventTwo");
        }

        //--------------------------------------------------------------------
        public void 
        onEventThree(EventDataThree anEventData)
        {
            theLogger.debug("Received event " + anEventData.toString());
            
            theCalledMethods.add("onEventThree");
        }        
        
        //--------------------------------------------------------------------
        public List<String>
        getCalledMethods()
        {
            return theCalledMethods;
        }        
        
        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        /**
         * Attribute required for testing purposes: keep track of what calls
         * completed successfully.
         */
        private List<String> theCalledMethods = new ArrayList<String>();
    }
    
    //------------------------------------------------------------------------
    private static 
    class EventDataOne
    implements IEventData
    {        
        // empty
    }
    
    //------------------------------------------------------------------------
    private static 
    class EventDataTwo
    implements IEventData
    {        
        // empty
    }

    //------------------------------------------------------------------------
    private static 
    class EventDataThree
    implements IEventData
    {        
        // empty
    }    
   
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class.
     */
    // CHECKSTYLE:OFF
    private final static Logger theLogger = LoggerFactory.getLogger(
        TestProxyObserver.class);
    // CHECKSTYLE:ON
}

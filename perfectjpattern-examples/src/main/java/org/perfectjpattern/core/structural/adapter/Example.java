//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Example.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.adapter;

import java.util.*;

import org.perfectjpattern.core.api.structural.adapter.*;
import org.slf4j.*;

/**
 * Startup Main for the Adapter Pattern Example code
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jan 29, 2009 11:48:47 AM $
 */
// CHECKSTYLE:OFF
public 
class Example
//CHECKSTYLE:ON
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public static void 
    main(String[] anArguments)
    {
        //---------------------------------------------------------------
        // Create List2QueueAdapter instance 
        //---------------------------------------------------------------
        List<String> myList = new LinkedList<String>();
        
        IAdapter<Queue<String>, List<String>> myAdapter = 
            new List2QueueAdapter<String>(myList);
        
        //---------------------------------------------------------------
        // Retrieve the Adapter's Target interface view i.e. the Queue 
        //---------------------------------------------------------------
        Queue<String> myQueue = myAdapter.getTarget();
        
        //---------------------------------------------------------------
        // Use the Queue as you would normally do, unaware that the 
        // underlying implementation comes from a List
        //---------------------------------------------------------------
        myQueue.offer("Hello");
        myQueue.add("Adapter");
        myQueue.offer("World!");

        String myPollValue = myQueue.poll();
        theLogger.debug(myPollValue);
        
        String myPeekValue = myQueue.peek();
        theLogger.debug(myPeekValue);
        
        theLogger.debug(Arrays.toString(myQueue.toArray()));
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    protected static void
    setLogger(Logger aLogger)
    {
        theLogger = aLogger;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging facilities for this class instance 
     */
    private static Logger theLogger = LoggerFactory.getLogger(Example.class);
}

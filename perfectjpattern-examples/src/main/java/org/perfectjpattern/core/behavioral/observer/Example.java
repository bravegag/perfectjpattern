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
package org.perfectjpattern.core.behavioral.observer;

import java.text.*;
import java.util.*;

import org.perfectjpattern.core.api.behavioral.observer.*;

/**
 * Startup Main for the Observer Pattern Example code
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 5, 2008 10:11:17 PM $
 */
// CHECKSTYLE:OFF
public final
class Example
// CHECKSTYLE:ON
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public static void 
    main(String[] anArguments)
    throws ParseException
    {
        //---------------------------------------------------------------
        // Create Clock Timer subject
        //---------------------------------------------------------------
        ISubject<Date> myClockTimer = new Subject<Date>();
        
        //---------------------------------------------------------------
        // Create and attach concrete observers
        //---------------------------------------------------------------
        IClock myAnalog =  new AnalogClock();        
        myClockTimer.attach(myAnalog, new DigitalClock());
        
        //---------------------------------------------------------------
        // AntiqueClock demonstrates the use of ObserverProxy for cases
        // where the Observer handling source code is not accessible and 
        // thus can not be abstract coupled to PerfectJPattern's Observer
        // In this case we have AntiqueClock that does not implement
        // IObserver and has a "displayTime" static method 
        //---------------------------------------------------------------
        IObserver<Date> myAntiqueClock = new ObserverProxy<Date>(
            AntiqueClock.class, "displayTime", Date.class); 
        myClockTimer.attach(myAntiqueClock);
        
        //---------------------------------------------------------------
        // Notify observers but make sure the date is predictable, this is
        // good for testability
        //---------------------------------------------------------------
        Date myFirstDate = DATE_FORMAT.parse("14.09.1975 08:00:00");        
        myClockTimer.notifyObservers(myFirstDate);

        //---------------------------------------------------------------
        // Optionally detach observers
        //---------------------------------------------------------------
        myClockTimer.detach(myAnalog);

        //---------------------------------------------------------------
        // Notify observers
        //---------------------------------------------------------------
        Date mySecondDate = DATE_FORMAT.parse("25.05.2008 15:56:00");
        myClockTimer.notifyObservers(mySecondDate);
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
        "dd.MM.yyyy HH:mm:ss");
}

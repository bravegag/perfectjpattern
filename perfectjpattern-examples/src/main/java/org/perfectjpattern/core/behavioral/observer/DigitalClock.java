//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AnalogClock.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.slf4j.*;

/**
 * Concrete implementation of {@link IClock} of type Digital
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: May 25, 2008 3:40:41 PM $
 */
public 
class DigitalClock
implements IClock
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void 
    draw()
    {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        
        theLogger.debug("Printing Digital timestamp: " + myFormat.format(
            theDate));
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void 
    update(Date aDate)
    {
        theLogger.debug("Digital clock received an update event");
                
        theDate = (Date) aDate.clone();
        
        draw();
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
    
    private Date theDate;
    
    /**
     * Provides logging facilities for this class 
     */
    private static Logger theLogger = LoggerFactory.getLogger(DigitalClock.
        class);
}

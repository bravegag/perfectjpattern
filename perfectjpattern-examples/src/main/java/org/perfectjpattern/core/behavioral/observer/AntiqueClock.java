//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AntiqueClock.java Copyright (c) 2012 Giovanni Azua Garcia
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
import org.slf4j.*;

/**
 * Antique Clock is introduced as example use-case where you want to target 
 * as Observer a third-party library for which there is no access to its 
 * source code and therefore it can't be modified to implement 
 * {@link IObserver} i.e. not possible to abstract couple it to 
 * PerfectJPattern's  
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jun 16, 2008 10:36:05 PM $
 */
public final
class AntiqueClock
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public static void 
    displayTime(Date aDate)
    {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        
        theLogger.debug("Antique clock works too! " + myFormat.format(aDate));
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
    // private
    //------------------------------------------------------------------------
    private 
    AntiqueClock()
    {
        // empty
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging facilities for this class 
     */
    private static Logger theLogger = LoggerFactory.getLogger(AntiqueClock.
        class);
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestStatusData.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.observer.data;

import junit.framework.*;

/**
 * Test Suite for <code>StatusData</code> implementation.
 *
 * @see StatusData
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 23, 2007 1:15:16 AM $
 */
public final 
class TestStatusData 
extends TestCase 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testValueOf()
    {
        StatusData myInstanceOne = new StatusData(Status.STARTED, 
            "There was a time");
        StatusData myInstanceTwo = new StatusData(myInstanceOne);
        
        assertEquals("valueOf() implemented incorrectly", myInstanceOne, 
            myInstanceTwo);
    }

    //------------------------------------------------------------------------
    public void
    testEquals()
    {
        assertEquals("equals() implemented incorrectly", StatusData.STARTED, 
            new StatusData(Status.STARTED, "Task has started."));
        
        assertFalse("equals() implemented incorrectly", 
            StatusData.STARTED.equals(ProgressData.STARTED));
               
        assertFalse("equals() implemented incorrectly", 
            StatusData.STARTED.equals(null));

        assertTrue("equals() implemented incorrectly", 
            !StatusData.STARTED.equals(StatusData.COMPLETED));
    }
    
    //------------------------------------------------------------------------
    public void
    testHashcode()
    {
        // same hash code => equals true
        assertTrue("hashCode() implemented incorrectly", 
            StatusData.STARTED.hashCode() == StatusData.STARTED.hashCode() && 
                StatusData.STARTED.equals(StatusData.STARTED));        
        
        StatusData myData = 
            new StatusData(Status.STARTED, "Task has started.");

        // different hash code may still hold equals true
        assertTrue("hashCode() implemented incorrectly", 
            StatusData.STARTED.hashCode() == myData.hashCode() && 
                StatusData.STARTED.equals(myData));        
        
        // equals false => different hash code
        assertTrue("hashCode() implemented incorrectly",
            !StatusData.COMPLETED.equals(myData) &&
                StatusData.COMPLETED.hashCode() != myData.hashCode());
    }    

    //------------------------------------------------------------------------
    public void
    testToString()
    {
        assertEquals("toString() implemented incorrectly", 
            StatusData.STARTED.toString(), 
            "StatusData(Status='Started', " +
            "Message='Task has started.')");        
    }
}

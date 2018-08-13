//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestProgressData.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Test Suite for <code>ProgressData</code> implementation.
 * 
 * @see ProgressData
 *   
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 23, 2007 1:23:01 AM $
 */
public final 
class TestProgressData 
extends TestCase 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testValueOf()
    {
        ProgressData myInstanceOne = new ProgressData(Status.STARTED, 
            "There was a time", 0);
        ProgressData myInstanceTwo = new ProgressData(myInstanceOne);
        
        assertEquals("valueOf implemented incorrectly", myInstanceOne, 
            myInstanceTwo);
    }

    //------------------------------------------------------------------------
    public void
    testEquals()
    {
        assertEquals("Equals implemented incorrectly", ProgressData.STARTED, 
            new ProgressData(Status.STARTED, "Task has started.", 0));
               
        assertTrue("Equals implemented incorrectly", 
            !ProgressData.STARTED.equals(ProgressData.COMPLETED));
        
        assertTrue("equals() implemented incorrectly", 
            !ProgressData.STARTED.equals(StatusData.STARTED));        
    }
    
    //------------------------------------------------------------------------
    public void
    testHashcode()
    {
        // same hash code => equals true
        assertTrue("hashCode() implemented incorrectly", 
            ProgressData.STARTED.hashCode() == 
                ProgressData.STARTED.hashCode() &&
            ProgressData.STARTED.equals(ProgressData.STARTED));        
        
        ProgressData myData = 
            new ProgressData(Status.STARTED, "Task has started.", 0);

        // different hash code may still hold equals true
        assertTrue("hashCode() implemented incorrectly", 
            ProgressData.STARTED.hashCode() == myData.hashCode() && 
                ProgressData.STARTED.equals(myData));        
        
        // equals false => different hash code
        assertTrue("hashCode() implemented incorrectly",
            !ProgressData.COMPLETED.equals(myData) &&
            ProgressData.COMPLETED.hashCode() != myData.hashCode());
    }        
    
    //------------------------------------------------------------------------
    public void
    testToString()
    {
        assertEquals("toString() implemented incorrectly", 
            ProgressData.STARTED.toString(), 
            "ProgressData(StatusData(Status='Started', " +
            "Message='Task has started.'), Progress='0%')");        
    }    
}

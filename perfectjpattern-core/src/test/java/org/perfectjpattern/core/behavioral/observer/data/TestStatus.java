//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestStatus.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Test Suite for <code>Status</code> implementation.
 * 
 * @see Status
 *  
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 23, 2007 1:04:38 AM $
 */
public final
class TestStatus
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Test <code>toString</code> implementation.
     */
    public void
    testToString()
    {
        String[] myExpected = new String[] 
        {
           "Not started", 
           "Started", 
           "Suspended", 
           "In Progress",
           "Completed", 
           "Failed"
        };
        
        Status[] myStatus = new Status[] 
        {
            Status.NOT_STARTED,
            Status.STARTED,
            Status.SUSPENDED,
            Status.IN_PROGRESS,
            Status.COMPLETED,
            Status.FAILED
        };
        
        assert myStatus.length == myExpected.length;
        
        final int mySize = myExpected.length;
        for (int i = 0; i < mySize; i++)
        {
            assertEquals("toString implementation failed.", 
                myExpected[i], myStatus[i].toString());
        }        
    }
}

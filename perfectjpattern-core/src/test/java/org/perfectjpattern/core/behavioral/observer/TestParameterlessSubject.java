//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestParameterlessSubject.java Copyright (c) 2012 Giovanni Azua Garcia
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

import junit.framework.*;

import org.perfectjpattern.core.api.behavioral.observer.*;
import org.perfectjpattern.core.api.behavioral.observer.data.*;

/**
 * Test suite for the {@link ParameterlessSubject} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 13, 2009 10:03:09 PM $
 */
public 
class TestParameterlessSubject
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public void 
    testParameterlessSubject()
    {
        IObserver<NullEventData> myObserver1 = new TestNullEventDataObserver();
        IObserver<NullEventData> myObserver2 = new TestNullEventDataObserver();
        
        ParameterlessSubject mySubject = new ParameterlessSubject();
        mySubject.attach(myObserver1, myObserver2);
        mySubject.notifyObservers();
        
        assertEquals("ParameterlessSubject did not work as expected", 2, 
            theCallCount);
    }
    
    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Concrete <code>IObserver&lt;NullEventData&gt;</code> implementation
     */
    private static final 
    class TestNullEventDataObserver 
    implements IObserver<NullEventData> 
    {
        //---------------------------------------------------------------------
        public void 
        update(NullEventData anEventData) 
        {
            theCallCount++;
        }
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static int theCallCount = 0;
}

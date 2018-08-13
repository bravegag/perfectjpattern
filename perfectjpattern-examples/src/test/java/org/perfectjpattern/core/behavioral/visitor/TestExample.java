//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestExample.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.visitor;

import static org.easymock.EasyMock.*;
import junit.framework.*;

import org.slf4j.*;

/**
 * Visitor example test suite 
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 20, 2008 1:58:38 PM $
 */
public 
class TestExample
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testVisitor()
    {
        Logger myLoggerMock = createNiceMock(Logger.class);
                
        myLoggerMock.debug("Visiting car");
        myLoggerMock.debug("Visiting engine");
        myLoggerMock.debug("Visiting body");
        myLoggerMock.debug("Visiting front left wheel");
        myLoggerMock.debug("Visiting front right wheel");
        myLoggerMock.debug("Visiting back left wheel");
        myLoggerMock.debug("Visiting back right wheel");
        myLoggerMock.debug("Vroom!");
        myLoggerMock.debug("Starting my engine");
        myLoggerMock.debug("Moving my body");
        myLoggerMock.debug("Steering my wheel");
        myLoggerMock.debug("Steering my wheel");
        myLoggerMock.debug("Steering my wheel");
        myLoggerMock.debug("Steering my wheel");
                
        replay(myLoggerMock);
        
        PrintVisitor.setLogger(myLoggerMock);
        DoVisitor.setLogger(myLoggerMock);
        
        Example.main(new String[0]);
        
        verify(myLoggerMock);
    }
}

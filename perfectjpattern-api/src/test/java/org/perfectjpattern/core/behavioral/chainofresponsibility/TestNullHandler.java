//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestNullHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.chainofresponsibility;

import org.perfectjpattern.core.api.behavioral.chainofresponsibility.*;
import org.perfectjpattern.support.test.*;

/**
 * Test Suite for {@link NullHandler} implementation
 * 
 * @see NullHandler
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 26, 2007 1:01:56 AM $
 */
public 
class TestNullHandler 
extends AbstractTestSingleton<NullHandler> 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    TestNullHandler()
    {
        super(NullHandler.class, NullHandler.getInstance());
    }
    
    //------------------------------------------------------------------------
    /**
     * Test that {@link UnsupportedOperationException} is thrown when 
     * appropriate
     */
    public void
    testExceptions()
    {
        NullHandler myNullHandler = NullHandler.getInstance();
        
        try 
        {
            myNullHandler.getSuccessor();
            fail("getSuccessor() was not implemented correctly.");
        }
        catch (UnsupportedOperationException anException)
        {
            // ok
        }
        
        try 
        {
            myNullHandler.setChainStrategy(null);            
            fail("setChainStrategy() was not implemented correctly.");
        }
        catch (UnsupportedOperationException anException)
        {
            // ok
        }

        try 
        {
            myNullHandler.setSuccessor(null);            
            fail("setSuccessor() was not implemented correctly.");
        }
        catch (UnsupportedOperationException anException)
        {
            // ok
        }

        try 
        {
            myNullHandler.handle(NullRequest.getInstance());            
            // ok no exception
        }
        catch (UnsupportedOperationException anException)
        {
            fail("handle() was not implemented correctly.");
        }
    }    
    
    //------------------------------------------------------------------------
    public void
    testCanHandle()
    {
        assertTrue("NullHandler canHandle(...) must always return true", 
            NullHandler.getInstance().canHandle(null));
    }
}

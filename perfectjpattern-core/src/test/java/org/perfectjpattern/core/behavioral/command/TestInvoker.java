//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestInvoker.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.command;

import junit.framework.*;

import org.perfectjpattern.core.api.behavioral.command.*;
import org.slf4j.*;


/**
 * Test Suite for <code>Invoker</code> implementation.
 * 
 * @see ICommand
 * @see IInvoker
 * @see Command
 * @see Invoker
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 30, 2007 12:47:40 PM $
 */
public 
class TestInvoker 
extends TestCase 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Test that Command Pattern lifecycle methods are executed in the 
     * right order.
     */
    public void 
    testInvokerInvalidStateDetected()
    {
        theLogger.debug("Running assertions ... ");

        // create a new Invoker
        IInvoker<Object, Object> myInvoker = new Invoker<Object, Object>();

        try 
        {
            myInvoker.invoke();
            fail("Invoker implementation did not detect missing ICommand");
        }
        catch (IllegalStateException anException)
        {
            // ok
        }
        
        try 
        {
            myInvoker.getResult();
            fail("Invoker implementation did not detect missing ICommand");
        }
        catch (IllegalStateException anException)
        {
            // ok
        }        

        // create a new Command
        ICommand<Object, Object> myCommand = new ICommand<Object, Object>()
        {
            //----------------------------------------------------------------
            public void 
            execute() 
            throws IllegalStateException
            {
                // do nothing
            }

            //----------------------------------------------------------------
            public Object 
            getResult() 
            throws IllegalStateException
            {
                return null;
            }

            //----------------------------------------------------------------
            public void 
            setParameter(Object aParameter) 
            throws IllegalArgumentException
            {
                // do nothing
            }

            //----------------------------------------------------------------
            public void 
            setReceiver(IReceiver<Object, Object> aReceiver) 
            throws IllegalArgumentException
            {
                // do nothing
            }            
        };
        
        myInvoker.setCommand(myCommand);
        try 
        {
            myInvoker.getResult();
            fail("Invoker implementation did not detect null IResult");
        }
        catch (IllegalStateException anException)
        {
            // ok
        }                
        
        theLogger.debug("Completed test");        
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class.
     */
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

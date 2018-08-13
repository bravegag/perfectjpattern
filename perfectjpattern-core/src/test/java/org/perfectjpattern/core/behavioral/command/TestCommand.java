//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestCommand.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import junit.framework.*;

import org.perfectjpattern.core.api.behavioral.command.*;
import org.slf4j.*;


/**
 * Test Suite for {@link Command} and {@link Invoker} implementations
 * 
 * @see ICommand
 * @see IInvoker
 * @see Command
 * @see Invoker
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 26, 2007 1:15:58 AM $
 */
// CHECKSTYLE:OFF
public 
class TestCommand 
extends TestCase 
// CHECKSTYLE:ON
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Test that Command Pattern lifecycle methods are executed in the 
     * right order.
     */
    public void 
    testLifecycleWorks() 
    {
        theLogger.debug("Running assertions ... ");

        State[] myExpectedStates = 
            new State[] 
            {
                State.RECEIVER_ASSIGNED,
                State.COMMAND_PARAMETER_ASSIGNED,
                State.COMMAND_EXECUTED,
                State.RECEIVER_PARAMETER_ASSIGNED,
                State.RECEIVER_EXECUTED,
                State.RESULT_RETRIEVED 
            };

        assertEquals("Invalid number of states.", myExpectedStates.length,
                theStates.size());

        assertEquals("States mismatch ", Arrays.deepToString(myExpectedStates),
            Arrays.deepToString(theStates.toArray(new State[0])));
        
        theLogger.debug("Completed test");
    }

    //------------------------------------------------------------------------
    /**
     * Test results handling.
     */
    public void 
    testResultHandling() 
    {
        theLogger.debug("Running assertions ... ");

        assertNotNull("Receiver final result must not be null",
                theFinalResult);

        theLogger.debug("Completed test");
    }
    
    //------------------------------------------------------------------------
    public void
    testCommandInvalidStateDetected()
    {
        theLogger.debug("Running assertions ... ");

        // create a new Command
        ICommand<Object, Object> myCommand = new Command<Object, Object>();

        try 
        {
            myCommand.execute();
            fail("Command implementation did not detect missing IReceiver");
        }
        catch (IllegalStateException anException)
        {
            // ok
        }

        try 
        {
            myCommand.getResult();
            fail("Command implementation did not detect missing IReceiver");
        }
        catch (IllegalStateException anException)
        {
            // ok
        }
        
        // now create a new Receiver
        IReceiver<Object, Object> myReceiver = new IReceiver<Object, Object>()
        {
            //----------------------------------------------------------------
            public void execute()
            {
                // do nothing
            }

            //----------------------------------------------------------------
            public Object
            getResult()
            {
                return null;
            }

            //----------------------------------------------------------------
            public void setParameter(Object aParameter)
            {
                // do nothing
            }            
        };
        
        myCommand.setReceiver(myReceiver);
        myCommand.execute();
        
        try 
        {
            myCommand.getResult();
            fail("Command implementation did not detect null IResult.");
        }
        catch (IllegalStateException anException)
        {
            // ok
        }        

        theLogger.debug("Completed test");        
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    @Override
    protected void 
    setUp() 
    throws Exception 
    {
        super.setUp();

        theLogger.debug("Creating Command pattern test fixture ... ");

        // clean all existing states
        theStates.clear();

        // fixture for usage of the Command
        fixture();

        theLogger.debug("Completed Command pattern test fixture.");
    }
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    public void 
    fixture() 
    {
        theLogger.debug("Step #1 := Create the Invoker");
        IInvoker<MyParameter, MyResult> myInvoker = 
            new Invoker<MyParameter, MyResult>();

        theLogger.debug("Step #2 := Create the Command");
        ICommand<MyParameter, MyResult> myCommand = new MyCommand();

        theLogger.debug("Step #3 := Create the Receiver");
        IReceiver<MyParameter, MyResult> myReceiver = new MyReceiver();

        theLogger.debug("Step #4 := Wire components");    
        
        // assign command to invoker
        myInvoker.setCommand(myCommand);
        
        // assign receiver to command
        myCommand.setReceiver(myReceiver);        
        
        // create theParameter
        MyParameter myParameter = new MyParameter();
        
        // assign theParameter to invoker
        myInvoker.setParameter(myParameter);
        
        theLogger.debug("Step #5 := Execute the invoker"); 
        myInvoker.invoke();

        theLogger.debug("Step #6 := Collect results");
        MyResult myResult = myInvoker.getResult();

        // Make result available for testing
        theFinalResult = myResult;

    }    
    
    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Enum type with all possible Command pattern states.
     */
    private static 
    enum State 
    {
        COMMAND_PARAMETER_ASSIGNED
        {
            @Override
            public String toString() 
            {
                return "Command Parameter Assigned";
            }
        }, 
        RECEIVER_PARAMETER_ASSIGNED
        {
            @Override
            public String toString() 
            {
                return "Receiver Parameter Assigned";
            }            
        }, 
        RECEIVER_ASSIGNED
        {
            @Override
            public String toString() 
            {
                return "Receiver Assigned";
            }            
        }, 
        COMMAND_EXECUTED 
        {
            @Override
            public String toString() 
            {
                return "Command Executed";
            }            
        }, 
        RECEIVER_EXECUTED 
        {
            @Override
            public String toString() 
            {
                return "Receiver Executed";
            }            
        }, 
        RESULT_RETRIEVED
        {
            @Override
            public String toString() 
            {
                return "Result Retrieved";
            }            
        };
    }    
    
    //------------------------------------------------------------------------
    /**
     * Example test implementation of <code>ICommand</code>.
     */
    private static 
    class MyCommand 
    extends Command<MyParameter, MyResult> 
    {
        //--------------------------------------------------------------------
        @Override
        public void 
        setParameter(MyParameter aParameter) 
        {
            TestCommand.theStates.add(State.COMMAND_PARAMETER_ASSIGNED);
            
            super.setParameter(aParameter);
        }

        //--------------------------------------------------------------------
        @Override
        public void 
        setReceiver(IReceiver<MyParameter, MyResult> aReceiver) 
        {
            TestCommand.theStates.add(State.RECEIVER_ASSIGNED);
            
            super.setReceiver(aReceiver);
        }       

        //--------------------------------------------------------------------
        @Override
        public void 
        execute() 
        {
            TestCommand.theStates.add(State.COMMAND_EXECUTED);
            super.execute();
        }
    }

    //------------------------------------------------------------------------
    /**
     * Example test implementation of <code>IReceiver</code>.
     */
    private static 
    class MyReceiver 
    extends AbstractReceiver<MyParameter, MyResult> 
    {
        //--------------------------------------------------------------------
        public void 
        execute() 
        {            
            setResult(new MyResult());
            
            TestCommand.theStates.add(State.RECEIVER_EXECUTED);
        }

        //--------------------------------------------------------------------
        @Override
        public void 
        setParameter(MyParameter aParameter) 
        {
            super.setParameter(aParameter);
            
            TestCommand.theStates.add(State.RECEIVER_PARAMETER_ASSIGNED);
        }

        //--------------------------------------------------------------------
        @Override
        public MyResult 
        getResult() 
        {
            TestCommand.theStates.add(State.RESULT_RETRIEVED);
            
            return super.getResult();
        }
    }    
    
    //------------------------------------------------------------------------
    /**
     * Example test <code>IParameter</code>.
     */
    private static 
    class MyParameter 
    {
        // empty
    }

    //------------------------------------------------------------------------
    /**
     * Example test <code>IResult</code>.
     */
    private static 
    class MyResult 
    {
        // empty
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Collection of states.
     */
    private static List<State> theStates = new ArrayList<State>();
    
    /**
     * Final result
     */
    private MyResult theFinalResult = null;    

    /**
     * Provides logging services for this class.
     */
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

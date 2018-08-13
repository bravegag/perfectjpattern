//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractTestState.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.support.test;

import java.lang.reflect.*;
import java.text.*;

import junit.framework.*;

import org.perfectjpattern.core.api.behavioral.state.*;
import org.slf4j.*;

/**
 * Abstract support for creating Test Suite to test implementations of 
 * the State Pattern
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 29, 2009 5:28:36 PM $
 */
public abstract 
class AbstractTestState<State extends IState<?, ?>,
    Context extends IContext<State, ?>>
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Test that the transition gives the expected Next State
     * 
     * @param <Request> Specific request type
     * @param aState A source State
     * @param aRequest A Request
     * @param anExpectedState The expected State
     */
    @SuppressWarnings("unchecked")
	public <Request extends IRequest> void 
    testTransition(State aState, Class<Request> aRequest, State anExpectedState)
    {
        assert aState != null : "'aState' must not be null";
        assert aRequest != null : "'aRequest' must not be null";
        assert anExpectedState != null : "'anExpectedState' must not be null";

        State myNextState = (State) aState.getNext(aRequest);
        
        String myMessage = MessageFormat.format("Next state ''{0}'' does not " +
            "match the one expected ''{1}''", myNextState, anExpectedState);
        
        assertEquals(myMessage, anExpectedState, myNextState);
    }

    //------------------------------------------------------------------------
    /**
     * Test that the transition and the given Context results in the expected 
     * Next State
     *
     * @param <Request> Specific request type
     * @param aState A source State
     * @param aContext A given Context
     * @param aRequest A Request
     * @param anExpectedState The expected State
     * @param anArguments The Arguments to pass to the Request method
     */
    public <Request extends IRequest> void 
    testTransition(State aState, Class<Request> aRequest, Context aContext,  
        State anExpectedState, Object ... anArguments)
    {
        assert aState != null : "'aState' must not be null";
        assert aRequest != null : "'aRequest' must not be null";
        assert aContext != null : "'aContext' must not be null";
        assert anExpectedState != null : "'anExpectedState' must not be null";
        assert anArguments != null : "'anArguments' must not be null";

        Method myMethod = aRequest.getMethods()[0];
        
        try 
        {
            myMethod.invoke(aState, anArguments);
        }
        // CHECKSTYLE:OFF
        catch (Exception anException)
        // CHECKSTYLE:ON
        {
            fail("Transition produces unexpected Exception '" + 
                anException.getMessage() + "'");
        }
        
        State myNextState = aContext.getCurrent();
        
        String myMessage = MessageFormat.format("Next state ''{0}'' does not " +
            "match the one expected ''{1}''", myNextState, anExpectedState);
        
        assertEquals(myMessage, anExpectedState, myNextState);
    }
    
    //------------------------------------------------------------------------
    /**
     * Test that the transition results in the expected Exception
     * 
     * @param <Request>
     * @param aState
     * @param aRequest
     * @param anExpectedException
     */
    public <Request extends IRequest> void 
    testTransition(State aState, Class<Request> aRequest, 
        Throwable anExpectedException)
    {
        assert aState != null : "'aState' must not be null";
        assert aRequest != null : "'aRequest' must not be null";
        assert anExpectedException != null : 
            "'anExpectedException' must not be null";
        
        theLogger.debug(MessageFormat.format("Testing transition from " +
            "State ''{0}'' with Request ''{1}'' an expected Exception ''{2}''",
                aState, aRequest, anExpectedException));
        
        try
        {
            aState.getNext(aRequest);
            
            fail("Transition does not produce exception");
        }
        // CHECKSTYLE:OFF
        catch (Throwable anException)
        // CHECKSTYLE:ON
        {
            String myMessage = MessageFormat.format("Transition generates " +
                "exection ''{0}'' different from the expected ''{1}''", 
                    anExpectedException, anException);
            
            assertEquals(myMessage, anExpectedException, anException);
            
            assertEquals(myMessage, anExpectedException, anException);
        }
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static Logger theLogger = LoggerFactory.getLogger(
        AbstractTestState.class);
}

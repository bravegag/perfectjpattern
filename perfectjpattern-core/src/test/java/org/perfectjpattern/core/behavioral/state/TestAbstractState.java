//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// TestAbstractState.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.state;

import junit.framework.*;

import org.easymock.*;
import org.perfectjpattern.core.api.behavioral.state.*;
import org.slf4j.*;

/**
 * Test suite for the {@link AbstractState} implementation
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 24, 2009 12:52:01 PM $
 */
public
class TestAbstractState
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Tests that by default States are not final
     */
    public void
    testFinalStateDefault()
    {
        assertEquals("States should not be the final by default",
            Boolean.FALSE, Boolean.valueOf(new TestState().isFinal()));
    }

    //------------------------------------------------------------------------
    /**
     * Test that the next assignment and resolution works
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void
    testNext()
    {
        theLogger.debug("Setting up mocks for IStateFactory and IState");

        // mock a IStateFactory
        IStateFactory myFactory = EasyMock.createNiceMock(
            IStateFactory.class);
        IState myInitial = EasyMock.createNiceMock(IState.class);

        theLogger.debug("IStateFactory mock will return a predefined IState " +
            "as initial");

        EasyMock.expect(myFactory.getInitial()).andReturn(myInitial);
        EasyMock.replay(myFactory);

        theLogger.debug("Testing the AbstractContext implementation");

        IContext myContext = new TestContext(myFactory);

        EasyMock.verify(myFactory);

        theLogger.debug("Create TestState instance");

        TestState myState = new TestState();
        TestState myNextState = new TestState();

        myState.setNext(ISomeRequest.class, myNextState);
        myState.next(myContext, ISomeRequest.class);

        theLogger.debug("Run assertions");
        assertEquals("", myNextState, myContext.getCurrent());

        try
        {
            myNextState.next(myContext, ISomeRequest.class);
            fail("Calling next on undefined Request should produce " +
                "an UnsupportedOperationException");
        }
        catch (UnsupportedOperationException anException)
        {
            // ok
        }
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    private
    interface ISomeRequest
    extends IRequest
    {
        //--------------------------------------------------------------------
        public void
        someRequest();
    }

    //------------------------------------------------------------------------
    private static
    class TestState
    extends AbstractState<TestState, IContext<TestState, ?>>
    {
        // empty
    }

    //------------------------------------------------------------------------
    private static
    class TestContext
    extends AbstractContext<TestState, IStateFactory<TestState>>
    {
        //--------------------------------------------------------------------
        public
        TestContext(IStateFactory<TestState> aStateFactory)
        {
            super(aStateFactory);
        }
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class.
     */
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

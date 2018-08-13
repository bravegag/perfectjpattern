//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// TestAbstractContext.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Test suite for the {@link AbstractContext} implementation
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 24, 2009 12:31:07 PM $
 */
public
class TestAbstractContext
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void
    testConstruction()
    throws Exception
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

        // run assertions
        theLogger.debug("Running assertions");
        assertEquals("StateFactory is not the one assigned", myFactory,
            myContext.getStateFactory());
        assertEquals("Initial State was not successfully assigned", myInitial,
            myContext.getCurrent());
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    private static
    class TestContext
    extends AbstractContext<IState<?, ?>, IStateFactory<IState<?, ?>>>
    {
        //--------------------------------------------------------------------
        public
        TestContext(IStateFactory<IState<?, ?>> aStateFactory)
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

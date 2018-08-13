//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestAbstractStateFactory.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.behavioral.state.*;
import org.slf4j.*;

/**
 * Test suite for the {@link AbstractStateFactory} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 24, 2009 12:52:49 PM $
 */
public 
class TestAbstractStateFactory
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Tests the ability of the AbstractStateFactory implementation to create 
     * full State implementation out of Partial ones.
     */
    public void
    testCreateState()
    {
        theLogger.debug("Create the StateFactory to test");
        
        TestPartialState myPartialState = new TestPartialState();
        
        TestStateFactory myFactory = new TestStateFactory(ITestState.class);
        ITestState myState = myFactory.createState(myPartialState);
        
        try
        {
            // nothing should happen
            myState.someRequest();
            
            fail("Requests not implemented, by default should result " +
                "in UnsupportedOperationException");
        }
        catch (UnsupportedOperationException anException)
        {
            // ok
        }
        
        myState.anotherRequest();
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    private static
    interface ISomeRequest
    extends IRequest
    {
        //--------------------------------------------------------------------
        public void
        someRequest();
    }
    
    //------------------------------------------------------------------------
    private static
    interface IAnotherRequest
    extends IRequest
    {
        //--------------------------------------------------------------------
        public void
        anotherRequest();
    }

    //------------------------------------------------------------------------
    public static
    class TestPartialState
    extends AbstractState<TestPartialState, IContext<TestPartialState, ?>>
    implements IAnotherRequest
    {
        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        public void 
        anotherRequest()
        {
            // empty
        }
    }

    //------------------------------------------------------------------------
    private static
    interface ITestState
    extends IAnotherRequest, ISomeRequest, IState<ITestState, 
        IContext<ITestState, ?>>
    {
        // empty
    }
    
    //------------------------------------------------------------------------
    @SuppressWarnings("unused")
    private static
    class TestStateFactory
    extends AbstractStateFactory<ITestState>
    {
        //--------------------------------------------------------------------
        protected 
        TestStateFactory(Class<ITestState> aStateClass)
        throws IllegalArgumentException
        {
            super(aStateClass);
        }

        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        @Override
        void 
        buildMachine()
        {
            // empty
        }

        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        public ITestState 
        getInitial()
        {
            return null;
        }        

        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        public Enum<?>
        toEnum()
        {
            return null;
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

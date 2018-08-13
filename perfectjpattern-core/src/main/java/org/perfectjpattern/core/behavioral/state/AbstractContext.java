//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractContext.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.state.*;

/**
 * Abstract reusable partial implementation of {@link IContext}
 * 
 * @param <State> Concrete State type
 * @param <StateFactory> Abstract Factory that creates instances of State
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 4, 2009 10:32:57 PM $
 */
public abstract
class AbstractContext<State extends IState<?, ?>, 
    StateFactory extends IStateFactory<State>>
implements IContext<State, StateFactory>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs an {@link AbstractContext} from the concrete 
     * {@link IStateFactory}.
     * 
     * @param aStateFactory Concrete {@link IStateFactory}
     * @throws IllegalArgumentException 'aStateFactory' must not be null
     */
    public 
    AbstractContext(StateFactory aStateFactory)
    {
        theStateFactory = aStateFactory;
        setCurrent(theStateFactory.getInitial());
        
        assert getCurrent() != null : "'theCurrent' must not be null";
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public StateFactory 
    getStateFactory()
    {
        return theStateFactory;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final State 
    getCurrent()
    {
        return theCurrent;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final void 
    setCurrent(State aState)
    throws IllegalArgumentException
    {
        Validate.notNull(aState, "'aState' must not be null");
        
        theCurrent = aState;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private State theCurrent;
    private StateFactory theStateFactory;
}

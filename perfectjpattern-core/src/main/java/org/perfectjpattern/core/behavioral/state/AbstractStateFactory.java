//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractStateFactory.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Abstract base reusable implementation of {@link IStateFactory}
 * 
 * @param <State> Type of the Full State that this Abstract Factory constructs
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 17, 2009 11:04:45 AM $
 */
public abstract 
class AbstractStateFactory<State extends IState<?, ?>>
implements IStateFactory<State>
{
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs an {@link AbstractStateFactory} from the full State type
     * 
     * @throws IllegalArgumentException 'aStateClass' must not be null
     */
    protected 
    AbstractStateFactory(Class<State> aStateClass)
    throws IllegalArgumentException
    {
        Validate.notNull(aStateClass, "'aStateClass' must not be null");
        
        theStateClass = aStateClass;
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns a new full State implementation from a partial State 
     * implementation. The full State will use a default behavior for
     * unimplemented requests.
     * 
     * @param <PartialState> Type of the Partial State
     * @param aPartialState The partial State
     * @return new full State implementation given a partial State 
     *         implementation
     */
    protected <PartialState extends IState<?, ?>> State
    createState(PartialState aPartialState)
    {
        return createState(aPartialState, new StateAdaptingStrategy());
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns a new full State implementation from a partial State 
     * implementation. The full State will use the specified behavior for
     * unimplemented requests i.e. the given {@link IStateAdaptingStrategy}
     * 
     * @param <PartialState> Type of the Partial State
     * @param aPartialState The partial State
     * @param aStateAdaptingStrategy The concrete {@link IStateAdaptingStrategy}
     * @return new full State implementation given a partial State 
     *         implementation
     */
    protected <PartialState extends IState<?, ?>> State
    createState(PartialState aPartialState, 
        IStateAdaptingStrategy aStateAdaptingStrategy)
    {
        State myState = new StateAdapter<State, PartialState>(theStateClass, 
                aPartialState, aStateAdaptingStrategy).getTarget();
        
        return myState;
    }

    //------------------------------------------------------------------------
    /**
     * Defines how to build the State Machine i.e. associate the next known
     * State for every Request. Note that this method can be overridden by 
     * subclasses that intend to only redefine transitions.
     */
    abstract void
    buildMachine();    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Class<State> theStateClass;
}

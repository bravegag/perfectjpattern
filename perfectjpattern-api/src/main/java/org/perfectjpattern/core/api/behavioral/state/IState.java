//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IState.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.behavioral.state;

/**
 * <b>State Design Pattern</b>: <i>"Allow an object to alter its behavior 
 * when its internal state changes. The object will appear to change its 
 * class."</i> (Gamma et al, Design Patterns)
 * <br/>
 * <br/>
 * 
 * <b>Responsibility</b>: Abstract generic definition of the "State"
 * pattern role.<br/>
 * <ul>
 * <li>defines an interface for encapsulating the behavior associated 
 * with a particular state of the Context.</li>
 * </ul>
 * 
 * @param <State> Concrete State type
 * @param <Context> Concrete Context type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 2, 2009 1:04:33 PM $
 */
public 
interface IState<State extends IState<?, ?>, Context extends IContext<State, ?>>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns true if this is a final state, false otherwise
     * 
     * @return true if this is a final state, false otherwise
     */
    public boolean
    isFinal();

    //------------------------------------------------------------------------
    /**
     * Returns the next predictable known {@link IState} for a given Request 
     * 
     * @param <Request> Concrete {@link IRequest} type
     * @param aRequestClass The {@link IRequest} received
     * @throws IllegalArgumentException 'aRequestClass' must not be null
     * @throws UnsupportedOperationException Next State not known for State 
     *         'State' and Request 'Request'
     */
    public <Request extends IRequest> State
    getNext(Class<Request> aRequestClass)
    throws IllegalArgumentException, UnsupportedOperationException;    

    //------------------------------------------------------------------------
    /**
     * Sets the next known {@link IState} for a given {@link IRequest}
     * 
     * @param <Request> Concrete {@link IRequest} type
     * @param aRequestClass The class of the {@link IRequest}
     * @param aState The next {@link IState} to set
     * @throws IllegalArgumentException 'aRequestClass' must not be null
     * @throws IllegalArgumentException 'aState' must not be null
     */
    public <Request extends IRequest> void
    setNext(Class<Request> aRequestClass, State aState)
    throws IllegalArgumentException;    

    //------------------------------------------------------------------------
    /**
     * Assigns to aStateContext the next predictable known {@link IState}. 
     * 
     * @param <Request> Concrete {@link IRequest} type
     * @param aStateContext The {@link IStateAware} Element to move to the next 
     *        known {@link IState}
     * @param aRequestClass The {@link IRequest} received
     * @throws IllegalArgumentException 'aStateContext' must not be null
     * @throws IllegalArgumentException 'aRequestClass' must not be null
     * @throws UnsupportedOperationException Next State not known for State 
     *         'State' and Request 'Request'
     */
    public <Request extends IRequest> void
    next(Context aStateContext, Class<Request> aRequestClass)
    throws IllegalArgumentException, UnsupportedOperationException;    
}

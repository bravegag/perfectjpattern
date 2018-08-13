//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IContext.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * <b>Responsibility</b>: Abstract generic definition of the "Context".
 * pattern role.<br/>
 * <ul>
 * <li>defines the interface of interest to clients.</li>
 * <li>maintains an instance of a ConcreteState subclass that defines 
 * the current state.</li>
 * </ul>
 * 
 * @param <State> Concrete State type
 * @param <StateFactory> Abstract Factory that creates instances of State
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 2, 2009 1:04:08 PM $
 */
public 
interface IContext<State extends IState<?, ?>, 
    StateFactory extends IStateFactory<State>>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the current state
     * 
     * @return the current state
     */
    public State 
    getCurrent();
    
    //------------------------------------------------------------------------
    /**
     * Sets the current state
     * 
     * @param aState The state to set
     */
    public void
    setCurrent(State aState);

    //------------------------------------------------------------------------
    /**
     * Returns the State Factory
     * 
     * @return the State Factory
     */
    public StateFactory
    getStateFactory();
}

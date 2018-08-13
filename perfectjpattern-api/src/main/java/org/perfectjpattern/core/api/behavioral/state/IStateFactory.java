//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IStateFactory.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.creational.abstractfactory.*;

/**
 * Abstract Factory that defines creation for all specific states. 
 * Having the {@link IContext} consume State instances from this 
 * {@link IAbstractFactory} makes possible to extend and customize 
 * the different states or even introduce new ones.
 * 
 * @param <State> Concrete State type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 14, 2009 5:57:58 PM $
 */
public 
interface IStateFactory<State extends IState<?, ?>>
extends IAbstractFactory
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the initial {@link IState}
     * 
     * @return the initial {@link IState}
     */
    public State
    getInitial();
}

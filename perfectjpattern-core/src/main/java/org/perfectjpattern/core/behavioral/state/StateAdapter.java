//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// StateAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.behavioral.state.*;
import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.core.structural.adapter.*;

/**
 * Concrete {@link IAdapter} implementation that adapts any Partial 
 * {@link IState} with default behavior for the undefined
 * methods i.e. this adapter makes possible for concrete {@link IState} 
 * to handle those requests of interest rather than having to implement 
 * the full blown specific {@link IState}
 * 
 * @param <State> Concrete Full State type
 * @param <Partial> Concrete Partial State type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 14, 2009 6:16:28 PM $
 */
public 
class StateAdapter<State extends IState<?, ?>, Partial extends IState<?, ?>>
extends Adapter<State, Partial>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs the {@link StateAdapter} from the target State interface and
     * the Partial state implementation.
     * 
     * @param aStateInterface The full {@link IState} interface 
     * @param anAdaptee Partial implementation of {@link IState}
     * @param aStateAdaptingStrategy {@link IStateAdaptingStrategy} to use
     * @throws IllegalArgumentException 'aTargetInterface' must not be null
     * @throws IllegalArgumentException 'anAdaptee' must not be null
     * @throws IllegalArgumentException 'aStateAdaptingStrategy' must 
     *         not be null
     */
    public 
    StateAdapter(Class<State> aStateInterface, Partial anAdaptee, 
        IStateAdaptingStrategy aStateAdaptingStrategy)
    throws IllegalArgumentException
    {
        super(aStateInterface, anAdaptee, aStateAdaptingStrategy);
    }
}

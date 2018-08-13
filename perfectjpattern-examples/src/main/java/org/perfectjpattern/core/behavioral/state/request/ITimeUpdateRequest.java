//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ITimeUpdateRequest.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.state.request;

import java.util.*;

import org.perfectjpattern.core.api.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.api.*;

/**
 * Fill Order {@link IState}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 19, 2009 12:13:54 PM $
 */
public 
interface ITimeUpdateRequest
extends IRequest
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Time Updates the given Order
     * 
     * @param anFXLimitOrder The Order Context
     * @param aTimestamp The time to update the Order to
     * @throws IllegalArgumentException 'anOrderContext' must not be null
     * @throws IllegalArgumentException 'aTimestamp' must not be null
     */
    public void
    timeUpdate(IFXLimitOrder anFXLimitOrder, Date aTimestamp)
    throws IllegalArgumentException;
}

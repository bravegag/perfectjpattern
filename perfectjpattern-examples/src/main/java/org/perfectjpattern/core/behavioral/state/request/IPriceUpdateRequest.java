//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IPriceUpdateRequest.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.api.*;

/**
 * Reject Order {@link IRequest} 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 19, 2009 12:03:53 PM $
 */
public 
interface IPriceUpdateRequest
extends IRequest
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Price Update the given Order
     * 
     * @param anFXLimitOrder The Order Context
     * @param aBidPrice The market bid price
     * @param anAskPrice The market ask price
     * @throws IllegalArgumentException 'anOrderContext' must not be null
     * @throws IllegalArgumentException 'aBidPrice' must be lower than 
     *                                  'anAskPrice'
     */
    public void
    priceUpdate(IFXLimitOrder anFXLimitOrder, double aBidPrice, 
        double anAskPrice)
    throws IllegalArgumentException;
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// CreatedState.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.state.state;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.api.*;
import org.perfectjpattern.core.behavioral.state.enums.*;
import org.perfectjpattern.core.behavioral.state.request.*;

/**
 * Concrete {@link ICreatedState} implementation. Note that this 
 * implementation will not directly implement {@link ICreatedState} 
 * but the relevant {@link IRequest} only
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 22, 2009 2:41:32 PM $
 */
public 
class CreatedState
extends AbstractState<IOrderState, IFXLimitOrder>
implements IInsertRequest
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    insert(IFXLimitOrder aFXLimitOrder, double aBidPrice, double anAskPrice) 
    throws IllegalArgumentException
    {
        Validate.notNull(aFXLimitOrder, "'aFXLimitOrder' must not be null");
        Validate.isTrue(aBidPrice < anAskPrice, "'aBidPrice' must be lower " +
            "than 'anAskPrice'");        
        
        // in this case the next state can't be determined beforehand without 
        // first evaluating the Context
        IOrderState myNextState = null;        
        if (isValid(aFXLimitOrder, aBidPrice, anAskPrice))
        {            
            myNextState = aFXLimitOrder.getStateFactory().getPending();
        }
        else
        {
            myNextState = aFXLimitOrder.getStateFactory().getRejected();
        }
        
        aFXLimitOrder.setCurrent(myNextState);
    }    
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    /**
     * Returns true if the Order is valid, false otherwise. The validity 
     * criteria works as follows for a Limit Order:
     * <ul>
     * <li>If BuySell == BUY then Order.price < Market.askPrice</li>
     * <li>If BuySell == SELL then Order.price > Market.bidPrice</li>
     * </ul>
     * 
     * @param aFXLimitOrder FX Limit Order
     * @param aBidPrice Market bid price
     * @param anAskPrice Market ask price
     * @return true if the Order is valid, false otherwise
     */
    private boolean
    isValid(IFXLimitOrder aFXLimitOrder, double aBidPrice, double anAskPrice)
    {
        return aFXLimitOrder.getBuySell().getSign() * 
            (aFXLimitOrder.getPrice() -
                (aFXLimitOrder.getBuySell() == BuySellEnum.SELL 
                    ? aBidPrice 
                    : anAskPrice)) < 0;
    }        
}

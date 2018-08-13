//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// PendingState.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.api.*;
import org.perfectjpattern.core.behavioral.state.enums.*;
import org.perfectjpattern.core.behavioral.state.request.*;
import org.slf4j.*;

/**
 * Concrete {@link IPendingState} implementation. Note that this 
 * implementation will not directly implement {@link IPendingState} 
 * but the relevant {@link IRequest} only
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 22, 2009 2:41:32 PM $
 */
public 
class PendingState
extends AbstractState<IOrderState, IFXLimitOrder>
implements ICancelRequest, ITimeUpdateRequest, IPriceUpdateRequest
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    timeUpdate(IFXLimitOrder anFXLimitOrder, Date aTimestamp)
    throws IllegalArgumentException
    {
        Validate.notNull(anFXLimitOrder, "'anOrderContext' must not be null");
        Validate.notNull(aTimestamp, "'aTimestamp' must not be null");
        
        if (anFXLimitOrder.getExpiration().before(aTimestamp) || 
            anFXLimitOrder.getExpiration().equals(aTimestamp))
        {
            // the Order just expired
            anFXLimitOrder.setCurrent(anFXLimitOrder.getStateFactory().
                getExpired());
        }
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    priceUpdate(IFXLimitOrder aFXLimitOrder, double aBidPrice, 
        double anAskPrice)
    throws IllegalArgumentException
    {
        Validate.notNull(aFXLimitOrder, "'aFXLimitOrder' must not be null");
        Validate.isTrue(aBidPrice < anAskPrice, "'aBidPrice' must be lower " +
            "than 'anAskPrice'");        

        if (isCrossing(aFXLimitOrder, aBidPrice, anAskPrice))
        {
            // the Order goes filled
            aFXLimitOrder.setCurrent(aFXLimitOrder.getStateFactory().
                getFilled());
        }
        else
        {
            // keep current state
            aFXLimitOrder.setCurrent(aFXLimitOrder.getStateFactory().
                getPending());            
        }
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void
    cancel(IFXLimitOrder aFXLimitOrder)
    throws IllegalArgumentException
    {
        super.next(aFXLimitOrder, ICancelRequest.class);
    }
     
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    /**
     * Returns true if there is a crossing, false otherwise. The crossing
     * criteria works as follows for a Limit Order:
     * <ul>
     * <li>If BuySell == BUY then Order.price >= Market.askPrice</li>
     * <li>If BuySell == SELL then Order.price <= Market.bidPrice</li>
     * </ul>
     * 
     * @param aFXLimitOrder FX Limit Order
     * @param aBidPrice Market bid price
     * @param anAskPrice Market ask price
     * @return true if there is a crossing, false otherwise
     */
    private boolean
    isCrossing(IFXLimitOrder aFXLimitOrder, double aBidPrice, 
        double anAskPrice)
    {
        return aFXLimitOrder.getBuySell().getSign() * 
            (aFXLimitOrder.getPrice() - 
                (aFXLimitOrder.getBuySell() == BuySellEnum.SELL 
                    ? aBidPrice 
                    : anAskPrice)) >= 0;
    }        
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    @SuppressWarnings("unused")
    private static Logger theLogger = LoggerFactory.getLogger(PendingState.
        class);
}

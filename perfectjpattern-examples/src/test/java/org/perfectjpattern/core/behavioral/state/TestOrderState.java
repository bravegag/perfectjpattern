//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestOrderState.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import org.apache.commons.lang3.time.*;
import org.perfectjpattern.core.behavioral.state.api.*;
import org.perfectjpattern.core.behavioral.state.enums.*;
import org.perfectjpattern.core.behavioral.state.request.*;
import org.perfectjpattern.support.test.*;

/**
 * Test suite for the {@link FXLimitOrder} State Machine
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 29, 2009 6:13:03 PM $
 */
public 
class TestOrderState
extends AbstractTestState<IOrderState, IFXLimitOrder>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testCancel()
    {
        FXLimitOrderStateFactory myFactory = FXLimitOrderStateFactory.
            getInstance();

        // target state expected 
        final IOrderState myPending = myFactory.getPending();

        // source state
        final IOrderState myCanceled = myFactory.getCanceled();
        
        // test transition
        super.testTransition(myPending, ICancelRequest.class, myCanceled);
    }

    //------------------------------------------------------------------------
    public void
    testInsert()
    {
        FXLimitOrderStateFactory myFactory = FXLimitOrderStateFactory.
            getInstance();

        // target state expected 
        final IOrderState myRejected = myFactory.getRejected();

        // source state
        final IOrderState myCreated = myFactory.getCreated();
        
        // test transition
        FXLimitOrder myContext = new FXLimitOrder(myFactory, 300000, 1.35525, 
            BuySellEnum.BUY, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GTC);
        double myBidPrice = 1.35521; 
        double myAskPrice = 1.35523; 
        super.testTransition(myCreated, IInsertRequest.class, myContext, 
            myRejected, myContext, myBidPrice, myAskPrice);

        // target state expected 
        final IOrderState myPending = myFactory.getPending();

        // test transition 
        myContext = new FXLimitOrder(myFactory, 300000, 1.35522, 
            BuySellEnum.BUY, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GTC);
        myBidPrice = 1.35521; 
        myAskPrice = 1.35523; 
        super.testTransition(myCreated, IInsertRequest.class, myContext, 
            myPending, myContext, myBidPrice, myAskPrice);
    }

    //------------------------------------------------------------------------
    public void
    testPurge()
    {
        FXLimitOrderStateFactory myFactory = FXLimitOrderStateFactory.
            getInstance();

        // target state expected 
        final IOrderState myPurged = myFactory.getPurged();

        // source state
        final IOrderState myRejected = myFactory.getRejected();
        final IOrderState myFilled = myFactory.getFilled();
        final IOrderState myExpired = myFactory.getExpired();
        final IOrderState myCanceled = myFactory.getCanceled();
        
        // test transition
        super.testTransition(myRejected, IPurgeRequest.class, myPurged);
        super.testTransition(myFilled, IPurgeRequest.class, myPurged);
        super.testTransition(myExpired, IPurgeRequest.class, myPurged);
        super.testTransition(myCanceled, IPurgeRequest.class, myPurged);
    }
    
    //------------------------------------------------------------------------
    public void
    testPriceUpdate()
    {
        FXLimitOrderStateFactory myFactory = FXLimitOrderStateFactory.
            getInstance();

        // target state expected 
        final IOrderState myFilled = myFactory.getFilled();

        // source state
        final IOrderState myPending = myFactory.getPending();

        // test transition
        FXLimitOrder myContext = new FXLimitOrder(myFactory, 300000, 1.35525, 
            BuySellEnum.BUY, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GTC);
        double myBidPrice = 1.35521; 
        double myAskPrice = 1.35523; 
        super.testTransition(myPending, IPriceUpdateRequest.class, myContext, 
            myFilled, myContext, myBidPrice, myAskPrice);

        // test transition
        myContext = new FXLimitOrder(myFactory, 300000, 1.35522, 
            BuySellEnum.BUY, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GTC);
        myBidPrice = 1.35521; 
        myAskPrice = 1.35523; 
        super.testTransition(myPending, IPriceUpdateRequest.class, myContext, 
            myPending, myContext, myBidPrice, myAskPrice);
    }    

    //------------------------------------------------------------------------
    public void
    testTimeUpdate()
    {
        FXLimitOrderStateFactory myFactory = FXLimitOrderStateFactory.
            getInstance();

        // target state expected 
        final IOrderState myExpired = myFactory.getExpired();

        // source state
        final IOrderState myPending = myFactory.getPending();

        // test transition
        FXLimitOrder myContext = new FXLimitOrder(myFactory, 200000, 1.35520, 
            BuySellEnum.BUY, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GFD);
        Date myTimestamp = DateUtils.addDays(new Date(), 1); 
        super.testTransition(myPending, ITimeUpdateRequest.class, myContext, 
            myExpired, myContext, myTimestamp);
    }    
}

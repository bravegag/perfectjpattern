//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Example.java Copyright (c) 2012 Giovanni Azua Garcia
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
import org.perfectjpattern.core.api.structural.composite.*;
import org.perfectjpattern.core.behavioral.state.api.*;
import org.perfectjpattern.core.behavioral.state.enums.*;
import org.perfectjpattern.core.structural.composite.*;
import org.slf4j.*;

/**
 * Startup Main for the State Pattern Example code
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 3, 2009 12:01:41 PM $
 */
// CHECKSTYLE:OFF
public final
class Example
// CHECKSTYLE:ON
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    // CHECKSTYLE:OFF
    public static void
    main(String[] anArguments)
    // CHECKSTYLE:ON
    {        
        //---------------------------------------------------------------
        // Define the State Abstract Factory in use
        //---------------------------------------------------------------
        FXLimitOrderStateFactory myFactory = FXLimitOrderStateFactory.
            getInstance();
        
        //---------------------------------------------------------------
        // Create mutiple Forex Limit Orders
        //---------------------------------------------------------------
        IFXLimitOrder myOrderNo1 = new FXLimitOrder(myFactory, 100000, 1.35510, 
            BuySellEnum.BUY, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GTC);
        
        IFXLimitOrder myOrderNo2 = new FXLimitOrder(myFactory, 200000, 1.35520, 
            BuySellEnum.BUY, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GFD);
        
        IFXLimitOrder myOrderNo3 = new FXLimitOrder(myFactory, 300000, 1.35525, 
            BuySellEnum.BUY, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GTC);
        
        IFXLimitOrder myOrderNo4 = new FXLimitOrder(myFactory, 100000, 1.35610, 
            BuySellEnum.SELL, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GFD);
        
        IFXLimitOrder myOrderNo5 = new FXLimitOrder(myFactory, 200000, 1.35615, 
            BuySellEnum.SELL, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GTC);
        
        IFXLimitOrder myOrderNo6 = new FXLimitOrder(myFactory, 300000, 1.35515, 
            BuySellEnum.SELL, CurrencyPairEnum.EUR_USD, ExpiryTypeEnum.GFD);
                
        //---------------------------------------------------------------
        // create Composite of Orders so they are easier to manipulate
        //---------------------------------------------------------------
        IComposite<IFXLimitOrder> myComposite = new Composite<IFXLimitOrder>(
            IFXLimitOrder.class);
        myComposite.addAll(myOrderNo1, myOrderNo2, myOrderNo3, myOrderNo4, 
            myOrderNo5, myOrderNo6);
        
        //---------------------------------------------------------------
        // print Orders
        //---------------------------------------------------------------
        StringBuilder myBuilder = new StringBuilder();
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);
        myBuilder.append("All Orders in Initial State");
        myBuilder.append(LINE_BREAK);
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);
        print(myComposite, myBuilder);
        
        //---------------------------------------------------------------
        // retrieve the Component View of the Composite
        //---------------------------------------------------------------
        IFXLimitOrder myOrders = myComposite.getComponent();        

        //---------------------------------------------------------------
        // inserts all Orders/ emulates customer inserting the Order
        // in the trading platform. To validate an Order during insertion, 
        // it is required the current market price for that currency pair
        //---------------------------------------------------------------
        double myBidPrice = 1.35521; 
        double myAskPrice = 1.35523; 
        
        //---------------------------------------------------------------
        // Note how this Bid/Ask price of 1.35521/1.35523 will trigger 
        // a REJECT in the following Orders:
        //
        //    - myOrderNo3: BUY Order with price higher than the market ask
        //    - myOrderNo6: SELL Order with price lower than the market bid
        //---------------------------------------------------------------
        myOrders.insert(myBidPrice, myAskPrice);
                
        //---------------------------------------------------------------
        // print, then remove all REJECTED Orders
        //---------------------------------------------------------------
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);        
        myBuilder.append("All Orders after Insert Request");
        myBuilder.append(LINE_BREAK);        
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);        
        print(myComposite, myBuilder);
        removeByState(myComposite, myFactory.getRejected());
        
        //---------------------------------------------------------------
        // emulates receiving market price tick updates
        //---------------------------------------------------------------
        myBidPrice = 1.35523; 
        myAskPrice = 1.35524; 
        myOrders.priceUpdate(myBidPrice, myAskPrice);
        
        myBidPrice = 1.35528; 
        myAskPrice = 1.35530; 
        myOrders.priceUpdate(myBidPrice, myAskPrice);

        myBidPrice = 1.35621; 
        myAskPrice = 1.35623; 
        myOrders.priceUpdate(myBidPrice, myAskPrice);
        
        //---------------------------------------------------------------
        // print, then remove all FILLED Orders
        //---------------------------------------------------------------
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);        
        myBuilder.append("Remaining Orders after Price Updates");
        myBuilder.append(LINE_BREAK);        
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);        
        print(myComposite, myBuilder);
        removeByState(myComposite, myFactory.getFilled());

        //---------------------------------------------------------------
        // send Time update => should trigger expiration in myOrderNo2
        //---------------------------------------------------------------
        Date myTimestamp = DateUtils.addDays(new Date(), 1); 
        myOrders.timeUpdate(myTimestamp);        

        //---------------------------------------------------------------
        // lets also cancel Order #1
        //---------------------------------------------------------------
        myOrderNo1.cancel();
        
        //---------------------------------------------------------------
        // print Orders
        //---------------------------------------------------------------
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);                
        myBuilder.append("Orders after Time Update and Cancel");
        myBuilder.append(LINE_BREAK);                
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);                
        print(myComposite, myBuilder);

        //---------------------------------------------------------------
        // purge remaining Orders
        //---------------------------------------------------------------
        myOrders.purge();
        
        //---------------------------------------------------------------
        // check that remaining Order are in final state
        //---------------------------------------------------------------
        for (IFXLimitOrder myOrder : myComposite)
        {
            boolean myFinal = myOrder.getCurrent().isFinal();
            
            assert myFinal : "PurgedState must be the final one";
        }

        //---------------------------------------------------------------
        // print Orders
        //---------------------------------------------------------------
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);        
        myBuilder.append("Orders after purge request");
        myBuilder.append(LINE_BREAK);        
        myBuilder.append("***************************************************");
        myBuilder.append("***********************************");
        myBuilder.append(LINE_BREAK);        
        print(myComposite, myBuilder);
        
        theLogger.debug(myBuilder.toString());
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    protected static void
    setLogger(Logger aLogger)
    {
        theLogger = aLogger;
    }
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    private static void
    print(IComposite<IFXLimitOrder> aComposite, StringBuilder aBuilder)
    {                
        for (int i = 0; i < aComposite.size(); i++)
        {
            IFXLimitOrder myOrder = aComposite.get(i);
            
            aBuilder.append(myOrder.toString());
            aBuilder.append(LINE_BREAK);
        }        
    }

    //------------------------------------------------------------------------
    private static void
    removeByState(IComposite<IFXLimitOrder> aComposite, 
        IOrderState anOrderState)
    {                
        for (int i = aComposite.size() - 1; i >= 0; i--)
        {
            IFXLimitOrder myOrder = aComposite.get(i);
            
            if (myOrder.getCurrent() == anOrderState)
            {
                aComposite.remove(i);
            }
        }        
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static Logger theLogger = LoggerFactory.getLogger(Example.class);
    private static final String LINE_BREAK = System.getProperty(
        "line.separator");            
}
//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestExample.java Copyright (c) 2012 Giovanni Azua Garcia
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

import static org.easymock.EasyMock.*;
import junit.framework.*;

import org.slf4j.*;

/**
 * Test suite for the State Pattern Example
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 24, 2009 11:04:35 PM $
 */
public 
class TestExample
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testState()
    {
        Logger myLoggerMock = createNiceMock(Logger.class);
                
        String myLogging = 
            "*************************************************************" +
                "*************************"  + LINE_BREAK + 
            "All Orders in Initial State"  + LINE_BREAK +  
            "**************************************************************" +
                "************************"  + LINE_BREAK + 
            "FXLimitOrder[State=CreatedState,Quantity=100000,Price=1.3551," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK +
            "FXLimitOrder[State=CreatedState,Quantity=200000,Price=1.3552," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "FXLimitOrder[State=CreatedState,Quantity=300000,Price=1.35525," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK +
            "FXLimitOrder[State=CreatedState,Quantity=100000,Price=1.3561," +
            "BuySell=SELL,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "FXLimitOrder[State=CreatedState,Quantity=200000,Price=1.35615," +
            "BuySell=SELL,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK + 
            "FXLimitOrder[State=CreatedState,Quantity=300000,Price=1.35515," +
            "BuySell=SELL,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "**************************************************************" +
            "************************" + LINE_BREAK + 
            "All Orders after Insert Request" + LINE_BREAK + 
            "**************************************************************" +
            "************************" + LINE_BREAK +  
            "FXLimitOrder[State=PendingState,Quantity=100000,Price=1.3551," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK +  
            "FXLimitOrder[State=PendingState,Quantity=200000,Price=1.3552," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "FXLimitOrder[State=RejectedState,Quantity=300000,Price=1.35525," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK + 
            "FXLimitOrder[State=PendingState,Quantity=100000,Price=1.3561," +
            "BuySell=SELL,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "FXLimitOrder[State=PendingState,Quantity=200000,Price=1.35615," +
            "BuySell=SELL,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]"  + LINE_BREAK + 
            "FXLimitOrder[State=RejectedState,Quantity=300000,Price=1.35515," +
            "BuySell=SELL,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "***************************************************************" +
            "***********************" + LINE_BREAK + 
            "Remaining Orders after Price Updates" + LINE_BREAK + 
            "***************************************************************" +
            "***********************" + LINE_BREAK +  
            "FXLimitOrder[State=PendingState,Quantity=100000,Price=1.3551," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK +  
            "FXLimitOrder[State=PendingState,Quantity=200000,Price=1.3552," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "FXLimitOrder[State=FilledState,Quantity=100000,Price=1.3561," +
            "BuySell=SELL,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "FXLimitOrder[State=FilledState,Quantity=200000,Price=1.35615," +
            "BuySell=SELL,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK + 
            "***************************************************************" +
            "***********************" + LINE_BREAK + 
            "Orders after Time Update and Cancel" + LINE_BREAK + 
            "***************************************************************" +
            "***********************" + LINE_BREAK +  
            "FXLimitOrder[State=CanceledState,Quantity=100000,Price=1.3551," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK +  
            "FXLimitOrder[State=ExpiredState,Quantity=200000,Price=1.3552," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK + 
            "***************************************************************" +
            "***********************" + LINE_BREAK + 
            "Orders after purge request" + LINE_BREAK + 
            "****************************************************************" +
            "**********************" + LINE_BREAK +  
            "FXLimitOrder[State=PurgedState,Quantity=100000,Price=1.3551," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good Until " +
                "Canceled]" + LINE_BREAK +  
            "FXLimitOrder[State=PurgedState,Quantity=200000,Price=1.3552," +
            "BuySell=BUY,CurrencyPair=EUR_USD,Expiry Type=Good For Date]" + 
                LINE_BREAK;
            
         myLoggerMock.debug(myLogging);
            
        replay(myLoggerMock);
        
        Example.setLogger(myLoggerMock);
        
        Example.main(new String[0]);
        
        verify(myLoggerMock);
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final String LINE_BREAK = System.getProperty(
        "line.separator");        
}

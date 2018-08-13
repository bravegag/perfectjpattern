//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IFXLimitOrder.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.state.api;

import java.util.*;

import org.perfectjpattern.core.api.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.enums.*;

/**
 * Context-specific definition of {@link IState} corresponding to 
 * an Order State Machine. Defines the Context made available to the
 * different {@link IOrderState}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 19, 2009 11:53:43 AM $
 */
public 
interface IFXLimitOrder
extends IContext<IOrderState, FXLimitOrderStateFactory>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the quantity
     *
     * @return the quantity
     */
    public int 
    getQuantity();

    //------------------------------------------------------------------------
    /**
     * Returns the price
     *
     * @return the price
     */
    public double 
    getPrice();

    //------------------------------------------------------------------------
    /**
     * Returns the expiration
     *
     * @return the expiration
     */
    public Date 
    getExpiration();

    //------------------------------------------------------------------------
    /**
     * Returns the buySell
     *
     * @return the buySell
     */
    public BuySellEnum 
    getBuySell();

    //------------------------------------------------------------------------
    /**
     * Returns the currencyPair
     *
     * @return the currencyPair
     */
    public CurrencyPairEnum 
    getCurrencyPair();
    
    //------------------------------------------------------------------------
    /**
     * Handles insert Order into the platform
     * 
     * @param aBidPrice The market bid price 
     * @param anAskPrice The market ask price
     * @throws IllegalArgumentException 'aBidPrice' must be lower than 
     *                                  'anAskPrice'
     */
    public void 
    insert(double aBidPrice, double anAskPrice)
    throws IllegalArgumentException;

    //------------------------------------------------------------------------
    /**
     * Handles market price updates
     * 
     * @param aBidPrice The market bid price 
     * @param anAskPrice The market ask price
     * @throws IllegalArgumentException 'aBidPrice' must be lower than 
     *                                  'anAskPrice'
     */
    public void 
    priceUpdate(double aBidPrice, double anAskPrice)
    throws IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Handles time updates
     * 
     * @param aTimestamp The time update
     */
    public void 
    timeUpdate(Date aTimestamp);
    
    //------------------------------------------------------------------------
    /**
     * Handles purge Order request
     */
    public void 
    purge();        

    //------------------------------------------------------------------------
    /**
     * Handles cancel Order request
     */
    public void 
    cancel();        
}

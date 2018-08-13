//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// FXLimitOrder.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.apache.commons.lang3.builder.*;
import org.perfectjpattern.core.behavioral.state.api.*;
import org.perfectjpattern.core.behavioral.state.enums.*;

/**
 * Forex Limit Order Model
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 22, 2009 2:22:26 PM $
 */
public final 
class FXLimitOrder
extends AbstractContext<IOrderState, FXLimitOrderStateFactory>
implements IFXLimitOrder
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    FXLimitOrder(FXLimitOrderStateFactory aStateFactory, int aQuantity, 
        double aPrice, BuySellEnum aBuySell, CurrencyPairEnum aCurrencyPair, 
            ExpiryTypeEnum anExpiryType)
    {
        super(aStateFactory);
        
        theQuantity = aQuantity;
        thePrice = aPrice;
        theExpiryType = anExpiryType;
        theExpiration = anExpiryType.createExpiration();
        theBuySell = aBuySell;
        theCurrencyPair = aCurrencyPair;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final int 
    getQuantity()
    {
        return theQuantity;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final double 
    getPrice()
    {
        return thePrice;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final Date 
    getExpiration()
    {
        return theExpiration;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final BuySellEnum 
    getBuySell()
    {
        return theBuySell;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final CurrencyPairEnum 
    getCurrencyPair()
    {
        return theCurrencyPair;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final ExpiryTypeEnum 
    getExpiryType()
    {
        return theExpiryType;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    insert(double aBidPrice, double anAskPrice)
    throws IllegalArgumentException
    {        
        getCurrent().insert(this, aBidPrice, anAskPrice);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    priceUpdate(double aBidPrice, double anAskPrice)
    {
        getCurrent().priceUpdate(this, aBidPrice, anAskPrice);
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    timeUpdate(Date aTimestamp)
    {
        getCurrent().timeUpdate(this, aTimestamp);
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    purge()
    {
        getCurrent().purge(this);
    }   
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    cancel()
    {
        getCurrent().cancel(this);
    }   

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public String 
    toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
            append("State", getCurrent()).
            append("Quantity", getQuantity()).
            append("Price", getPrice()).
            append("BuySell", getBuySell()).
            append("CurrencyPair", getCurrencyPair()).
            append("Expiry Type", getExpiryType()).
                toString();
    }   

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final int theQuantity;
    private final double thePrice;
    private final Date theExpiration;
    private final BuySellEnum theBuySell;
    private final CurrencyPairEnum theCurrencyPair;
    private final ExpiryTypeEnum theExpiryType;
}

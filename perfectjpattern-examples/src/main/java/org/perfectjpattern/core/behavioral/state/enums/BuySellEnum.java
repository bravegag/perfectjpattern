//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// BuySellEnum.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.state.enums;

/**
 * Buy/Sell Enum 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 22, 2009 2:25:09 PM $
 */
public 
enum BuySellEnum
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    BUY
    {
        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        @Override
        public int
        getSign()
        {
            return +1;
        }
    }, 
    SELL
    {
        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        @Override
        public int
        getSign()
        {
            return -1;
        }        
    };
    
    //------------------------------------------------------------------------
    /**
     * Returns the multiplying factor that assists in the formula for 
     * validating an order. The multiplying factor is either -1 or +1
     * 
     * @return the multiplying factor -1 or +1
     */
    public abstract int
    getSign();
}

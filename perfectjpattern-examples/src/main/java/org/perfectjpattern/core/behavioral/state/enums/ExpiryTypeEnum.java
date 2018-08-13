//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ExpiryTypeEnum.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import org.apache.commons.lang3.time.*;

/**
 * Expiry Type Enum
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 22, 2009 2:26:25 PM $
 */
public 
enum ExpiryTypeEnum
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    GTC
    {
        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        @Override        
        public Date 
        createExpiration()
        {
            // lets get a nearly impossible expiration date
            return theFarthestDate;
        }        
        
        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        @Override
        public String 
        toString()
        {
            return "Good Until Canceled";
        }        
        
        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private final Date theFarthestDate = new Date(Long.MAX_VALUE);
    }, 
    GFD
    {
        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        @Override
        public Date 
        createExpiration()
        {
            Date myTomorrow = DateUtils.addDays(new Date(), +1);
            
            return DateUtils.truncate(myTomorrow, Calendar.DATE);
        }        

        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        @Override
        public String 
        toString()
        {
            return "Good For Date";
        }        
    };

    //------------------------------------------------------------------------
    /**
     * Returns Expiration Date corresponding to the Expiry Type
     * 
     * @return Expiration Date corresponding to the Expiry Type
     */
    public abstract Date 
    createExpiration();
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// CompositeOrder.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.integration.dao;

import java.lang.reflect.*;

import org.perfectjpattern.core.structural.composite.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Composite {@link Order} that knows how to aggregate composite 
 * {@link #toString()}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 17, 2009 10:55:58 PM $
 */
public 
class CompositeOrder
extends Composite<IOrder>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Creates a {@link CompositeOrder}
     */
    public 
    CompositeOrder()
    {
        super(IOrder.class);
    }    
    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected Object 
    aggregate(Method aMethod, Object[] aResults)
    {
        // assumes that this is only triggered by the toString method
        assert aMethod.getName() == "toString" : 
            "Unexpected method to aggregate '" + aMethod.getName() + "'";
        
        StringBuilder myOrderByClause = new StringBuilder();
        
        for (Object myOrderCriteria : aResults)
        {
            myOrderByClause.append(myOrderCriteria);
            myOrderByClause.append(", ");
        }                

        myOrderByClause.delete(myOrderByClause.length() - 2, 
            myOrderByClause.length());
        
        return myOrderByClause.toString();
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Serial version ID 
     */
    private static final long serialVersionUID = 1L;
}

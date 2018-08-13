//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ITransactionStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.integration.dao;

import org.perfectjpattern.core.api.behavioral.strategy.*;

/**
 * Abstract {@link IStrategy} reusable by {@link IGenericDao} that defines:
 * <ul>
 * <li>Defines retrieval of instances of JPA implementation-specific 
 * <code>Transaction</code></li>
 * </ul>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 8, 2009 2:22:30 PM $
 */
public 
interface ITransactionStrategy
extends IStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the adapted JPA implementation-specific <code>Transaction</code> 
     * 
     * @return adapted JPA implementation-specific <code>Transaction</code>
     */
    public ITransaction
    getTransaction();

    //------------------------------------------------------------------------
    /**
     * Returns true if the transaction handled by this 
     * {@link ITransactionStrategy} is Container Managed Transaction CMT, 
     * false otherwise
     * 
     * @return true if the transaction handled by this 
     *         {@link ITransactionStrategy} is Container Managed Transaction 
     *         CMT, false otherwise
     */
    public boolean
    isManaged();
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestManagedTransactionStrategy.java Copyright (c) 2012 
// Giovanni Azua Garcia bravegag@hotmail.com
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

import junit.framework.*;

import org.easymock.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Test suite for the {@link ManagedTransactionStrategy}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 12, 2009 9:43:51 PM $
 */
public 
class TestManagedTransactionStrategy
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testGetTransaction()
    {
        ITransaction myMockTransaction = EasyMock.createNiceMock(
            ITransaction.class); 
        ISession mySession = EasyMock.createNiceMock(ISession.class); 
        ISessionStrategy myMockSessionStrategy = EasyMock.createNiceMock(
            ISessionStrategy.class); 

        EasyMock.expect(myMockSessionStrategy.getSession()).andReturn(
            mySession);
        EasyMock.expect(mySession.getTransaction()).andReturn(
            myMockTransaction);
        EasyMock.replay(myMockSessionStrategy, mySession);
        
        ManagedTransactionStrategy myTransactionStrategy = 
            new ManagedTransactionStrategy();
        myTransactionStrategy.setSessionStrategy(myMockSessionStrategy);
        
        ITransaction myTransaction = myTransactionStrategy.getTransaction();
        
        assertNotNull("Transaction is expected not to be null", myTransaction);
    }
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestJtaTransactionStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import junit.framework.*;

import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Test suite for the {@link JtaTransactionStrategy} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 20, 2009 3:48:46 AM $
 */
public 
class TestJtaTransactionStrategy
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testGetTransaction()
    {
        System.setProperty("log4j.category.OpenEJB", "debug"); 
        
        ITransactionStrategy myTransactionStrategy = 
            new JtaTransactionStrategy();

        assertNotNull("TransactionStrategy must not be null", 
            myTransactionStrategy);
        
        // TODO: fails with javax.naming.NameNotFoundException
        // "java:comp/UserTransaction" seems to be a bug in OpenEJB        
        // http://issues.apache.org/jira/browse/OPENEJB-960
        // additionally I get the following popping out in the logs
        // ERROR - JAVA AGENT NOT INSTALLED. The JPA Persistence Provider 
        // requested installation of a ClassFileTransformer which requires a 
        // JavaAgent.  See http://openejb.apache.org/3.0/javaagent.html
        
//        ITransaction myTransaction = myTransactionStrategy.getTransaction();
//        
//        assertNotNull("Transaction must not be null", myTransaction);
//        
//        myTransaction.begin();
//        
//        myTransaction.rollback();
    }
}
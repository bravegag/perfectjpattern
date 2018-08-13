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
package org.perfectjpattern.jee.integration.dao.hibernate;

import static org.easymock.EasyMock.*;

import java.text.*;

import junit.framework.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.integration.dao.*;
import org.slf4j.*;

/**
 * Hibernate Generic DAO example test suite 
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 20, 2008 1:58:38 PM $
 */
public 
class TestExample
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testHibernateGenericDao()
    throws ParseException
    {
        Logger myLoggerMock = createNiceMock(Logger.class);
        Example.setLogger(myLoggerMock);

        String myLogging = LINE_BREAK +
        "**************************************************************" + 
            LINE_BREAK +
        "| Customer(id='1', name='Ernesto')" + LINE_BREAK +
        "|--> Order(id='1', date='3/14/07 8:00 AM')" + LINE_BREAK +
        "|    |-> Product(id='2', name='Nikon D300', list price='2,000')" + 
            LINE_BREAK +
        "|    |-> Product(id='3', name='Nikon 80-400mm', list price='1,500')" + 
            LINE_BREAK +
        "|--> Order(id='2', date='10/19/07 8:00 AM')" + LINE_BREAK +
        "|    |-> Product(id='4', name='Nikon Fisheye 10.5mm', " +
            "list price='900')" + LINE_BREAK +
        "**************************************************************";
        
        myLoggerMock.debug(myLogging);
                        
        replay(myLoggerMock);
                
        Example.main(new String[0]);
        
        verify(myLoggerMock);
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected void 
    tearDown() 
    throws Exception
    {
        super.tearDown();
        
        IGenericDaoFactory myDaoFactory = HibernateDaoFactory.getInstance();

        try
        {
            // renew 
            HibernateCurrentSessionStrategy mySessionStrategy = 
                new HibernateCurrentSessionStrategy();
            HibernateConfiguredTransactionStrategy myTransactionStrategy = 
                new HibernateConfiguredTransactionStrategy(mySessionStrategy);
            
            myDaoFactory.setSessionStrategy(mySessionStrategy);
            myDaoFactory.setTransactionStrategy(myTransactionStrategy);
            
            IGenericDao<Long, Customer> myCustomerDao = myDaoFactory.
                createDao(Customer.class);
            myCustomerDao.deleteAll();
        }
        finally 
        {
            myDaoFactory.shutdown();
        }
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final String LINE_BREAK = System.getProperty(
        "line.separator");    
}

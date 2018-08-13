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
package org.perfectjpattern.jee.integration.dao.spring;

import static org.easymock.EasyMock.*;

import java.text.*;

import junit.framework.*;

import org.slf4j.*;
import org.springframework.transaction.annotation.*;

/**
 * Spring-Hibernate Generic DAO example test suite 
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 20, 2008 1:58:38 PM $
 */
@Transactional
public 
class TestExample
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testSpringGenericDao()
    throws ParseException
    {
        Logger myLoggerMock = createNiceMock(Logger.class);
        Example.setLogger(myLoggerMock);

        String myLogging = LINE_BREAK +        
        "**************************************************************" + 
            LINE_BREAK + 
        "| Customer(id='2', name='Giovanni')" + LINE_BREAK + 
        "|--> Order(id='2', date='11/17/07 8:00 AM')" + LINE_BREAK + 
        "|    |-> Product(id='4', name='Nikon D40x', list price='350')" + 
            LINE_BREAK + 
        "|    |-> Product(id='5', name='Nikon D300', list price='2,000')" + 
            LINE_BREAK + 
        "|    |-> Product(id='2', name='Nikon D700', list price='3,200')" + 
            LINE_BREAK + 
        "|--> Order(id='3', date='1/15/08 5:35 PM')" + LINE_BREAK + 
        "|    |-> Product(id='1', name='Nikon 70-200mm', " +
            "list price='1,800')" + LINE_BREAK + 
        "|    |-> Product(id='6', name='Nikon 17-55mm', " +
            "list price='1,200')" + LINE_BREAK + 
        "|    |-> Product(id='3', name='Nikon 80-400mm', " +
            "list price='1,500')" + LINE_BREAK + 
        "**************************************************************";
        
        myLoggerMock.debug(myLogging);
                        
        replay(myLoggerMock);
                
        Example.main(new String[0]);
        
        verify(myLoggerMock);
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final String LINE_BREAK = System.getProperty(
        "line.separator");
}

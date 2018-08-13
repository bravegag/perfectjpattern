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
package org.perfectjpattern.jee.integration.dao.jpa;

import static org.easymock.EasyMock.*;
import junit.framework.*;

import org.slf4j.*;

/**
 * JPA CMP Generic DAO example test suite 
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
    testJpaCmpGenericDao()
    {
        Logger myLoggerMock = createNiceMock(Logger.class);
        Example.setLogger(myLoggerMock);

        String myLogging = LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + 
        "| Movie(id='1', title='Tomas Gutierrez Alea', director='Fresa y " +
            "chocolate', year='1,994')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + 
        "| Movie(id='2', title='Roberto Benigni', director='Vita e bella, " +
            "La', year='1,997')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + 
        "| Movie(id='3', title='Pierre Morel', director='Taken', " +
            "year='2,008')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + 
        "| Movie(id='3', title='Pierre Morel', director='Taken', year='2,008')";
        
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

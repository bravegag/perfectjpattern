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
package org.perfectjpattern.jee.business.valuelisthandler;

import static org.easymock.EasyMock.*;
import junit.framework.*;

import org.slf4j.*;

/**
 * Test suite for the Value List Handler example
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 20, 2009 11:01:52 PM $
 */
public 
class TestExample
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testValueListHandler()
    {
        Logger myLoggerMock = createNiceMock(Logger.class);
        Example.setLogger(myLoggerMock);

        String myLogging = LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='10', name='Amor de Loca Juventud')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='13', name='Buena Vista Social Club')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='9', name='Candela')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='1', name='Chan Chan')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='2', name='De Camino a La Vereda')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='5', name='Dos Gardenias')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='8', name='El Carretero')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='3', name='El Cuarto de Tula')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='14', name='La Bayamesa')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='12', name='Murmullo')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='11', name='Orgullecida')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='4', name='Pueblo Nuevo')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='7', name='Veinte Anos')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='6', name='Y Tu Que Has Hecho ?')" + 
            LINE_BREAK + 
        "**************************************************************" +
            LINE_BREAK + "| Song(id='10', name='Amor de Loca Juventud')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='13', name='Buena Vista Social Club')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='9', name='Candela')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='1', name='Chan Chan')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='2', name='De Camino a La Vereda')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='5', name='Dos Gardenias')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='8', name='El Carretero')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='3', name='El Cuarto de Tula')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='14', name='La Bayamesa')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='12', name='Murmullo')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='11', name='Orgullecida')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='4', name='Pueblo Nuevo')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='7', name='Veinte Anos')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='6', name='Y Tu Que Has Hecho ?')" + 
            LINE_BREAK + 
        "**************************************************************" +
            LINE_BREAK + "| Song(id='10', name='Amor de Loca Juventud')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='13', name='Buena Vista Social Club')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='9', name='Candela')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='1', name='Chan Chan')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='2', name='De Camino a La Vereda')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='5', name='Dos Gardenias')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='8', name='El Carretero')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='3', name='El Cuarto de Tula')" + 
            LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='14', name='La Bayamesa')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='12', name='Murmullo')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='11', name='Orgullecida')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='4', name='Pueblo Nuevo')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='7', name='Veinte Anos')" + LINE_BREAK + 
        "**************************************************************" + 
            LINE_BREAK + "| Song(id='6', name='Y Tu Que Has Hecho ?')";
        
        myLoggerMock.debug(myLogging);
                        
//        replay(myLoggerMock);
//                
//        Example.main(new String[0]);
//        
//        verify(myLoggerMock);        
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final String LINE_BREAK = System.getProperty(
        "line.separator");        
}

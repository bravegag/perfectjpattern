//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestGenericValueListHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import junit.framework.*;

import org.easymock.*;
import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.business.valuelisthandler.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.integration.dao.*;

/**
 * Test suite for {@link IGenericValueListHandler}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 19, 2009 1:33:11 PM $
 */
public 
class TestGenericValueListHandler
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public void
    testQueryByExample()
    {
        // create base read-only Dao instance
        IGenericReadOnlyDao<Long, Person> myPersonDao = EasyMock.createNiceMock(
            IGenericReadOnlyDao.class);

        // create example
        Person myExample = new Person();
        myExample.setName("Ernesto");
        
        EasyMock.expect(myPersonDao.findByExample(myExample)).andReturn(
           new ArrayList<Person>());
        
        EasyMock.replay(myPersonDao);
        
        // create Value List Handler
        IGenericValueListHandler<Person> myValueListHandler = 
            new GenericValueListHandler<Person>(myPersonDao);
        
        // execute query
        myValueListHandler.executeQueryByExample(myExample);

        // run assertions
        EasyMock.verify(myPersonDao);
    }

    //------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public void
    testQueryByExampleWithOrder()
    {
        // create base read-only Dao instance
        IGenericReadOnlyDao<Long, Person> myPersonDao = EasyMock.createNiceMock(
            IGenericReadOnlyDao.class);

        // create example
        Person myExample = new Person();
        myExample.setName("Ernesto");
        
        // setup Order
        IOrder myOrder = new Order("name");        
        
        EasyMock.expect(myPersonDao.findByExample(myExample, myOrder)).
           andReturn(new ArrayList<Person>());
        
        EasyMock.replay(myPersonDao);
        
        // create Value List Handler
        IGenericValueListHandler<Person> myValueListHandler = 
            new GenericValueListHandler<Person>(myPersonDao);
        
        // execute query
        myValueListHandler.executeQueryByExample(myExample, myOrder);

        // run assertions
        EasyMock.verify(myPersonDao);
    }
}

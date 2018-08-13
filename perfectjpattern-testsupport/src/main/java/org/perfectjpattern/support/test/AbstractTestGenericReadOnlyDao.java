//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractTestGenericReadOnlyDao.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.support.test;

import java.util.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Abstract reusable Test suite for any {@link IGenericReadOnlyDao} 
 * implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: May 19, 2009 9:00:47 PM $
 */
public abstract 
class AbstractTestGenericReadOnlyDao
extends AbstractTestBaseReadOnlyDao
{
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs a new {@link AbstractTestGenericReadOnlyDao} from the given 
     * {@link IGenericDaoFactory} instance
     * 
     * @param aGenericDaoFactory {@link IGenericDaoFactory} instance
     * @throws IllegalArgumentException 'aBaseDaoFactory' must not be null
     */
    protected 
    AbstractTestGenericReadOnlyDao(IGenericDaoFactory aGenericDaoFactory)
    throws IllegalArgumentException
    {
        super(aGenericDaoFactory);
        
        theGenericDaoFactory = aGenericDaoFactory;
    }
    
    //------------------------------------------------------------------------
    public void
    testFindByExample()
    {
        // create generic read-only Dao instance
        IGenericReadOnlyDao<Long, Person> myPersonDao = theGenericDaoFactory.
            createReadOnlyDao(Person.class);
     
        // prepare example
        Person myExample = new Person();
        myExample.setName("Pedro");
        myExample.setAge(45);
        
        // find by example
        List<Person> myPersons = myPersonDao.findByExample(myExample);

        // run assertions
        assertNotNull("findByExample did not find the expected Persons", 
            myPersons);
        assertEquals("findByExample did not find the expected Persons", 1, 
            myPersons.size());
        assertEquals("findByExample did not find the expected Persons", 
            "Pedro", myPersons.get(0).getName());

        // find by example excluding properties
        myExample.setAge(-1);
        myPersons = myPersonDao.findByExample(myExample, "age");

        // run assertions
        assertNotNull("findByExample did not find the expected Persons", 
            myPersons);
        assertEquals("findByExample did not find the expected Persons", 1, 
            myPersons.size());
        assertEquals("findByExample did not find the expected Persons", 
            "Pedro", myPersons.get(0).getName());    
    }
    
    //------------------------------------------------------------------------
    public void
    testFindByExampleOrdered(IOrder aCompositeOrder)
    {
        // create generic read-only Dao instance
        IGenericReadOnlyDao<Long, Person> myPersonDao = theGenericDaoFactory.
            createReadOnlyDao(Person.class);
     
        // prepare example
        Person myExample = new Person();
        myExample.setName("Pedro");
        myExample.setAge(45);
                
        // find by example        
        List<Person> myPersons = myPersonDao.findByExample(myExample, 
            aCompositeOrder);

        // run assertions
        assertNotNull("findByExample did not find the expected Persons", 
            myPersons);
        assertEquals("findByExample did not find the expected Persons", 1, 
            myPersons.size());
        assertEquals("findByExample did not find the expected Persons", 
            "Pedro", myPersons.get(0).getName());

        // find by example excluding properties
        myExample.setAge(-1);
        myPersons = myPersonDao.findByExample(myExample, aCompositeOrder, 
            "age");

        // run assertions
        assertNotNull("findByExample did not find the expected Persons", 
            myPersons);
        assertEquals("findByExample did not find the expected Persons", 1, 
            myPersons.size());
        assertEquals("findByExample did not find the expected Persons", 
            "Pedro", myPersons.get(0).getName());    
    }
    
    //------------------------------------------------------------------------
    public void
    testFindByPageAndExample(IOrder aCompositeOrder, IOrder aNameOrder)
    {
        // create generic read-only Dao instance
        IGenericReadOnlyDao<Long, Person> myPersonDao = theGenericDaoFactory.
            createReadOnlyDao(Person.class);
     
        // find by Page
        int myPageSize = 50;
        int myPageIndex = 1;
        
        // setup example
        Person myExample = new Person();
        myExample.setName("Pedro");
                
        String myExcludeProperties = "age";
        List<Person> myPersons = myPersonDao.findByPage(myPageSize, myPageIndex,
            myExample, aCompositeOrder, myExcludeProperties);

        // run assertions
        assertNotNull("findByPage did not find the expected Persons", 
            myPersons);
        assertEquals("findByPage did not find the expected Persons", 1, 
            myPersons.size());
        assertEquals("findByPage did not produce the expected order", 
            "Pedro", myPersons.get(0).getName());
        
        // now test a precondition violation e.g. requesting page index #2
        myPageIndex = 2;        
        try
        {
            myPersons = myPersonDao.findByPage(myPageSize, myPageIndex,
                myExample, aCompositeOrder);
            
            fail("findByPage did not check for 'aPageIndex' precondition " +
                "violation");
        }
        catch (IllegalArgumentException anException)
        {
            assertEquals("getPage error message is wrong", 
                "'aPageIndex' is out of range", anException.getMessage());
        }
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final IGenericDaoFactory theGenericDaoFactory;
}

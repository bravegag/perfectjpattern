//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractTestBaseReadOnlyDao.java Copyright (c) 2012 Giovanni Azua Garcia
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

import junit.framework.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.slf4j.*;

/**
 * Abstract reusable Test suite for any {@link IBaseReadOnlyDao} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 26, 2007 9:29:34 PM $
 */
public abstract
class AbstractTestBaseReadOnlyDao
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testContains()
    {        
        // create generic read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createReadOnlyDao(Person.class);
     
        // contains
        List<Person> myPersons = myPersonDao.findAll();
        boolean myContains = myPersonDao.contains(myPersons.get(0));

        // run assertions
        assertTrue("contains did not work as expected", myContains);
    }

    //------------------------------------------------------------------------
    public void
    testCount()
    {        
        // create generic read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createReadOnlyDao(Person.class);
     
        // contains
        int myCount = myPersonDao.count();

        // run assertions
        assertEquals("count did not work as expected", 2, myCount);
    }

    //------------------------------------------------------------------------
    public void
    testFindById()
    {        
        // create generic read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createReadOnlyDao(Person.class);
     
        // find by Id
        Person myPedro = myPersonDao.findById(thePedro.getId());

        // run assertions
        assertNotNull("findById did not find the expected Person", myPedro);
        assertEquals("findById did not find the expected Person", "Pedro", 
            myPedro.getName());
    }
    
    //------------------------------------------------------------------------
    public void
    testFindByNamedQueryPositional()
    {        
        // create generic read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createReadOnlyDao(Person.class);
     
        // find by Id
        List<Person> myPersons = myPersonDao.findByNamedQuery(
            "Person.findByName.positional", "Pedro");

        // run assertions
        assertNotNull("findByNamedQuery positional did not find the expected " +
            "Person", myPersons);
        assertEquals("findByNamedQuery positional did not find the expected " +
            "Person", 1, myPersons.size());
        assertEquals("findByNamedQuery positional did not find the expected " +
            "Person", "Pedro", myPersons.get(0).getName());
    }

    //------------------------------------------------------------------------
    public void
    testFindByNamedQueryNamed()
    {        
        // create generic read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createReadOnlyDao(Person.class);
     
        // find by Id
        Map<String, Object> myNamedParameters = new HashMap<String, Object>();
        myNamedParameters.put("age", 45);
        
        List<Person> myPersons = myPersonDao.findByNamedQuery(
            "Person.findByAge.named", myNamedParameters);

        // run assertions
        assertNotNull("findByNamedQuery positional did not find the expected " +
            "Person", myPersons);
        assertEquals("findByNamedQuery positional did not find the expected " +
            "Person", 1, myPersons.size());
        assertEquals("findByNamedQuery positional did not find the expected " +
            "Person", "Pedro", myPersons.get(0).getName());
    }

    //------------------------------------------------------------------------
    public void
    testFindAll(IOrder ... anOrder)
    {
        // create generic read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createReadOnlyDao(Person.class);
     
        // find All
        List<Person> myPersons = myPersonDao.findAll(anOrder);

        // run assertions
        assertNotNull("findAll did not find the expected Persons", myPersons);
        assertEquals("findAll did not find the expected Persons", 2, myPersons.
            size());
    }
    
    //------------------------------------------------------------------------
    public void
    testFindByPage(IOrder aCompositeOrder, IOrder aNameOrder)
    {
        // create generic read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createReadOnlyDao(Person.class);
     
        // find by Page
        int myPageSize = 50;
        int myPageIndex = 1;
                
        List<Person> myPersons = myPersonDao.findByPage(myPageSize, myPageIndex,
            aCompositeOrder);

        // run assertions
        assertNotNull("findByPage did not find the expected Persons", 
            myPersons);
        assertEquals("findByPage did not find the expected Persons", 2, 
            myPersons.size());
        assertEquals("findByPage did not produce the expected order", 
            theManuela, myPersons.get(0));
        assertEquals("findByPage did not produce the expected order", 
            thePedro, myPersons.get(1));
        
        // now test a precondition violation e.g. requesting page index #2
        myPageIndex = 2;        
        try
        {
            myPersons = myPersonDao.findByPage(myPageSize, myPageIndex, 
                aNameOrder);
            
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
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs an {@link AbstractTestBaseReadOnlyDao} from an instance of 
     * {@link IBaseDaoFactory}
     * 
     * @param aBaseDaoFactory {@link IBaseDaoFactory} instance
     * @throws IllegalArgumentException "'aBaseDaoFactory' must not be null"
     */
    protected 
    AbstractTestBaseReadOnlyDao(IBaseDaoFactory aBaseDaoFactory)
    throws IllegalArgumentException
    {
        Validate.notNull(aBaseDaoFactory, "'aBaseDaoFactory' must not be null");
        
        theBaseDaoFactory = aBaseDaoFactory;
    }

    //------------------------------------------------------------------------
    @Override
    protected void 
    setUp() 
    throws Exception
    {
        super.setUp();
     
        // create the two example Persons
        IBaseDao<Long, Person> myPersonDao = theBaseDaoFactory.createDao(
            Person.class);
        
        // null all Id
        Long nullId = null;
        thePedro.setId(nullId);        
        theManuela.setId(nullId);

        // create all fixture records
        myPersonDao.create(thePedro);            
        myPersonDao.create(theManuela);
        
        myPersonDao.getSession().flush();
    }
        
    //------------------------------------------------------------------------
    @Override
    protected void 
    tearDown() 
    throws Exception
    {
        super.tearDown();
        
        // create the two example Persons
        IBaseDao<Long, Person> myPersonDao = theBaseDaoFactory.createDao(
            Person.class);

        myPersonDao.getTransaction().rollback();
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    // CHECKSTYLE:OFF
    private static final Person thePedro = new Person("Pedro", 45);
    private static final Person theManuela = new Person("Manuela", 23);
    // CHECKSTYLE:ON
    
    /**
     * {@link IBaseDaoFactory} instance
     */
    private final IBaseDaoFactory theBaseDaoFactory;
    
    /**
     * Provides logging services for this class.
     */
    @SuppressWarnings("unused")
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

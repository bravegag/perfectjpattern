//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestBaseValueListHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.business.valuelisthandler.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.integration.dao.*;
import org.slf4j.*;

/**
 * Test suite for the {@link BaseValueListHandler} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 18, 2009 11:42:05 PM $
 */
public 
class TestBaseValueListHandler
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testExecuteQueryAll()
    {
        // create base read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = JpaDaoFactory.
            getInstance().createReadOnlyDao(Person.class);
     
        // create Value List Handler
        IBaseValueListHandler<Person> myValueListHandler = 
            new BaseValueListHandler<Person>(myPersonDao);
        
        // execute query
        myValueListHandler.executeQueryAll(new Order("name"));

        // run assertions
        assertEquals("Query was not successfully executed", 7, 
            myValueListHandler.size());
    }
    
    //------------------------------------------------------------------------
    public void
    testExecuteNamedQueryPositional()
    {
        // create base read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = JpaDaoFactory.
            getInstance().createReadOnlyDao(Person.class);
     
        // create Value List Handler
        IBaseValueListHandler<Person> myValueListHandler = 
            new BaseValueListHandler<Person>(myPersonDao);
        
        // execute query
        myValueListHandler.executeNamedQuery("Person.findByName.positional", "Pedro");

        // run assertions
        assertEquals("Query was not successfully executed", 1, 
            myValueListHandler.size());
    }

    //------------------------------------------------------------------------
    public void
    testExecuteNamedQueryNamed()
    {
        // create base read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = JpaDaoFactory.
            getInstance().createReadOnlyDao(Person.class);
     
        // create Value List Handler
        IBaseValueListHandler<Person> myValueListHandler = 
            new BaseValueListHandler<Person>(myPersonDao);
        
        // execute query
        Map<String, Object> myNamedParameters = new HashMap<String, Object>();
        myNamedParameters.put("age", 45);
        myValueListHandler.executeNamedQuery("Person.findByAge.named", 
            myNamedParameters);

        // run assertions
        assertEquals("Query was not successfully executed", 1, 
            myValueListHandler.size());
    }

    //------------------------------------------------------------------------
    public void
    testGetPage()
    {
        // create base read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = JpaDaoFactory.
            getInstance().createReadOnlyDao(Person.class);
     
        // create Value List Handler
        IBaseValueListHandler<Person> myValueListHandler = 
            new BaseValueListHandler<Person>(myPersonDao);

        // execute query
        myValueListHandler.executeQueryAll(new Order("name"));

        // get Page
        int myPageSize = 3;
        int myPageIndex = 1;
        
        List<Person> myFirstPage = myValueListHandler.getPage(myPageSize, 
            myPageIndex);
        assertEquals("getPage did not work as expected", 3, myFirstPage.size());
        assertEquals("getPage did not work as expected", theAnna, 
            myFirstPage.get(0));
        assertEquals("getPage did not work as expected", theErnesto, 
            myFirstPage.get(1));
        assertEquals("getPage did not work as expected", theFrancesca, 
            myFirstPage.get(2));
        
        myPageSize = 3;
        myPageIndex = 2;

        List<Person> mySecondPage = myValueListHandler.getPage(myPageSize, 
            myPageIndex);
        assertEquals("getPage did not work as expected", 3, mySecondPage.
            size());
        assertEquals("getPage did not work as expected", theLaura, 
            mySecondPage.get(0));
        assertEquals("getPage did not work as expected", theManuela, 
            mySecondPage.get(1));
        assertEquals("getPage did not work as expected", thePedro, 
            mySecondPage.get(2));

        myPageSize = 3;
        myPageIndex = 3;

        List<Person> myThirdPage = myValueListHandler.getPage(myPageSize, 
            myPageIndex);
        assertEquals("getPage did not work as expected", 1, myThirdPage.
            size());
        assertEquals("getPage did not work as expected", theRosa, 
            myThirdPage.get(0));
        
        // now test a precondition violation e.g. requesting page index #4
        myPageIndex = 4;        
        try
        {
            myValueListHandler.getPage(myPageSize, myPageIndex);
            
            fail("getPage did not check for 'aPageIndex' precondition " +
                "violation");
        }
        catch (IllegalArgumentException anException)
        {
            assertEquals("getPage error message is wrong", 
                "'aPageIndex' is out of range", anException.getMessage());
        }        
    }    

    //------------------------------------------------------------------------
    public void
    testIterator()
    {
        // create base read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = JpaDaoFactory.
            getInstance().createReadOnlyDao(Person.class);
     
        // create Value List Handler
        BaseValueListHandler<Person> myValueListHandler = 
            new BaseValueListHandler<Person>();
        myValueListHandler.setReadOnlyDao(myPersonDao);
        
        // execute query
        myValueListHandler.executeQueryAll(new Order("name"));

        // lets iterate one by one first
        assertTrue("Iterator one-by-one does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator one-by-one does not work", theAnna, 
            myValueListHandler.next());        
        assertTrue("Iterator one-by-one does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator one-by-one does not work", theErnesto, 
            myValueListHandler.next());        
        assertTrue("Iterator one-by-one does not work", myValueListHandler.
        hasNext());
        assertEquals("Iterator one-by-one does not work", theFrancesca, 
            myValueListHandler.next());
        assertTrue("Iterator one-by-one does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator one-by-one does not work", theLaura, 
            myValueListHandler.next());
        assertTrue("Iterator one-by-one does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator one-by-one does not work", theManuela, 
            myValueListHandler.next());
        assertTrue("Iterator one-by-one does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator one-by-one does not work", thePedro, 
            myValueListHandler.next());
        assertTrue("Iterator one-by-one does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator one-by-one does not work", theRosa, 
            myValueListHandler.next());
        
        assertFalse("Iterator one-by-one does not work", myValueListHandler.
            hasNext());

        // make sure it fails now
        try
        {
            myValueListHandler.next();
            
            fail("Iterator one-by-one does not work");
        }
        catch (NoSuchElementException anException)
        {
            // ok
        }
        
        // reset the Iterator
        myValueListHandler.reset();
        
        // now lets iterate with steps of 3 with happy ending
        assertTrue("Iterator three-by-three does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator three-by-three does not work", 3, 
            myValueListHandler.next(3).size());        
        assertTrue("Iterator three-by-three does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator three-by-three does not work", 3, 
            myValueListHandler.next(3).size());                
        assertTrue("Iterator three-by-three does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator three-by-three does not work", 1, 
            myValueListHandler.next(1).size());                
        assertFalse("Iterator three-by-three does not work", myValueListHandler.
            hasNext());

        // reset the Iterator
        myValueListHandler.reset();

        // now lets iterate with steps of 4 with unhappy ending
        assertTrue("Iterator four-by-four does not work", myValueListHandler.
            hasNext());
        assertEquals("Iterator four-by-four does not work", 4, 
            myValueListHandler.next(4).size());        
        assertTrue("Iterator four-by-four does not work", myValueListHandler.
            hasNext());

        // make sure it fails now
        try
        {
            myValueListHandler.next(4);
            
            fail("Iterator four-by-four does not work");
        }
        catch (IllegalArgumentException anException)
        {
            assertEquals("Iterator four-by-four does not work", 
                "'aNumberOfElements' plus Current Position must be " +
                    "equal or lower than size", anException.getMessage());
        }
    }

    //------------------------------------------------------------------------
    public void
    testSublist()
    {
        // create base read-only Dao instance
        IBaseReadOnlyDao<Long, Person> myPersonDao = JpaDaoFactory.
            getInstance().createReadOnlyDao(Person.class);
     
        // create Value List Handler
        BaseValueListHandler<Person> myValueListHandler = 
            new BaseValueListHandler<Person>(myPersonDao);
        
        // execute query
        myValueListHandler.executeQueryAll(new Order("name"));
        
        // get sublist
        List<Person> mySublist = myValueListHandler.subList(0, 1);
        assertEquals("sublist does not work", 1, mySublist.size());

        mySublist = myValueListHandler.subList(3, 6);
        assertEquals("sublist does not work", 3, mySublist.size());
        assertEquals("sublist does not work", theLaura, mySublist.get(0));
        assertEquals("sublist does not work", theManuela, mySublist.get(1));
        assertEquals("sublist does not work", thePedro, mySublist.get(2));
        
        try
        {
            mySublist = myValueListHandler.subList(6, 8);
            
            fail("sublist does not work");
        }
        catch (IllegalArgumentException anException)
        {
            assertEquals("sublist does not work", "Illegal end point index " +
                "value ('aFromIndex' < 0 || 'aToIndex' > size || " +
                    "'aFromIndex' > 'aToIndex')", anException.getMessage());
        }
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    @Override
    protected void 
    setUp() 
    throws Exception
    {
        super.setUp();
     
        // create the two example Persons
        IBaseDao<Long, Person> myPersonDao = JpaDaoFactory.
            getInstance().createDao(Person.class);
        
        // null all Id
        Long nullId = null;
        thePedro.setId(nullId);        
        theManuela.setId(nullId);
        theErnesto.setId(nullId);
        theFrancesca.setId(nullId);
        theLaura.setId(nullId);
        theRosa.setId(nullId);
        theAnna.setId(nullId);

        // create all fixture records
        
        myPersonDao.create(thePedro);            
        myPersonDao.create(theManuela);
        myPersonDao.create(theErnesto);
        myPersonDao.create(theFrancesca);
        myPersonDao.create(theLaura);
        myPersonDao.create(theRosa);
        myPersonDao.create(theAnna);
        
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
        IBaseDao<Long, Person> myPersonDao = JpaDaoFactory.getInstance().
            createDao(Person.class);

        myPersonDao.getTransaction().rollback();
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    // CHECKSTYLE:OFF
    private static final Person thePedro = new Person("Pedro", 45);
    private static final Person theManuela = new Person("Manuela", 23);
    private static final Person theErnesto = new Person("Ernesto", 33);
    private static final Person theFrancesca = new Person("Francesca", 24);
    private static final Person theLaura = new Person("Laura", 25);
    private static final Person theRosa = new Person("Rosa", 21);
    private static final Person theAnna = new Person("Ann", 27);
    // CHECKSTYLE:ON
    
    /**
     * Provides logging services for this class.
     */
    @SuppressWarnings("unused")
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

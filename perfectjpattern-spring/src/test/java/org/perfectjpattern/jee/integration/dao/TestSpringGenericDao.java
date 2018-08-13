//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestSpringGenericDao.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.slf4j.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.transaction.*;
import org.springframework.transaction.annotation.*;

import static org.junit.Assert.*;

/**
 * Test Suite for the {@link SpringGenericDao} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 21, 2012 9:05:00 PM $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/resources/genericDao-applicationContext.xml",
    "file:src/test/resources/test-applicationContext.xml"
})
@Transactional
public 
class TestSpringGenericDao
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    @Test
    public void
    testFindByName()
    {
        // create Generic DAO instance
        IPersonDao myPersonDao = LocalDaoFactory.getInstance().
            createPersonDao();
        
        // create fixture Person instances
        Person myErnesto = new Person("Ernesto", 35);        
        myPersonDao.create(myErnesto);
        Person myGiovanni = new Person("Giovanni", 33);        
        myPersonDao.create(myGiovanni);
        Person myAlberto = new Person("Alberto", 33);        
        myPersonDao.create(myAlberto);
        
        List<Person> myMatches = myPersonDao.findByName("Ernesto");
        assertNotNull("findByName did not work as expected", myMatches);
        assertEquals("findByName did not work as expected", 1, myMatches.
            size());
        assertEquals("findByName did not work as expected", myErnesto, 
            myMatches.get(0));
    }
    
    //------------------------------------------------------------------------
    @Test
    public void
    testFindByAge()
    {
        // create Generic DAO instance
        IPersonDao myPersonDao = LocalDaoFactory.getInstance().
            createPersonDao();
        
        // create fixture Person instances
        Person myErnesto = new Person("Ernesto", 35);        
        myPersonDao.create(myErnesto);
        Person myGiovanni = new Person("Giovanni", 33);        
        myPersonDao.create(myGiovanni);
        Person myAlberto = new Person("Alberto", 33);        
        myPersonDao.create(myAlberto);
        
        List<Person> myMatches = myPersonDao.findByAge(33);
        assertNotNull("findByAge did not work as expected", myMatches);
        assertEquals("findByAge did not work as expected", 2, myMatches.
            size());
        assertEquals("findByAge did not work as expected", 33, 
            myMatches.get(0).getAge());        
        assertEquals("findByAge did not work as expected", 33, 
            myMatches.get(1).getAge());        
    }
    
    //------------------------------------------------------------------------
    @Test
    public void
    testFindByExample()
    {
        // create Generic DAO instance
        IPersonDao myPersonDao = LocalDaoFactory.getInstance().
            createPersonDao();
        
        // create fixture Person instances
        Person myErnesto = new Person("Ernesto", 35);        
        myPersonDao.create(myErnesto);
        Person myGiovanni = new Person("Giovanni", 33);        
        myPersonDao.create(myGiovanni);
        Person myAlberto = new Person("Alberto", 33);        
        myPersonDao.create(myAlberto);
        
        Person myExample = new Person();
        myExample.setAge(33);        
        List<Person> myMatches = myPersonDao.findByExample(myExample);
        assertNotNull("findByExample did not work as expected", myMatches);
        assertEquals("findByExample did not work as expected", 2, myMatches.
            size());
        assertEquals("findByExample did not work as expected", 33, 
            myMatches.get(0).getAge());        
        assertEquals("findByExample did not work as expected", 33, 
            myMatches.get(1).getAge());        
    }

    //------------------------------------------------------------------------
    @Test
    public void
    testFindById()
    {
        // create Generic DAO instance
        IPersonDao myPersonDao = LocalDaoFactory.getInstance().
            createPersonDao();
        
        // create fixture Person instances
        Person myErnesto = new Person("Ernesto", 35);        
        myPersonDao.create(myErnesto);
        
        Person myMatch = myPersonDao.findById(myErnesto.getId());
        assertNotNull("findById did not work as expected", myMatch);
        assertEquals("findById did not work as expected", myErnesto, 
            myMatch);
    }

    //------------------------------------------------------------------------
    @After
    @AfterTransaction
    public void 
    tearDown()
    throws Exception 
    {
        // empty the Person table
        IPersonDao myPersonDao = LocalDaoFactory.getInstance().
            createPersonDao();
        myPersonDao.deleteAll();
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class
     */
    @SuppressWarnings("unused")
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

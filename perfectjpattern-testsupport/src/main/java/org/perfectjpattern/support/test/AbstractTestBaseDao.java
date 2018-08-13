//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestHibernateGenericDao.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Abstract reusable Test suite for any {@link IBaseDao} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 26, 2007 9:31:46 PM $
 */
public abstract
class AbstractTestBaseDao
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testCreate()
    {
        // create generic read-only Dao instance
        IBaseDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createDao(Person.class);
        
        // create a new Person
        Person myGiovanni = new Person("Giovanni", 32);
        
        myPersonDao.create(myGiovanni);
        
        // make sure it exists in the persistent storage
        List<Person> myPersons = myPersonDao.findAll();
        
        assertNotNull("create seems not to work, record was not found.", 
            myPersons);
        assertEquals("create seems not to work, record was not found.", 
            1, myPersons.size());
        assertEquals("create seems not to work, record was not found.", 
            "Giovanni", myPersons.get(0).getName());  
    }
    
    //------------------------------------------------------------------------
    public void
    testUpdate()
    {
        // create generic read-only Dao instance
        IBaseDao<Long, Person> myPersonDao = theBaseDaoFactory.
            createDao(Person.class);
        
        // create a new Person
        Person myRosa = new Person("Rosa", 27);        
        myPersonDao.create(myRosa);
        
        // retrieve the record just created 
        List<Person> myPersons = myPersonDao.findAll();
        
        Person myUpdate = myPersons.get(0);
        myUpdate.setAge(28);
        myPersonDao.update(myUpdate);
        
        // retrieve the record just edited
        myPersons = myPersonDao.findAll();

        assertNotNull("create seems not to work, record was not found.", 
            myPersons);
        assertEquals("create seems not to work, record was not found.", 
            "Rosa", myPersons.get(0).getName());        
        assertEquals("create seems not to work, record was not found.", 
            28, myPersons.get(0).getAge());        
    }

    //------------------------------------------------------------------------
    public void
    testDelete()
    {
        // create generic read-only Dao instance
        IBaseDao<Long, Person> myPersonDao = theBaseDaoFactory.createDao(
            Person.class);
        
        // create a new Person
        Person myErnesto = new Person("Ernesto", 35);        
        myPersonDao.create(myErnesto);
        
        // flush so the Id will be assigned
        myPersonDao.getSession().flush();
        
        Long myId = myErnesto.getId();
        
        Person myDelete = myPersonDao.findById(myId);
        
        // delete Ernesto
        myPersonDao.delete(myDelete);
        
        boolean myContains = myPersonDao.contains(myDelete);
        
        // run assertions on it
        assertFalse("There must be no Elements left matching the deleted.", 
            myContains);
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
    AbstractTestBaseDao(IBaseDaoFactory aBaseDaoFactory)
    throws IllegalArgumentException
    {
        Validate.notNull(aBaseDaoFactory, "'aBaseDaoFactory' must not be null");
        
        theBaseDaoFactory = aBaseDaoFactory;
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

        ITransaction myTransaction = myPersonDao.getTransaction();
        if (myTransaction.isActive())
        {
            myTransaction.rollback();
        }
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging services for this class.
     */
    @SuppressWarnings("unused")
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * {@link IBaseDaoFactory} instance
     */
    private final IBaseDaoFactory theBaseDaoFactory;    
}

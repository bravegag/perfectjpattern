//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IPersonDao.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.integration.dao;

import java.util.*;

import org.perfectjpattern.example.model.*;

/**
 * Abstract definition of the Person DAO
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Nov 6, 2008 2:11:46 PM $
 */
public 
interface IPersonDao
extends IGenericDao<Long, Person>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the List of matching {@link Person} instances. Finds person 
     * instances by name
     * 
     * @param aName the name of the person to search for
     * @return List of matching {@link Person} instances
     */
    @QueryParameters(names = "Name")
    public List<Person> 
    findByName(String aName);
    
    //------------------------------------------------------------------------
    /**
     * Returns the List of matching {@link Person} instances. Finds person 
     * instances by age
     * 
     * @param anAge the age of the person to search for
     * @return List of matching {@link Person} instances
     */
    @QueryParameters(names = "Age")
    public List<Person> 
    findByAge(int anAge);
}

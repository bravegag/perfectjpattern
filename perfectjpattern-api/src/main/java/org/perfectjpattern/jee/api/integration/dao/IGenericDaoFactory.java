//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IGenericDaoFactory.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.io.*;

/**
 * Abstract Factory for creating instances of {@link IGenericReadOnlyDao} and 
 * {@link IGenericDao} types
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 5, 2008 5:26:23 PM $
 */
public 
interface IGenericDaoFactory
extends IBaseDaoFactory
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the Generic Read-Only DAO implementation corresponding to the 
     * given model class type. Provides a generic and reusable means to 
     * create cached DAO instances. 
     * 
     * @param <Id> Id type    
     * @param <Element> Element type
     * @param aPersistentClass Data model class type
     * @return Generic DAO implementation corresponding to the given  
     *         model class type
     * @throws IllegalArgumentException 'aPersistentClass' must not be null
     * @throws IllegalArgumentException 'aPersistentClass' must be a class type
     */
    public <Id extends Serializable, Element> IGenericReadOnlyDao<Id, Element>
    createReadOnlyDao(Class<Element> aPersistentClass)
    throws IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Returns the Generic DAO implementation corresponding to the given 
     * model class type. Provides a generic and reusable means to create 
     * cached DAO instances. 
     * 
     * @param <Id> Id type    
     * @param <Element> Element type
     * @param aPersistentClass Data model class type
     * @return Generic DAO implementation corresponding to the given  
     *         model class type
     * @throws IllegalArgumentException 'aPersistentClass' must not be null
     * @throws IllegalArgumentException 'aPersistentClass' must be a class type
     */
    public <Id extends Serializable, Element> IGenericDao<Id, Element>
    createDao(Class<Element> aPersistentClass)
    throws IllegalArgumentException;  
}

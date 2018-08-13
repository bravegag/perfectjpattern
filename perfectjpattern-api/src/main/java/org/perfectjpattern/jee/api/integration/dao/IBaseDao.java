//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IBaseDao.java Copyright (c) 2012 Giovanni Azua Garcia
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

/**
 * <b>Data Access Object (DAO) Pattern</b>: Abstracts from any direct type of 
 * database or persistence mechanism. Provides specific operations without 
 * exposing details of the database.
 * <br/><br/>
 * <b>Responsibility</b> : Base Data Access Object definition including: 
 * <br/>
 * <br/>
 * <ul>
 * <li>Creating a new record in the underlying persistent storage</li> 
 * <li>Reading existing records from the underlying persistence 
 * storage through the finder methods as defined in {@link IBaseReadOnlyDao}
 * </li> 
 * <li>Update an existing record in the underlying persistent storage</li> 
 * <li>Delete an existing record in the underlying persistent storage</li> 
 * <li>Delete all records from the underlying persistent storage</li> 
 * </ul>
 * 
 * @param <Id> Identification type
 * @param <Element> Element type
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Data_Access_Object">Data Access 
 * Object wiki definition</a> 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 19, 2009 11:08:28 AM $
 */
public 
interface IBaseDao<Id, Element>
extends IBaseReadOnlyDao<Id, Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the ID of the newly created Element. Creates an Element in the 
     * persistent storage and returns its ID.
     * 
     * @param anElement Element to create
     * @return ID of the newly created Element
     * @throws DaoException 
     * @throws IllegalArgumentException 'anElement' must not be null.  
     */
    public Id 
    create(Element anElement)
    throws DaoException, IllegalArgumentException;

    //------------------------------------------------------------------------
    /**
     * Returns true if the Element was updated, false otherwise i.e. the 
     * Element was not found. Updates the given Element in the persistent 
     * storage.
     * 
     * @param anElement Element to update
     * @return true if the Element was updated, false otherwise.
     * @throws DaoException
     * @throws IllegalArgumentException 'anElement' must not be null.  
     */
    public boolean
    update(Element anElement)
    throws DaoException, IllegalArgumentException;

    //------------------------------------------------------------------------
    /**
     * Returns true if the Element was deleted, false otherwise i.e. the 
     * Element was not found. Deletes the given Element from the persistent 
     * storage.
     * 
     * @param anElement Element to delete
     * @return true if the Element was deleted, false otherwise.
     * @throws DaoException
     * @throws IllegalArgumentException 'anElement' must not be null.  
     */
    public boolean
    delete(Element anElement)
    throws DaoException, IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Deletes all Element from the persistent storage.
     * 
     * @throws DaoException
     */
    public void
    deleteAll()
    throws DaoException;
}

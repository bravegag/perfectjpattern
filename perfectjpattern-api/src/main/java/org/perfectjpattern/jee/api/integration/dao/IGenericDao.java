//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IGenericDao.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * <b>Responsibility</b> : Complete Generic Data Access Object definition 
 * including: 
 * <br/>
 * <br/>
 * <ul>
 * <li>Creating a new record in the underlying persistent storage</li> 
 * <li>Reading existing records from the underlying persistence 
 * storage through the finder methods as defined in {@link IGenericReadOnlyDao}
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
 * @see <a href="http://www.ibm.com/developerworks/java/library/j-genericdao.
 * html">Don't repeat the DAO!</a> 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 26, 2007 8:24:15 PM $
 */
public 
interface IGenericDao<Id, Element>
extends IGenericReadOnlyDao<Id, Element>, IBaseDao<Id, Element>
{
    // empty
}

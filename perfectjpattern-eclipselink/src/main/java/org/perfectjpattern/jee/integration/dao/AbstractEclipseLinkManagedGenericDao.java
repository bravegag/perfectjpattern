//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractEclipseLinkManagedGenericDao.java Copyright (c) 2012 
// Giovanni Azua Garcia bravegag@hotmail.com
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

import java.io.*;

import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Abstract base partial implementation for all managed EJB exposing 
 * PerfectJPattern {@link IGenericDao} implementation.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 11, 2009 10:51:42 AM $
 */
public abstract
class AbstractEclipseLinkManagedGenericDao<Id extends Serializable, Element>
extends EclipseLinkGenericDao<Id, Element>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs a {@link AbstractEclipseLinkManagedGenericDao} instance from 
     * the persistent class type.
     * 
     * @param aPersistentClass The persistent Java Bean class
     * @throws IllegalArgumentException 'aPersistentClass' must not be null
     * @throws IllegalArgumentException 'aPersistentClass' must be a class type
     */
    public 
    AbstractEclipseLinkManagedGenericDao(Class<Element> aPersistentClass)
    {
        super(aPersistentClass, new ManagedSessionStrategy(), 
            new ManagedTransactionStrategy());
        
        ManagedTransactionStrategy myTransactionStrategy = 
            (ManagedTransactionStrategy) getTransactionStrategy();
        myTransactionStrategy.setSessionStrategy(getSessionStrategy());
    }    
}

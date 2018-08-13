//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IFinderNamingStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.lang.reflect.*;

import org.perfectjpattern.core.api.behavioral.strategy.*;

/**
 * Abstract definition of a Strategy for discovering query names based 
 * on a data model type and a finder interface method
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Nov 5, 2008 11:55:29 AM $
 */
public
interface IFinderNamingStrategy
extends IStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the query name based on the given finder method. Defines 
     * a discovery mechanism to connect queries declared in mapping 
     * files to a data model type class
     * 
     * @param aClass Data model class type
     * @param aMethod Method to derive the query name from
     * 
     * @return Query name based on the given method
     * @throws IllegalArgumentException 'aClass' must no be null
     * @throws IllegalArgumentException 'aMethod' must no be null
     */
    public String 
    getQueryName(Class<?> aClass, Method aMethod)
    throws IllegalArgumentException;
}

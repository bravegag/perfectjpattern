//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// SimpleFinderNamingStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.lang.reflect.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.jee.api.integration.dao.*;


/**
 * Concrete basic implementation of {@link IFinderNamingStrategy}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Nov 6, 2008 1:32:07 PM $
 */
class SimpleFinderNamingStrategy
implements IFinderNamingStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public String 
    getQueryName(Class<?> aClass, Method aMethod)
    throws IllegalArgumentException
    {
        Validate.notNull(aClass, "'aClass' must not be null");
        Validate.notNull(aMethod, "'aMethod' must not be null");
        
        String myMethodName = aMethod.getName();
        String myMethodPart = myMethodName;
        
        assert myMethodName.startsWith("findBy") : "Finder methods must " +
            "comply with 'findBy' naming convention";
        
        return aClass.getSimpleName() + "." + myMethodPart;
    }
}

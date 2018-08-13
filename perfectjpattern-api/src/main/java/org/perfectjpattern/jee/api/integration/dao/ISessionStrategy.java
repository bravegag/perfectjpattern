//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ISessionStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.behavioral.strategy.*;

/**
 * Abstract {@link IStrategy} reusable by {@link IGenericReadOnlyDao} 
 * that defines:
 * <ul>
 * <li>Defines retrieval of instances of JPA implementation-specific 
 * <code>Session</code> instances</li>
 * <li>defines shutdown</li>
 * </ul>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 8, 2009 2:20:43 PM $
 */
public 
interface ISessionStrategy
extends IStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the adapted JPA implementation-specific <code>ISession</code>
     * 
     * @return adapted JPA implementation-specific <code>ISession</code>
     */
    public ISession
    getSession();

    //------------------------------------------------------------------------
    /**
     * Shutdown the underlying provider of <code>Session</code> instances
     */
    public void
    shutdown();
}

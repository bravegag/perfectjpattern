//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// NullEventData.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.behavioral.observer.data;

import org.perfectjpattern.core.api.creational.singleton.*;

/**
 * Null Object Pattern implementation of <code>IEventData</code>. 
 * Prevents different <code>ISubject</code> implementations from pushing 
 * <code>null</code> into the <code>IObserver</code>'s 
 * <code>update</code> method.
 * <br/><br/>
 * <code>NullEventData</code> is a Singleton therefore it can not be directly 
 * instantiated, neither it may be extended.
 * <br/><br/>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 19, 2007 10:17:38 PM $
 */
public final
class NullEventData
implements IEventData, ISingleton
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of <code>NullEventData</code>.
     * 
     * @return Singleton instance of <code>NullEventData</code>.
     */
    public static NullEventData
    getInstance()
    {
        return INSTANCE;
    }
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    private 
    NullEventData()
    {        
        // do nothing
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Singleton instance of <code>NullEventData</code>
     */
    private static final NullEventData INSTANCE = new NullEventData(); 
}

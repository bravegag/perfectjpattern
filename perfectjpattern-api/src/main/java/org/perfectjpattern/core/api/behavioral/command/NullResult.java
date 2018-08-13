//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// NullResult.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.behavioral.command;

import org.perfectjpattern.core.api.creational.singleton.*;

/**
 * Null Object Pattern implementation of Command <code>Result</code>. 
 * Prevents different <code>Receiver</code> implementations from pushing 
 * <code>null</code> back into the <code>Command</code>'s result value.
 * <br/><br/>
 * <code>NullResult</code> is a Singleton therefore it can not be directly 
 * instantiated, neither it may be extended.
 * <br/><br/>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 19, 2007 10:17:38 PM $
 */
public final
class NullResult
implements ISingleton
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of <code>NullResult</code>.
     * 
     * @return Singleton instance of <code>NullResult</code>.
     */
    public static NullResult
    getInstance()
    {
        return INSTANCE;
    }
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    private 
    NullResult()
    {        
        // do nothing
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Singleton instance of <code>NullResult</code>
     */
    private static final NullResult INSTANCE = new NullResult(); 
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// NullRequest.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.behavioral.chainofresponsibility;

import org.perfectjpattern.core.api.creational.singleton.*;

/**
 * Null Object Pattern implementation of Chain of Responsibility 
 * Request Parameter. 
 * <br/><br/>
 * <code>NullRequest</code> is a Singleton therefore it may not be directly 
 * instantiated, neither it may be extended.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 6, 2007 5:35:36 PM $
 */
public final
class NullRequest
implements ISingleton
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of <code>NullRequest</code>.
     * 
     * @return Singleton instance of <code>NullRequest</code>.
     */
    public static NullRequest
    getInstance()
    {
        return INSTANCE;
    }    
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    private 
    NullRequest()
    {       
        // do nothing
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Singleton instance of <code>NullRequest</code>
     */
    private static final NullRequest INSTANCE = new NullRequest();    
}

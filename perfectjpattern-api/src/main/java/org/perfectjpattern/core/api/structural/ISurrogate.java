//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ISurrogate.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.structural;

import java.lang.reflect.*;

/**
 * Base abstraction for any Design Pattern type whose structure is based on 
 * surrogation or wrapping. Surrogate Design Patterns are those that for some 
 * purpose wrap and provide to clients the same interface view of the actual 
 * wrapped <code>Component</code> interface. <br/><br/>
 * 
 * Structural surrogate Patterns are:
 * 
 * <ul>
 * <li>Adapter Pattern</li>
 * <li>Composite Pattern</li>
 * <li>Decorator Pattern</li>
 * <li>Proxy Pattern</li>
 * </ul>
 * 
 * @param <C> <code>Component</code> or wrapper element type
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 5, 2008 3:44:45 PM $
 */
public 
interface ISurrogate<C>
extends InvocationHandler
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the <code>Component</code> interface view <code>&lt;C&gt;</code> 
     * of this <code>ISurrogate</code><br/><br/>
     * Client code calls this method to receive an instance of type <code
     * >&lt;C&gt;</code> making accessible the Component business methods 
     * while the underlying implementation is wrapped by this surrogate. 
     * 
     * @return <code>Component</code> interface view <code>&lt;C&gt;</code> 
     *         of this <code>ISurrogate</code> type
     */
    public C
    getComponent();    
}

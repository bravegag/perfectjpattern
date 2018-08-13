//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IDecorator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.structural.proxy;

import org.perfectjpattern.core.api.structural.*;

/**
 * <b>Proxy Design Pattern</b>: Provide a surrogate or placeholder for 
 * another object to control access to it. (Gamma et al, Design Patterns)
 * <br/><br/>
 * 
 * <b>Responsibility</b> Abstract definition of the "Proxy": <br/>
 * <br/>
 * <ul>
 * <li>maintains a reference that lets the proxy access the real subject. 
 * Proxy may refer to a Subject if the RealSubject and Subject interfaces are 
 * the same.</li>
 * <li>provides an interface identical to Subject's so that a proxy can be 
 * substituted for for the real subject.</li>
 * <li>controls access to the real subject and may be responsible for 
 * creating and deleting it.</li>
 * <li>other responsibilites depend on the kind of proxy:
 * <ul>
 * <li>remote proxies are responsible for encoding a request and 
 * its arguments and for sending the encoded request to the real 
 * subject in a different address space.</li>
 * <li>virtual proxies may cache additional information about the 
 * real subject so that they can postpone accessing it. For example, 
 * the ImageProxy from the Motivation caches the real images's 
 * extent.</li>
 * <li>protection proxies check that the caller has the access 
 * permissions required to perform a request.</li>
 * </ul>
 * </ul>
 *
 * @param <S> <code>Subject</code> element type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 25, 2007 3:06:58 PM $
 */
public 
interface IProxy<S>
extends ISurrogate<S>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the <code>Proxy</code> wrapper instance
     * 
     * @return <code>Proxy</code> wrapper instance
     */
    public S
    getSubject();

    //------------------------------------------------------------------------
    /**
     * Returns the actual wrapped <code>Subject</code> instance
     * 
     * @return actual wrapped <code>Subject</code> instance
     */
    public S
    getRealSubject();
}

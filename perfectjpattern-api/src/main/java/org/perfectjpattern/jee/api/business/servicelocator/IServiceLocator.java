//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IServiceLocator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.business.servicelocator;

/**
 * <b>Service Locator Pattern</b>: Centralizes distributed service object 
 * lookups, provides a centralized point of control, and may act as a cache 
 * that eliminates redundant lookups. It also encapsulates any vendor-
 * specific features of the lookup process. 
 * <br/><br/>
 * 
 * <b>Responsibility</b> Abstract definition of the "Service Locator": <br/>
 * <br/>
 * <ul>
 * <li>abstracts API lookup for naming services providing simpler 
 * interface to clients</li>
 * </ul>
 *
 * @param <S> <code>Service</code> type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 6, 2009 9:35:08 PM $
 */
public 
interface IServiceLocator
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the matching Service found. For some reason the generic cast of 
     * this variant is not type-safe even when trying to catch a possible 
     * {@link ClassCastException} while casting to the generic service type i.e.
     * <pre><code>
     * (S) lookup(...) // produces ClassCastException when lookup(...)  
     *                 // returns something different than S
     * </code></pre> 
     * Prefer using the other alternative {@link #locate(Class, String)} 
     * <br/>
     * <br/> 
     * @param aName Name of Service to lookup
     * @return matching Service found
     * @throws IllegalArgumentException 'aName' must not be null
     * @throws ServiceLocatorException
     */
    public <S> S
    locate(String aName)
    throws ClassCastException, IllegalArgumentException, 
        ServiceLocatorException;
    
    //------------------------------------------------------------------------
    /**
     * Returns the matching Service found. This method is preferred over the 
     * other {@link #locate(String)} because it will ensure that the Service 
     * Type matches the expected one. For some reason the generic cast of the 
     * other implementation is not type-safe even when trying to catch a 
     * possible {@link ClassCastException} while casting to the generic
     * service type i.e.
     * <pre><code>
     * (S) lookup(...) // does not produce ClassCastException even when 
     *                 // lookup(...) returns something different than S
     * </code></pre>
     * 
     * @param aName Name of Service to lookup
     * @param aServiceClass Class type of the Service to lookup
     * @return matching Service found
     * @throws IllegalArgumentException 'aServiceClass' must not be null
     * @throws IllegalArgumentException 'aName' must not be null
     * @throws ServiceLocatorException
     */
    public <S> S
    locate(Class<S> aServiceClass, String aName)
    throws IllegalArgumentException, ServiceLocatorException;

    //------------------------------------------------------------------------
    /**
     * Shutdown the {@link IServiceLocator} and release resources
     */
    public void
    shutdown();
}

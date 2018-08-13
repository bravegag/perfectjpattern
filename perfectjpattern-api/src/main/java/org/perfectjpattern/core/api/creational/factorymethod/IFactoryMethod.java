//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IFactoryMethod.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.creational.factorymethod;

/**
 * <b>Factory Method Design Pattern</b>: Define an interface for creating an
 * object, but let subclasses decide which class to instantiate. 
 * Factory Method lets a class defer instantiation to subclasses.
 * (Gamma et al, Design Patterns)
 * <br/><br/>
 * 
 * <b>Responsibility</b> Abstract definition of the "Creator": <br/>
 * <br/>
 * <ul>
 * <li>Declares the factory method, which returns an object of type 
 * Product.</li> 
 * <li>Creator may also define a default implementation of the factory 
 * method that returns a default ConcreteProduct object.
 * may call the factory method to create a Product object.</li>
 * </ul>
 * 
 * <br/>
 * Example usage:
 * <pre><code>
 * import org.perfectjpattern.core.api.creational.factorymethod.*;
 *       
 *  //
 *  // Example of Factory Method subtype that creates Product with a
 *  // name parameter. 
 *  //
 *  public IProductFactory
 *  extends IFactoryMethod&lt;Product&gt;
 *  {
 *      //--------------------------------------------------------------------
 *      // public
 *      //--------------------------------------------------------------------
 *      public void
 *      setName(String aName);          
 *  } 
 * 
 * </code></pre>
 * @param <T> Type of elements created.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 19, 2007 11:01:51 PM $
 */
public 
interface IFactoryMethod<T>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns newly created instance
     * 
     * @return Newly created instance
     */
    T create();    
}

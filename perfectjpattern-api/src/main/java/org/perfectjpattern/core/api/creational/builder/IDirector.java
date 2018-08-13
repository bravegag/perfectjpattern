//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IDirector.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.creational.builder;

/**
 * <b>Builder Design Pattern</b>: Separate the construction of a complex object 
 * from its representation so that the same construction process can create 
 * different representations. (Gamma et al, Design Patterns)
 * <br/><br/>
 * 
 * <b>Responsibility</b> Director: <br/>
 * <br/>
 * <ul>
 * <li>constructs an object using the Builder interface</li>
 * </ul>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 4, 2009 2:40:46 PM $
 */
public 
interface IDirector
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs the final product using the appropriate {@link IBuilder} 
     */
    public void
    construct();
}
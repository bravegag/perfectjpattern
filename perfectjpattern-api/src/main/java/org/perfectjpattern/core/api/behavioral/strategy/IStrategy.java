//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.behavioral.strategy;

/**
 * <b>Strategy Design Pattern</b>: <i>"Define a family of algorithms, 
 * encapsulate each one, and make them interchangeable. Strategy lets 
 * the algorithm vary independently from clients that use it." </i> 
 * (Gamma et al, Design Patterns)
 * <br/>
 * <br/>
 * 
 * <b>Responsibility</b>: Abstract definition of the "Strategy"
 * 
 * <ul>
 * <li>declares an interface common to all supported algorithms. Context uses 
 * this interface to call the algorithm defined by a ConcreteStrategy</li>
 * </ul>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 6, 2007 9:59:45 PM $
 */
public 
interface IStrategy
{
    // marker interface
}

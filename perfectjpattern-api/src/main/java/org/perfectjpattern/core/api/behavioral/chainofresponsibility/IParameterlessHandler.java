//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IParameterlessHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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

/**
 * <b>Chain of Responsibility Design Pattern</b>: <i>"Avoid coupling the sender 
 * of a request to its receiver by giving more than one object a chance to 
 * handle the request. Chain the receiving objects and pass the request 
 * along the chain until an object handles it."</i> (Gamma et al, Design 
 * Patterns)
 * <br/>
 * <br/>
 * 
 * <b>Responsibility</b>: Abstract definition of the "Handler".
 * 
 * <ul>
 * <li>Defines an interface for handling requests.</li>
 * <li>Implements the Successor link.</li>
 * </ul>
 * 
 * <br/>
 * Example usage:
 * <pre><code>
 *    //
 *    // Create chain elements 
 *    //  
 *    IParameterlessHandler myFirst = new ConcreteHandler();
 *    IParameterlessHandler mySecond = new ConcreteHandler();
 *    IParameterlessHandler myThird = new ConcreteHandler();
 *    
 *    //
 *    // Associate Handler elements 
 *    //
 *    myFirst.setSuccessor(mySecond);
 *    mySecond.setSuccessor(myThird);
 *    
 *    //
 *    // Execute the first Handler that triggers the execution of the 
 *    // complete chain.
 *    //
 *    myFirst.start();
 * </code></pre>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 6, 2007 6:14:22 PM $
 */
public 
interface IParameterlessHandler
extends IHandler<NullRequest>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Triggers execution of the Chain if the target Handler is the first 
     * reference, otherwise implements the decision-making regarding forwarding
     * the request to its successor <code>IHandler</code> instance.
     */
    public void
    start();

    //------------------------------------------------------------------------
    /**
     * Handle the given request. Implements the actual handling logic and must 
     * not contain any decision-making regarding e.g. forwarding the request.
     */
    public void
    handle();
}

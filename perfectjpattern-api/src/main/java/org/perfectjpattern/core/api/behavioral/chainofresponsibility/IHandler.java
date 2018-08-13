//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * handle the request. Chain the receiving objects and pass the request along 
 * the chain until an object handles it."</i> (Gamma et al, Design Patterns)
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
 *    IHandler myFirst = new ConcreteHandler();
 *    IHandler mySecond = new ConcreteHandler();
 *    IHandler myThird = new ConcreteHandler();
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
 *    myFirst.start(NullRequest.getInstance());
 * </code></pre>
 * 
 * @param <R> Request parameter type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 23, 2007 1:27:25 PM $
 */
public 
interface IHandler<R>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns true if this <code>IHandler</code> can handle the given request, 
     * false otherwise.
     * 
     * @param aRequest Context-specific request to handle.
     * @return true if this <code>IHandler</code> can handle the given request, 
     *         false otherwise.
     * @throws IllegalArgumentException 'aRequest' must not be null.
     */
    public boolean
    canHandle(R aRequest)
    throws IllegalArgumentException;        
    
    //------------------------------------------------------------------------
    /**
     * Triggers execution of the Chain if the target Handler is the first 
     * reference, otherwise implements the decision-making regarding forwarding
     * the request to its successor <code>IHandler</code> instance.
     * 
     * @param aRequest Context-specific request to handle.
     * @throws IllegalArgumentException 'aRequest' must not be null.
     */
    public void
    start(R aRequest)
    throws IllegalArgumentException;    

    //------------------------------------------------------------------------
    /**
     * Handle the given request. Implements the actual handling logic and must 
     * not contain any decision-making regarding e.g. forwarding the request.
     * 
     * @param aRequest Context-specific request to handle.
     * @throws IllegalArgumentException 'aRequest' must not be null.
     * @throws IllegalArgumentException 'aRequest' can not be handled.
     */
    public void
    handle(R aRequest)
    throws IllegalArgumentException;    

    //------------------------------------------------------------------------
    /**
     * Returns the Successor handler.
     * 
     * @return Successor handler.
     */
    public IHandler<R>    
    getSuccessor();

    //------------------------------------------------------------------------
    /**
     * Sets the Successor element.
     * 
     * @param aSuccessor Successor handler
     * @throws IllegalArgumentException 'aSuccessor' must not be null.
     */
    public void 
    setSuccessor(IHandler<R> aSuccessor)
    throws IllegalArgumentException;
    
    //------------------------------------------------------------------------
    /**
     * Set the <code>IChainStrategy</code> to this <code>IHandler</code>. 
     * <code>IChainStrategy</code> allows to easily modify how the 
     * Chain should behave. Possible implementations are e.g.
     * <ul>
     * <li>Halt as soon as the first Handler processes the request</li>
     * <li>Let all handlers process the request</li>
     * <li>Let the first N handlers process the request</li>
     * </ul>
     * 
     * @param aStrategy Continuation strategy defines how the chain should 
     *        behave
     * @throws IllegalArgumentException 'aStrategy' must not be null.
     */
    public void
    setChainStrategy(IChainStrategy aStrategy)
    throws IllegalArgumentException;
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IReceiver.java Copyright (c) 2012 Giovanni Azua Garcia
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

/**
 * <b>Command Design Pattern</b>: <i>"Encapsulate a request as an object, 
 * thereby letting you parameterize clients with different 
 * requests, queue or log requests, and support undoable operations."</i> 
 * (Gamma et al, Design Patterns)
 * <br/>
 * <br/>
 * <b>Responsibility</b>: Abstract generic definition of the "Receiver".
 * <ul>
 * <li>Knows how to perform the operations associated with carrying 
 * out the request.</li>
 * </ul><br/>
 * 
 * <br/>
 * Example usage:
 * <pre><code>
 *    //
 *    // Create Command Pattern elements 
 *    //  
 *    IInvoker myInvoker = new ConcreteInvoker();
 *    ICommand myCommand = new ConcreteCommand();
 *    IReceiver myReceiver = new ConcreteReceiver();
 *    
 *    //
 *    // Associate Command Pattern elements 
 *    //
 *    myInvoker.setCommand(myCommand);
 *    myCommand.setReceiver(myReceiver);
 *    
 *    //
 *    // Optionally parameterize the Invoker
 *    //
 *    myInvoker.setParameter(new SomeParameter());
 *    
 *    //
 *    // Execute Invoker's invoke method that triggers execution of 
 *    // Command and Receiver
 *    //
 *    myInvoker.invoke();
 *    
 *    //
 *    // Optionally and if the Command is synchronous, retrieve a result
 *    //
 *    System.out.println(myInvoker.getResult().toString());
 * </code></pre>
 *
 * @param <P> Command Parameter context-specific
 * @param <R> Command Result context-specific
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 19, 2007 11:09:44 PM $
 */
public 
interface IReceiver<P, R> 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Sets the input parameter required for the execution of 
     * this <code>IReceiver</code>.
     * 
     * @param aParameter Parameter required for executing the
     *        <code>IReceiver</code>.
     * @throws IllegalArgumentException 'aParameter' must not be null.
     */
    public void 
    setParameter(P aParameter)
    throws IllegalArgumentException;

    //------------------------------------------------------------------------
    /**
     * Returns result out of executing this <code>IReceiver</code>.
     * 
     * @return Result out of executing this <code>IReceiver</code>.
     * @throws IllegalStateException No result available.
     */
    public R 
    getResult()
    throws IllegalStateException;    
    
    //------------------------------------------------------------------------
    /**
     * Starts the execution of the <code>IReceiver</code>.
     * 
     * @throws IllegalStateException Parameter not set.
     */
    public void 
    execute()
    throws IllegalStateException;        
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IInvoker.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * <b>Responsibility</b>: Abstract generic definition of the "Invoker".
 * <ul>
 * <li>Asks the command to carry out the request.</li>
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
 * @version $Revision: 1.0 $ $Date: Jun 19, 2007 11:11:00 PM $
 */
public 
interface IInvoker<P, R>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Sets the <code>ICommand</code> to start as result of executing the 
     * invoke method.
     * 
     * @param aCommand <code>ICommand</code> to set.
     */
    public void 
    setCommand(ICommand<P, R> aCommand);

    //------------------------------------------------------------------------
    /**
     * Returns the result of the execution of the <code>ICommand</code>. 
     * 
     * @return Result of the execution of the <code>ICommand</code>. 
     * @throws IllegalStateException 'ICommand was not set'
     * @throws IllegalStateException 'No results available'
     */
    public R 
    getResult()
    throws IllegalStateException;

    //------------------------------------------------------------------------
    /**
     * Starts the appropriate <code>ICommand</code>.
     * 
     * @throws IllegalStateException 'ICommand was not set'
     */    
    public void 
    invoke()
    throws IllegalStateException;

    //------------------------------------------------------------------------
    /**
     * Sets the parameter required for the execution of the target 
     * <code>ICommand</code>.
     * 
     * @param aParameter Parameter required for the execution of the target 
     *        <code>ICommand</code>.
     * 
     * @throws IllegalArgumentException Underlying implementations will 
     *         impose different preconditions on the aParameter argument
     */
    public void 
    setParameter(P aParameter);
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IVisitor.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.behavioral.visitor;

/**
 * <b>Visitor Design Pattern</b>: Represent an operation to be performed on the
 * elements of an object structure. Visitor lets you define a new operation
 * without changing the classes of the elements on which it operates.<br/>
 * <br/>
 * 
 * <b>Responsibility</b> Abstract definition of "Visitor".
 * 
 * <ul>
 * <li>Declares a {@link #visit} operation for each class of ConcreteElement 
 * in the object structure. The operation's name and signature identifies the 
 * class that sends the Visit request to the visitor. That lets the visitor 
 * determine the concrete class of the element being visited. Then the visitor 
 * can access the elements directly through its particular interface.</li>
 * </ul>
 * 
 * <br/>
 * Example usage:
 * <pre><code>
 *    //
 *    // Abstract Base Node for a simple Element hierarchy.
 *    //  
 *    abstract
 *    class AbstractNode 
 *    implements IElement 
 *    {
 *        //--------------------------------------------------------------------
 *        public 
 *        BaseNode(String aName) 
 *        {
 *            theName = aName;
 *        }
 *                
 *        //--------------------------------------------------------------------
 *        public final String 
 *        getName() 
 *        {
 *            return theName;
 *        }        
 *
 *        //--------------------------------------------------------------------
 *        // members
 *        //--------------------------------------------------------------------
 *        private final String theName;
 *    }
 *
 *    //
 *    // RedNode
 *    //  
 *    class RedNode 
 *    extends AbstractNode 
 *    {
 *        //--------------------------------------------------------------------
 *        public 
 *        RedNode() 
 *        {
 *            super("Red");
 *        }
 *    }
 *    
 *    //
 *    // BlackNode
 *    //  
 *    class BlackNode 
 *    extends AbstractNode 
 *    {
 *        //--------------------------------------------------------------------
 *        public 
 *        BlackNode() 
 *        {
 *            super("Black");
 *        }
 *    }
 *    
 *    //
 *    // Simple Visitor
 *    // 
 *    class NodeVisitor
 *    extends AbstractVisitor&lt;AbstractNode&gt;
 *    {
 *        //--------------------------------------------------------------------
 *        public void
 *        visitRedNode(RedNode aNode)
 *        {
 *             // do RedNode visiting
 *             System.out.println(aNode.getName());    
 *        }
 *        
 *        //--------------------------------------------------------------------
 *        public void
 *        visitBlackNode(BlackNode aNode)
 *        {
 *             // do BlackNode visiting
 *             System.out.println(aNode.getName());    
 *        }
 *    }
 *    
 *    //
 *    // Usage example
 *    //
 *    IVisitor&lt;AbstractNode&gt; myVisitor = new NodeVisitor();
 *    myVisitor.visit(new RedNode());
 *    myVisitor.visit(new BlackNode());
 * </code></pre>
 * 
 * @param <E> Element type that this <code>IVisitor</code> can visit.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jul 1, 2007 6:54:54 AM $
 */
public 
interface IVisitor<E> 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Abstract definition of the Visitor's Pattern <code>visit</code> method. 
     * This method will alone handle the dynamic double-dispatch operation 
     * avoiding the need for a counterpart <code>accept</code> method in the 
     * <code>IElement</code> hierarchy. First the <code>visit</visit> method 
     * will resolve to the base Visitor implementation's by dynamic invocation 
     * i.e. overriding. Then the base Visitor implementation will lookup 
     * automatically by a second dynamic dispatch mechanism the right Visitor's 
     * visit implementation depending on the concrete <code>IElement</code> 
     * sub-type. 
     * 
     * @param anElement <code>IElement</code> instance to visit.
     * @throws IllegalArgumentException 'anElement' must not be null.
     */
    public void 
    visit(E anElement)
    throws IllegalArgumentException;
}

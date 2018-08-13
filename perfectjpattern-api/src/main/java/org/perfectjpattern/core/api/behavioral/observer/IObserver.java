//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IObserver.java Copyright (c) 2012 Giovanni Azua Garcia
// bravegag@hotmail.com
//  
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 3
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, see <http://www.gnu.org/licenses/>.
//
//----------------------------------------------------------------------
package org.perfectjpattern.core.api.behavioral.observer;

/**
 * <b>Observer Design Pattern</b>: <i>"Define a one-to-many dependency between
 * objects so that when one object changes state, all its dependents are
 * notified and updated automatically."</i> (Gamma et al, Design Patterns)
 * <br/>
 * <br/>
 * 
 * <b>Responsibility</b>: Abstract generic definition of the "Observer".
 * pattern role.<br/>
 * <ul>
 * <li>Defines an updating interface for objects that should be notified of
 * changes in a subject.</li>
 * </ul>
 * 
 * <b>Notes</b>:
 * Improves over the original GoF recipe and Java Observer implementation 
 * in that:<br/>
 * <ul>
 * <li>Push over Pull approach: <code>IObserver</code> instances are not 
 * coupled to the <code>ISubject</code> interface i.e. there is no need for the 
 * <code>IObserver</code> to query <code>ISubject</code>'s state. State 
 * change or Message content is pushed to the {@link #update} callback 
 * method.</li> 
 * <li>Strongly typed generic parameter eliminates the need for 
 * <code>IObserver</code> to downcast the parameter as is the case for 
 * e.g. <code>java.util.Observer</code> implementation.</li>
 * </ul>
 *
 * <br/>
 * Example usage:
 * <pre><code>
 *    //
 *    // Create Observer Pattern elements 
 *    //  
 *    ISubject mySubject = new ConcreteSubject();
 *    IObserver myFirstObserver = new ConcreteObserver();
 *    IObserver mySecondObserver = new ConcreteObserver();
 *    
 *    //
 *    // Subscribe multiple Observer instances to the Subject
 *    //
 *    mySubject.attach(myFirstObserver, mySecondObserver);
 *    
 *    //
 *    // Notify all subscribed Observers, optionally pass some Event 
 *    // data associated with the Event.
 *    //
 *    mySubject.notifyObservers(NullEventData.getInstance());
 *    
 *    //
 *    // Detach Observer instances
 *    //
 *    mySubject.detach(myFirstObserver);
 * </code></pre>
 *
 * @param <E> Type of event data that this <code>IObserver</code> subscribes 
 * to.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 11, 2007 6:16:01 AM $
 */
public 
interface IObserver<E> 
{
    //-------------------------------------------------------------------------
    // public
    //-------------------------------------------------------------------------
    /**
     * Notification from <code>ISubject</code> instance that a change 
     * has ocurred.
     * 
     * @param anEventData Event data pushed to <code>IObserver</code> 
     *        instances.
     */
    public void
    update(E anEventData);
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ISubject.java Copyright (c) 2012 Giovanni Azua Garcia
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
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
// MA 02110-1301, USA.
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
 * <b>Responsibility</b>: Abstract generic definition of the "Subject".
 * pattern role:<br/>
 * <ul>
 * <li>Knows its observers. Any number of <code>IObserver</code> objects may
 * observe a <code>ISubject</code>.</li>
 * <li>Provides an interface for attaching and detaching <code>IObserver</code>
 * objects.</li>
 * </ul>
 * 
 * <b>Notes</b>:
 * Improves over the original GoF recipe and Java Observer implementation 
 * in that:<br/>
 * <ul>
 * <li>Push over Pull approach: <code>IObserver</code> instances are not 
 * coupled to the <code>ISubject</code> interface i.e. there is no need for the 
 * <code>IObserver</code> to query <code>ISubject</code>'s state. State 
 * change or Message content is pushed to the {@link #notifyObservers} 
 * callback method.</li> 
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
 *    ISubject&lt;NullEventData&gt; mySubject = new ConcreteSubject();
 *    IObserver&lt;NullEventData&gt; myFirstObserver = new ConcreteObserver();
 *    IObserver&lt;NullEventData&gt; mySecondObserver = new ConcreteObserver();
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
 * @param <E> Type of event data that this <code>ISubject</code> 
 * notifies with.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 11, 2007 6:21:08 AM $
 */
public 
interface ISubject<E> 
{
    //-------------------------------------------------------------------------
    // public
    //-------------------------------------------------------------------------
    /**
     * Attaches a variable list of <code>IObserver</code> instances to the 
     * <code>ISubject</code> instance.
     * 
     * @param anObservers <code>IObserver</code> instances to be attached. 
     * @throws IllegalArgumentException 'anObservers' must not be null. 
     */
    @SuppressWarnings("unchecked")
    public void 
    attach(IObserver<E>... anObservers);

    //-------------------------------------------------------------------------
    /**
     * Detaches all registered <code>IObserver</code> instances from this
     * <code>ISubject</code>
     */
    public void 
    clear();

    //-------------------------------------------------------------------------
    /**
     * Detaches a variable list of <code>IObserver</code> instances from the 
     * <code>ISubject</code> instance.
     * 
     * @param anObservers {@link IObserver} instances to be detached.
     * @throws IllegalArgumentException 'anObservers' must not be null. 
     */
    @SuppressWarnings("unchecked")
    public void 
    detach(IObserver<E>... anObservers);

    //-------------------------------------------------------------------------
    /**
     * Notifies the attached <code>IObserver</code> instances that a change 
     * has occurred. It is compulsory to provide a non null parameter 
     * <code>anEventData</code>. For situations where passing information is 
     * not required use instead <code>NullEventData</code> singleton instance.
     * 
     * @param anEventData Event data pushed to <code>IObserver</code> 
     *        instances.
     * @throws IllegalArgumentException 'anEventData' must not be null. 
     */
    public void 
    notifyObservers(E anEventData);

    //-------------------------------------------------------------------------
    /**
     * Returns the number of attached {@link IObserver} instances
     * 
     * @return Number of attached {@link IObserver} instances
     */
    public int 
    size();
}

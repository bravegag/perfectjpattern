//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IAdapter.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.structural.adapter;

import org.perfectjpattern.core.api.structural.*;

/**
 * <b>Adapter Design Pattern</b>: Convert the interface of a class into another 
 * interface clients expect. Adapter lets classes work together that couldn't 
 * otherwise because of incompatible interfaces.
 * <br/><br/>
 * 
 * <b>Responsibility</b> Abstract definition of the "Adapter": 
 * <br/>
 * <ul>
 * <li>adapts the interface Adaptee to the Target interface.</li> 
 * </ul>
 *
 * @param <T> <code>Target</code> element type
 * @param <A> <code>Adaptee</code> element type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jan 28, 2009 12:51:08 PM $
 */
public 
interface IAdapter<T, A>
extends ISurrogate<T>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns the <code>Target</code> instance
     * 
     * @return <code>Target</code> instance
     */
    public T
    getTarget();    

    //------------------------------------------------------------------------
    /**
     * Returns the <code>Adaptee</code> instance
     * 
     * @return <code>Adaptee</code> instance
     */
    public A
    getAdaptee();
    
    //------------------------------------------------------------------------
    /**
     * Sets the adapting strategy e.g. exact, name, user-defined
     * 
     * @param anAdaptingStrategy {@link IAdaptingStrategy} instance to assign
     * @throws IllegalArgumentException 'anAdaptingStrategy' must not be null
     */
    public void
    setAdaptingStrategy(IAdaptingStrategy anAdaptingStrategy)
    throws IllegalArgumentException;
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IAdaptingStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.lang.reflect.*;

import org.perfectjpattern.core.api.behavioral.strategy.*;


/**
 * Abstract definition of the adapting strategy to use e.g.
 * <ul>
 * <li>Signature exact matching: the Adaptee and Target methods (name and 
 * parameters) must match or at least the Adaptee methods must be a subset 
 * of Target methods</li>
 * <li>Signature types matching: the Adaptee and Target methods must 
 * match or at least the Adaptee methods must be a subset of the type 
 * signature of the Target methods</li>
 * <li>Names mapping: The user defines dynamically the correspondence 
 * between methods in Adaptee and Target by name</li>
 * </ul> 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jan 28, 2009 1:00:42 PM $
 */
public 
interface IAdaptingStrategy
extends IStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * According to the definition of this strategy validates the tuple <code>
     * Target</code>/<code>Adaptee</code>. Should the two not match an 
     * {@link IllegalArgumentException} will be thrown.
     * 
     * @param aTargetClass Target class type
     * @param anAdaptee Adaptee instance 
     * @param anAdapter Adapter instance 
     * @throws IllegalArgumentException
     */
    public void
    validate(Class<?> aTargetClass, Object anAdaptee, Object anAdapter)
    throws IllegalArgumentException;

    //------------------------------------------------------------------------
    /**
     * Returns the <code>Adaptee</code> or otherwise <code>Adapter</code> method
     * that corresponds to the given <code>Target</code> class type. If the 
     * corresponding method is not found an {@link IllegalArgumentException} 
     * will be thrown.
     * 
     * @see #validate(Class, Object, Object)
     *
     * @param aTargetClass Target class type
     * @param anAdaptee Adaptee instance
     * @param anAdapter Adapter instance
     * @param aTargetMethod Target method to search for in the Adaptee
     * @return <code>Adaptee</code> method that corresponds to the given
     *         <code>Target</code> class type
     * @throws IllegalArgumentException
     */
    public Method
    resolve(Class<?> aTargetClass, Object anAdaptee, Object anAdapter, 
        Method aTargetMethod)
    throws IllegalArgumentException;
}

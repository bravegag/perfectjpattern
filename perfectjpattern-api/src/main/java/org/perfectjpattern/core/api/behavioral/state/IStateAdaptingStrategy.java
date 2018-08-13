//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IStateAdaptingStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.behavioral.state;

import org.perfectjpattern.core.api.structural.adapter.*;

/**
 * Abstract subclass of {@link IAdaptingStrategy} allowing the State Pattern 
 * implementation to feature partial States. Supporting partial States is 
 * good from the maintenance standpoint:
 * <ul>
 * <li>client code will not need to define and maintain a full blown 
 * {@link IState} implementation in every concrete state</li>
 * <li>Provides a clean way to define default request handling behavior 
 * e.g. throwing {@link UnsupportedOperationException}</li>
 * </ul>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 17, 2009 10:56:08 AM $
 */
public 
interface IStateAdaptingStrategy
extends IAdaptingStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Defines how to handle undefined {@link IRequest} or {@link IState} 
     * transitions
     */
    public void
    handleUnsupported();
}

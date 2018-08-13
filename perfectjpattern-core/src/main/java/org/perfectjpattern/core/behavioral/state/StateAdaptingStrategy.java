//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// StateAdaptingStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.state;

import java.lang.reflect.*;
import java.text.*;
import java.util.*;

import org.perfectjpattern.core.api.behavioral.state.*;
import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.core.structural.adapter.*;

/**
 * Concrete {@link IAdaptingStrategy} tailored for adapting partial 
 * implementations of concrete {@link IState} instances
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 14, 2009 6:39:50 PM $
 */
public 
class StateAdaptingStrategy
extends ExactMatchAdaptingStrategy
implements IStateAdaptingStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public Method 
    resolve(Class<?> aTargetClass, Object anAdaptee, Object anAdapter, 
        Method aTargetMethod)
    throws IllegalArgumentException, UnsupportedOperationException
    {
        Method myMethod = null;
        try
        {
            myMethod = super.resolve(aTargetClass, anAdaptee, anAdapter, 
                aTargetMethod);
        }
        catch (IllegalArgumentException anException)
        {
            theAdapteeClass = anAdaptee.getClass();
            theTargetMethod = aTargetMethod;
            
            handleUnsupported();
        }
        
        return myMethod;
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    handleUnsupported() 
    throws UnsupportedOperationException
    {
        // provide default behavior
        String myMessage = MessageFormat.format(EXCEPTION_PATTERN, 
            theAdapteeClass.getSimpleName(), theTargetMethod.getName());
        
        throw new UnsupportedOperationException(myMessage);        
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public void 
    handleMismatch(Set<String> aTargetMethods)
    {
        // deliberately ignore missing methods, this is a partial 
        // IState implementation and the undefined IRequest are handled 
        // with a default behavior e.g. throwing UnsupportedOperationException
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private Class<?> theAdapteeClass;
    private Method theTargetMethod;
    private static final String EXCEPTION_PATTERN = "State ''{0}'' won't " +
        "handle Request ''{1}''";
}

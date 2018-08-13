//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// DynamicDelegator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.extras.delegate;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.extras.delegate.*;


/**
 * Implementation of <code>IDelegator</code> interface for creating
 * Delegate instances based on the dynamic </code>IDelegate</code>
 * type. <code>DynamicDelegator</code> should be used in cases where
 * there is no known interface definition for the targeted method.
 * <br/><br/>
 * <b>Notes</b>: Base source code implemented by Steve Lewis and Wilhelm
 * Fitzpatrick and adapted to fit PerfectJPattern componentization
 * criteria and code conventions.
 *
 * @see IDelegate
 *
 * @author <a href="mailto:smlewis@lordjoe.com">Steve Lewis</a>
 * @author <a href="mailto:wilhelmf@agileinformatics.com">Wilhelm
 * Fitzpatrick</a>
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 25, 2007 7:07:18 AM $
 */
public final
class DynamicDelegator
extends AbstractDelegator<IDelegate>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Construct a <code>DynamicDelegator</code> by specifying the target
     * method signature. The method signature is defined by the types of the
     * parameters and return.
     *
     * @param aReturnClass Return type for the targeted method.
     * @param aParameters Array of method parameter types.
     */
    public DynamicDelegator(Class<?> aReturnClass, Class<?>... aParameters)
    {
        super(aReturnClass, aParameters);
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public IDelegate
    build(Class<?> aTarget, String aMethodName)
    throws IllegalArgumentException, UnsupportedOperationException
    {
        Validate.notNull(aTarget, "'aTarget' must not be null");
        Validate.notNull(aMethodName, "'aMethodName' must not be null");

        DelegateProxy myDelegate = new DelegateProxy(null, aTarget, aMethodName,
            this);

        return myDelegate;
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public IDelegate
    build(Object aTarget, String aMethodName)
    throws IllegalArgumentException, UnsupportedOperationException
    {
        Validate.notNull(aTarget, "'aTarget' must not be null");
        Validate.notNull(aMethodName, "'aMethodName' must not be null");

        DelegateProxy myDelegate = new DelegateProxy(aTarget, aTarget.
            getClass(), aMethodName, this);

        return myDelegate;
    }
}

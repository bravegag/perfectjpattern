//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractReceiver.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.command;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.command.*;


/**
 * Reusable base abstract implementation of {@link IReceiver} 
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 13, 2008 2:24:53 PM $
 */
public abstract 
class AbstractReceiver<P, R>
implements IReceiver<P, R>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public R
    getResult()
    {
        return theResult;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    setParameter(P aParameter)
    throws IllegalArgumentException
    {
        Validate.notNull(aParameter, "'aParameter' must not be null");
        
        theParameter = aParameter;
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Returns the previously assigned Parameter
     * 
     * @return the parameter
     */
    protected P 
    getParameter()
    {
        return theParameter;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the Result
     * 
     * @param aResult the result to set
     */
    protected void 
    setResult(R aResult)
    {
        assert aResult != null : "'aResult' must not be null";

        theResult = aResult;
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private P theParameter;
    private R theResult;
}

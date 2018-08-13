//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Invoker.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Base core implementation of <code>IInvoker</code> interface.
 * <br/>
 * 
 * @see IInvoker
 * 
 * @param <P> Command Parameter context-specific.
 * @param <R> Command Result context-specific.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 23, 2007 2:34:57 AM $
 */
public
class Invoker<P, R>
implements IInvoker<P, R> 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public R
    getResult() 
    throws IllegalStateException
    {
        if (theCommand == null)
        {
            throw new IllegalStateException("ICommand was not set.");
        }

        R myResult = theCommand.getResult();
        if (myResult == null)
        {
            throw new IllegalStateException("No result available.");
        }

        return myResult;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    invoke() 
    throws IllegalStateException    
    {
        if (theCommand == null)
        {
            throw new IllegalStateException("ICommand was not set.");
        }
        
        if (theParameter != null)
        {
            theCommand.setParameter(theParameter);
        }
        
        theCommand.execute();
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    setCommand(ICommand<P, R> aCommand) 
    {
        theCommand = aCommand;
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
    // members
    //------------------------------------------------------------------------
    /**
     * The <code>ICommand</code> instance.
     */
    private ICommand<P, R> theCommand;

    /**
     * The <code>IParameter</code> instance.
     */
    private P theParameter;
}

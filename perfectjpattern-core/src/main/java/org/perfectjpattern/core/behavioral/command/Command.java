//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Command.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Base core implementation of <code>ICommand</code> interface.
 * <br/>
 * 
 * @see ICommand
 * 
 * @param <P> Command Parameter context-specific.
 * @param <R> Command Result context-specific.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 23, 2007 3:00:54 AM $
 */
public 
class Command<P, R>
implements ICommand<P, R>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    Command()
    {
        // empty
    }

    //------------------------------------------------------------------------
    public 
    Command(IReceiver<P, R> aReceiver)
    {
        super();
        
        setReceiver(aReceiver);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public R
    getResult() 
    throws IllegalStateException
    {
        if (theReceiver == null)
        {
            throw new IllegalStateException("IReceiver was not set.");
        }

        R myResult = theReceiver.getResult();
        
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
    setParameter(P aParameter) 
    throws IllegalArgumentException
    {
        Validate.notNull(aParameter, "'aParameter' must not be null");

        theParameter = aParameter;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    setReceiver(IReceiver<P, R> aReceiver) 
    throws IllegalArgumentException
    {
        Validate.notNull(aReceiver, "'aReceiver' must not be null");
        
        theReceiver = aReceiver;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    execute() 
    throws IllegalStateException
    {
        if (theReceiver == null)
        {
            throw new IllegalStateException("IReceiver was not set.");
        }        
        
        if (theParameter != null)
        {
            theReceiver.setParameter(theParameter);
        }
        
        theReceiver.execute();
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * The <code>IReceiver</code> instance.
     */
    private IReceiver<P, R> theReceiver;    
    
    /**
     * The <code>IParameter</code> instance.
     */
    private P theParameter;
}

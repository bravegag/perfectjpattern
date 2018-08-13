//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.chainofresponsibility;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.chainofresponsibility.*;


/**
 * Abstract reusable implementation of <code>IHandler</code> interface.
 * <br/><br/>
 * 
 * <b>The Default Strategy is <code>OnlyOneHandleStrategy</code></b> same as 
 * in GoF.
 *  
 * @see IHandler
 * @see NullHandler
 *
 * @param <R> Request parameter type
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 23, 2007 1:31:27 PM $
 */
public abstract 
class AbstractHandler<R> 
implements IHandler<R>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs a AbstractHandler from a NullHandler
     */
    @SuppressWarnings("unchecked")
    public 
    AbstractHandler() 
    {
        theSuccessor = (IHandler<R>) NullHandler.getInstance();
    }    

    //------------------------------------------------------------------------
    /**
     * Constructs a AbstractHandler from a Successor
     * 
     * @param aSuccessor Successor handler.
     * @throws IllegalArgumentException 'aSuccessor' must not be null.
     */
    public 
    AbstractHandler(IHandler<R> aSuccessor)
    throws IllegalArgumentException
    {
        Validate.notNull(aSuccessor, "'aSuccessor' must not be null");
        
        theSuccessor = aSuccessor;
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public final void 
    start(R aRequest) 
    throws IllegalArgumentException
    {
        IHandler<Object> myHandler = (IHandler<Object>) this;
        
        theStrategy.process(myHandler, aRequest);
    }        
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final IHandler<R>
    getSuccessor() 
    {        
        return theSuccessor;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    canHandle(R aRequest) 
    throws IllegalArgumentException
    {
        return true;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void
    handle(R aRequest) 
    throws IllegalArgumentException
    {
        Validate.notNull(aRequest, "'aRequest' must not be null");
        Validate.isTrue(canHandle(aRequest), "'aRequest' can not be handled.");
    }    

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final void 
    setSuccessor(IHandler<R> aSuccessor) 
    throws IllegalArgumentException
    {
        Validate.notNull(aSuccessor, "'aSuccessor' must not be null");
        
        theSuccessor = aSuccessor;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final void 
    setChainStrategy(IChainStrategy aStrategy)
    throws IllegalArgumentException
    {
        theStrategy = aStrategy;
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Successor Handler
     */
    private IHandler<R> theSuccessor;
    
    /**
     * Defines the Chain Strategy. <b>The Default Strategy is <code>
     * OnlyOneHandleStrategy</code></b> same as in GoF.
     */
    private IChainStrategy theStrategy = OnlyOneHandleStrategy.getInstance();
}
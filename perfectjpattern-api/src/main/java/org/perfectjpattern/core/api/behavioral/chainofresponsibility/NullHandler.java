//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// NullHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.api.behavioral.chainofresponsibility;

import org.perfectjpattern.core.api.creational.singleton.*;

/**
 * Null Object Pattern implementation of <code>IHandler</code>. 
 * <br/><br/>
 * <code>NullHandler</code> is a Singleton therefore it may not be directly 
 * instantiated, neither it may be extended.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 23, 2007 1:40:23 PM $
 */
public final 
class NullHandler
implements IHandler<Object>, ISingleton
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of <code>NullHandler</code>.
     * 
     * @return Singleton instance of <code>NullHandler</code>.
     */
    public static NullHandler
    getInstance()
    {
        return INSTANCE;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public IHandler<Object> 
    getSuccessor()
    throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("'NullHandler' does not " +
            "support method getSuccessor().");
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    start(Object aRequest) 
    throws IllegalArgumentException
    {
        // do nothing
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    canHandle(Object aRequest) 
    throws IllegalArgumentException
    {
        return true;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void
    handle(Object aRequest)
    {
        // do nothing
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    setSuccessor(IHandler<Object> aSuccessor)
    throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("'NullHandler' does not " +
            "support method setSuccessor().");
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    setChainStrategy(IChainStrategy aStrategy)
    throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("'NullHandler' does not " +
            "support method setChainStrategy().");
    }    
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    private 
    NullHandler()
    {        
        // do nothing        
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Singleton instance of <code>NullHandler</code>
     */
    private static final NullHandler INSTANCE = new NullHandler();
}

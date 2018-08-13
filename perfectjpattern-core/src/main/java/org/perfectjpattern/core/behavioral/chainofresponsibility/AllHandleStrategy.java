//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// FirstNHandleStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.behavioral.chainofresponsibility.*;

/**
 * Concrete implementation of <code>IChainStrategy</code> that allows
 * all <code>IHandler</code> instances in the Chain to handle the given request.
 * <br/><br/>
 * <code>AllHandleStrategy</code> is stateless, reentrant and a Singleton 
 * therefore it may not be directly instantiated, neither it may be extended.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 8, 2007 9:57:12 PM $
 */
public final 
class AllHandleStrategy
implements IChainStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of <code>AllHandleStrategy</code>.
     * 
     * @return Singleton instance of <code>AllHandleStrategy</code>.
     */
    public static IChainStrategy
    getInstance()
    {
        return INSTANCE;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    process(IHandler<Object> aHandler, Object aRequest)
    {
        if (aHandler.canHandle(aRequest))
        {
            aHandler.handle(aRequest);
        }
        
        aHandler.getSuccessor().start(aRequest);            
    }
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    private 
    AllHandleStrategy()
    {
        // do nothing
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final IChainStrategy INSTANCE = new AllHandleStrategy();
}
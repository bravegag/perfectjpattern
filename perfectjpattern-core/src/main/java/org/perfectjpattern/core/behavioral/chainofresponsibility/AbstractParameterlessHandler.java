//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractParameterlessHandler.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Abstract reusable implementation of <code>IParameterlessHandler</code> 
 * interface.
 * <br/>
 * 
 * @see IParameterlessHandler
 * @see NullRequest
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 6, 2007 6:15:59 PM $
 */
public abstract 
class AbstractParameterlessHandler
extends AbstractHandler<NullRequest>
implements IParameterlessHandler
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructs new instance of AbstractParameterlessHandler 
     */
    public 
    AbstractParameterlessHandler()
    {
        super();
    }

    //------------------------------------------------------------------------
    /**
     * Constructs new instance of AbstractParameterlessHandler specifying the 
     * Successor Element.
     * 
     * @param aSuccessor The Successor Element.
     */
    public 
    AbstractParameterlessHandler(IParameterlessHandler aSuccessor)
    {
        super(aSuccessor);
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public final void 
    start()
    {
        super.start(NullRequest.getInstance());
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean 
    canHandle(NullRequest aRequest)
    throws IllegalArgumentException
    {        
        return true;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public final void 
    handle(NullRequest aRequest) 
    throws IllegalArgumentException
    {
        handle();
    }            
}
//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractWidget.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Abstract base definition of a AbstractWidget. Adapts or maps the inner
 * Handler implementation into abstract methods that can be overridden by 
 * subclasses.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 19, 2008 12:25:59 PM $
 */
public abstract 
class AbstractWidget
implements IHelpAware, IPrintAware
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public IParameterlessHandler
    getHelpHandler()
    {
        return theHelpHandler;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public IParameterlessHandler 
    getPrintHandler()
    {
        return thePrintHandler;
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    protected abstract
    boolean canHandleHelp();

    //------------------------------------------------------------------------
    protected abstract
    void handleHelp();

    //------------------------------------------------------------------------
    protected abstract
    boolean canHandlePrint();

    //------------------------------------------------------------------------
    protected abstract
    void handlePrint();

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Inner implementation that handles Help requests
     */
    private IParameterlessHandler theHelpHandler = 
        new AbstractParameterlessHandler()
    {
        //--------------------------------------------------------------------
        @Override
        public boolean 
        canHandle(NullRequest aRequest)
        throws IllegalArgumentException
        {
            return canHandleHelp();
        }

        //--------------------------------------------------------------------
        public void handle()
        {
            handleHelp();
        }        
    };
    
    //------------------------------------------------------------------------
    /**
     * Inner implementation that handles Print requests
     */
    private IParameterlessHandler thePrintHandler = 
        new AbstractParameterlessHandler()
    {
        //--------------------------------------------------------------------
        @Override
        public boolean 
        canHandle(NullRequest aRequest)
        throws IllegalArgumentException
        {
            return canHandlePrint();
        }

        //--------------------------------------------------------------------
        public void 
        handle()
        {
            handlePrint();
        }                
    };
}

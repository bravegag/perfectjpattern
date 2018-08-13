//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Dialog.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.slf4j.*;

/**
 * Dialog widget
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 19, 2008 12:25:45 PM $
 */
public 
class Dialog
extends AbstractWidget
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    Dialog(String aName)
    {
        theName = aName;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected boolean 
    canHandleHelp()
    {
        return false;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected boolean 
    canHandlePrint()
    {
        return true;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected void 
    handleHelp()
    {
        throw new IllegalStateException("The Help request can not be handled");
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected void 
    handlePrint()
    {
        theLogger.debug(theName + " dialog is Printing ...");
    }
    
    //------------------------------------------------------------------------
    protected static void
    setLogger(Logger aLogger)
    {
        theLogger = aLogger;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final String theName;

    /**
     * Provides logging facilities for this class instance 
     */
    private static Logger theLogger = LoggerFactory.getLogger(Button.class);
}

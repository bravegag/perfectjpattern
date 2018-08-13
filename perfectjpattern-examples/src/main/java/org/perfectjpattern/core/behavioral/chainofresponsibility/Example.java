//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Example.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.util.*;

import org.perfectjpattern.core.api.behavioral.chainofresponsibility.*;

/**
 * Startup Main for the Chain Of Responsibility Pattern Example code
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 5, 2008 10:11:17 PM $
 */
// CHECKSTYLE:OFF
public final
class Example
// CHECKSTYLE:ON
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public static void 
    main(String[] anArguments)
    {
        //---------------------------------------------------------------
        // Create all components of the Chain of Responsibility 
        //---------------------------------------------------------------
        Button myOkButton = new Button("OK");
        Button myPrintButton = new Button("Print");
        Dialog mySaveDialog = new Dialog("Save");
        Dialog myPrintDialog = new Dialog("Print");
        Application myApplication = new Application();
        
        //---------------------------------------------------------------
        // Build "Show Help" Chain of Responsibility 
        //---------------------------------------------------------------
        myOkButton.getHelpHandler().setSuccessor(myPrintDialog.
            getHelpHandler());
        myPrintButton.getHelpHandler().setSuccessor(myPrintDialog.
            getHelpHandler());
        myPrintDialog.getHelpHandler().setSuccessor(myApplication.
            getHelpHandler());
        mySaveDialog.getHelpHandler().setSuccessor(myApplication.
            getHelpHandler());

        //---------------------------------------------------------------
        // Execute "Show Help" Chain of Responsibility from OK Button 
        //---------------------------------------------------------------
        myOkButton.getHelpHandler().start();
        
        //---------------------------------------------------------------
        // Execute "Show Help" Chain of Responsibility from Save Dialog
        //---------------------------------------------------------------       
        mySaveDialog.getHelpHandler().start();

        //---------------------------------------------------------------
        // Build "Print" Chain of Responsibility with a strategy 
        // AllHandleStrategy different than the default GoF Chain 
        // OnlyOneHandleStrategy
        //---------------------------------------------------------------
        myOkButton.getPrintHandler().setSuccessor(myPrintDialog.
            getPrintHandler());
        myPrintButton.getPrintHandler().setSuccessor(myPrintDialog.
            getPrintHandler());
        myPrintDialog.getPrintHandler().setSuccessor(myApplication.
            getPrintHandler());
        mySaveDialog.getPrintHandler().setSuccessor(myApplication.
            getPrintHandler());
        
        //---------------------------------------------------------------
        // for this use-case we switch to the default Strategy 
        // AllHandleStrategy
        //---------------------------------------------------------------
        List<IHandler<NullRequest>> myHandlers = new ArrayList<IHandler<
            NullRequest>>();
        myHandlers.add(myOkButton.getPrintHandler());
        myHandlers.add(myPrintButton.getPrintHandler());
        myHandlers.add(myPrintDialog.getPrintHandler());
        myHandlers.add(mySaveDialog.getPrintHandler());
        myHandlers.add(myApplication.getPrintHandler());
        
        for (IHandler<NullRequest> myHandler : myHandlers)
        {
            myHandler.setChainStrategy(AllHandleStrategy.getInstance());
        }
        
        //---------------------------------------------------------------
        // Execute the "Print" Chain of Responsibility from OK Button 
        //---------------------------------------------------------------
        myOkButton.getPrintHandler().start();
        
        //---------------------------------------------------------------
        // Execute the "Print" Chain of Responsibility from Save Dialog
        //---------------------------------------------------------------
        mySaveDialog.getPrintHandler().start();        
    }
}

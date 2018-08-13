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
package org.perfectjpattern.core.behavioral.command;

import org.perfectjpattern.core.api.behavioral.command.*;
import org.perfectjpattern.core.api.structural.composite.*;
import org.perfectjpattern.core.structural.composite.*;

/**
 * Startup Main for the Command Pattern Example code
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
        // Create simple use-cases with Open and Paste commands
        //---------------------------------------------------------------
        IParameterlessInvoker myOpenInvoker = new ParameterlessInvoker();
        myOpenInvoker.setCommand(new ParameterlessCommand(new Open()));
        myOpenInvoker.invoke();
        
        IParameterlessInvoker myPasteInvoker = new ParameterlessInvoker();
        myPasteInvoker.setCommand(new ParameterlessCommand(new Paste()));
        myPasteInvoker.invoke();        
                
        //---------------------------------------------------------------
        // Create macro use-case with multiple Open and Paste commands
        // i.e. a Composite Command
        //---------------------------------------------------------------
        IComposite<IParameterlessCommand> myComposite = new Composite<
            IParameterlessCommand>(IParameterlessCommand.class);
        myComposite.add(new ParameterlessCommand(new Open()));
        myComposite.add(new ParameterlessCommand(new Paste()));
        myComposite.add(new ParameterlessCommand(new Open()));
        myComposite.add(new ParameterlessCommand(new Paste()));
        
        IParameterlessCommand myMacroCommand = myComposite.getComponent();
    
        //---------------------------------------------------------------
        // note how Invoker is agnostic of the underlying Composite 
        // Macro Command
        //---------------------------------------------------------------
        IParameterlessInvoker myMacroInvoker = new ParameterlessInvoker();
        myMacroInvoker.setCommand(myMacroCommand);
        myMacroInvoker.invoke();
    }
}

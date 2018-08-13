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
package org.perfectjpattern.core.structural.decorator;

/**
 * Startup Main for the Decorator Pattern Example code
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
        // Create TextView decorated by Scroll and Border decorators
        //---------------------------------------------------------------
        IVisualComponent myTextView = new TextView();
        ScrollDecorator myScrollDecorator = new ScrollDecorator(
            new BorderDecorator(myTextView).getComponent());

        //---------------------------------------------------------------
        // get reference to the Decorator instance
        //---------------------------------------------------------------
        IScrollableComponent myScrollableComponent = myScrollDecorator.
            getComponent();

        assert myScrollableComponent.equals(myTextView) : 
            "Identity problems? I thought not!";
        
        //---------------------------------------------------------------
        // either call additional method ...
        //---------------------------------------------------------------
        myScrollableComponent.scrollTo();
        
        //---------------------------------------------------------------
        // or call [optionally decorated] component methods
        //---------------------------------------------------------------
        myScrollableComponent.draw();
        myScrollableComponent.focus();
        myScrollableComponent.hide();
    }
}

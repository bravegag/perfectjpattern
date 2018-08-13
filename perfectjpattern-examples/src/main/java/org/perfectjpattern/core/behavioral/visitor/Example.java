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
package org.perfectjpattern.core.behavioral.visitor;

import java.util.*;

import org.perfectjpattern.core.api.behavioral.visitor.*;

/**
 * Startup Main for the Visitor Pattern Example code
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
        // Create Element object
        //---------------------------------------------------------------
        Car myCar = new Car();
 
        //---------------------------------------------------------------
        // Create collection of Visitors
        //---------------------------------------------------------------
        List<IVisitor<ICarPart>> myVisitors = new ArrayList<IVisitor<
            ICarPart>>();
        myVisitors.add(new PrintVisitor());
        myVisitors.add(new DoVisitor());
 
        //---------------------------------------------------------------
        // Do the visiting, transparently achieves double-dispatch
        //---------------------------------------------------------------
        for (IVisitor<ICarPart> myVisitor : myVisitors)
        {
            myVisitor.visit(myCar);
        }
    }
}

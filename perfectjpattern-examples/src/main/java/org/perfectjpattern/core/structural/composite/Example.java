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
package org.perfectjpattern.core.structural.composite;

import java.util.*;

import org.perfectjpattern.core.api.structural.composite.*;

/**
 * Startup Main for the Composite Pattern Example code
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
        // Create composition using the reusable Composite implementation
        //---------------------------------------------------------------
        IComposite<IGraphic> myNestedComposite = new Composite<IGraphic>(
            IGraphic.class);
        myNestedComposite.add(new Rectangle());                
        myNestedComposite.add(new Line());
        myNestedComposite.add(new Line());

        IComposite<IGraphic> myComposite = new Composite<IGraphic>(IGraphic.
            class);
        myComposite.add(new Rectangle());        
        myComposite.add(new Text());
        myComposite.add(new Text());
        myComposite.add(myNestedComposite.getComponent());

        //---------------------------------------------------------------
        // Optionally define a custom order for the Composition
        //---------------------------------------------------------------
        Comparator<IGraphic> myComparator = new Comparator<IGraphic>()
        {
            //----------------------------------------------------------------
            public int 
            compare(IGraphic aGraphicA, IGraphic aGraphicB)
            {
                //
                // let's say we want to order alphabetically by type, except for
                // nested Composite types, we want Composites at the beginning
                //
                String myNameA = aGraphicA.getClass().getSimpleName();
                String myNameB = aGraphicB.getClass().getSimpleName();
                
                return myNameA.compareTo(myNameB);
            }            
        };
        
        Collections.sort(myNestedComposite, myComparator);
        Collections.sort(myComposite, myComparator);
        
        //---------------------------------------------------------------
        // Acquire reference to an IGraphic view of the Composite and call 
        // business methods on it
        //---------------------------------------------------------------
        IGraphic myGraphic = myComposite.getComponent();
        myGraphic.draw();        
    }
}

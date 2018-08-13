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
package org.perfectjpattern.core.structural.nullobject;

import java.util.*;

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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void 
    main(String[] anArguments)
    {
        //---------------------------------------------------------------
        // Create NullObject for a Collection type
        //---------------------------------------------------------------
        List<String> myList = new NullObject<List>(List.class).getComponent();
        myList.add("Won't do anything");        
        assert myList.size() == 0 : "List must be empty, this is a NullObject";
        assert myList.get(0) == null : "For any element i, get(i) will return null";
    }
}

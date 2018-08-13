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
package org.perfectjpattern.core.behavioral.delegate;

import java.util.*;

import org.perfectjpattern.core.api.extras.delegate.*;
import org.perfectjpattern.core.extras.delegate.*;
import org.slf4j.*;

/**
 * Startup Main for the Delegate Pattern Example code
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
        // Arbitrary types that have a size awareness but do not 
        // share a common interface, therefore it could not be possible 
        // writing a reusable code to get their sizes in a single way. 
        // In this case without using Delegates, different adapter 
        // implementations would be required:
        // 
        // * String.length() is a function
        // * StringBuilder.length() is another function
        // * List.size() is another function
        //---------------------------------------------------------------
        String myString = "Remember String instances are immutable ...";
        theLogger.debug(myString);
        theLogger.debug("String length is: '" + myString.length() + "'");
        
        StringBuilder myBuilder = new StringBuilder();
        myBuilder.append("StringBuilder on the other hand");
        myBuilder.append("are not immutable");
        theLogger.debug(myBuilder.toString());
        theLogger.debug("Builder length is: '" + myBuilder.length() + "'");
        
        List<Integer> myList = new ArrayList<Integer>();
        myList.add(Integer.valueOf(0));
        myList.add(Integer.valueOf(1));
        myList.add(Integer.valueOf(2));
        myList.add(Integer.valueOf(3));
        myList.add(Integer.valueOf(4));
        theLogger.debug(Arrays.deepToString(myList.toArray()));
        theLogger.debug("List size is: '" + myList.size() + "'");

        //---------------------------------------------------------------
        // Create a delegate that unifies the interface view of all different 
        // non-matching types and method signatures
        //---------------------------------------------------------------
        IDelegator<ISizeAware> myDelegator = new Delegator<ISizeAware>(
            ISizeAware.class);
        
        //---------------------------------------------------------------
        // Create the delegates i.e. "function pointers"
        //---------------------------------------------------------------
        List<ISizeAware> mySizeAwares = new ArrayList<ISizeAware>();
        mySizeAwares.add(myDelegator.build(myString, "length"));
        mySizeAwares.add(myDelegator.build(myBuilder, "length"));
        mySizeAwares.add(myDelegator.build(myList, "size"));
        
        //---------------------------------------------------------------
        // Use the delegates
        //---------------------------------------------------------------
        for (ISizeAware mySizeAware : mySizeAwares)
        {
            theLogger.debug(String.valueOf(mySizeAware.getSize()));
        }
    }
    
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    protected static void
    setLogger(Logger aLogger)
    {
        theLogger = aLogger;
    }    
        
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging facilities for this class 
     */
    private static Logger theLogger = LoggerFactory.getLogger(Example.class);
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Rectangle.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.slf4j.*;

/**
 * Rectangle concrete implementation of {@link IGraphic}
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 5, 2008 10:27:02 PM $
 */
public 
class Rectangle
implements IGraphic
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    draw()
    {
        theLogger.debug("Drawing a Rectangle");
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
     * Provides logging facilities for this class instance 
     */
    private static Logger theLogger = LoggerFactory.getLogger(Rectangle.class);
}

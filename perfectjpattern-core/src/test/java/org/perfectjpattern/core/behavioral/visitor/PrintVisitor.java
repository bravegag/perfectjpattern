//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// PrintVisitor.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.slf4j.*;

/**
 * Concrete Visitor implementation that exemplifies the case where your 
 * Visitor can directly extend PerfectJPattern's base reusable 
 * {@link AbstractVisitor} implementation
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Jun 8, 2008 11:57:41 PM $
 */
public 
class PrintVisitor
extends AbstractVisitor<ICarPart>
implements IHybridVisitor
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Visit Wheel
     * 
     * @param aWheel
     */
    public void 
    visitWheel(Wheel aWheel) 
    {
        theLogger.debug("Visiting " + aWheel.getName() + " wheel");
    }
 
    //------------------------------------------------------------------------
    /**
     * Visit Engine
     * 
     * @param anEngine
     */
    public void 
    visitEngine(Engine anEngine) 
    {
        theLogger.debug("Visiting engine");
    }
 
    //------------------------------------------------------------------------
    /**
     * Visit Body
     * 
     * @param aBody
     */
    public void 
    visitBody(Body aBody) 
    {
        theLogger.debug("Visiting body");
    }
 
    //------------------------------------------------------------------------
    /**
     * Visit Car
     * 
     * @param aCar
     */
    public void 
    visitCar(Car aCar) 
    {
        theLogger.debug("Visiting car");
        
        visitEngine(aCar.getEngine());
        visitBody(aCar.getBody());
        
        for (Wheel myWheel : aCar.getWheels())
        {
            visitWheel(myWheel);            
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
    private static Logger theLogger = LoggerFactory.getLogger(DoVisitor.
        class);   
}

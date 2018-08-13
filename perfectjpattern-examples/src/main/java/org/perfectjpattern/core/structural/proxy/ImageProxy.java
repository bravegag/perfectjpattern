//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ImageProxy.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.proxy;

import java.lang.reflect.*;

import org.slf4j.*;

/**
 * Proxy that control access to Image instances
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 5, 2008 11:57:56 PM $
 */
public 
class ImageProxy
extends AbstractProxy<IGraphic>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    ImageProxy(IGraphic aComponent)
    {
        super(IGraphic.class, aComponent);
    }

    //------------------------------------------------------------------------
    @Override
    protected Object 
    invokeUnderlying(Method aMethod, Object[] anArguments)
    throws Throwable
    {
        if (!theLoaded)
        {
            theLogger.debug("Proxy is now loading the Image ...");
            
            theLoaded = true;
        }
        
        return super.invokeUnderlying(aMethod, anArguments);
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
    private boolean theLoaded;

    /**
     * Provides logging facilities for this class instance 
     */
    private static Logger theLogger = LoggerFactory.getLogger(Image.class);
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// FilledState.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.state.state;

import org.perfectjpattern.core.api.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.*;
import org.perfectjpattern.core.behavioral.state.api.*;
import org.perfectjpattern.core.behavioral.state.request.*;
import org.slf4j.*;

/**
 * Concrete {@link IFilledState} implementation. Note that this 
 * implementation will not directly implement {@link IFilledState} 
 * but the relevant {@link IRequest} only
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 22, 2009 2:41:32 PM $
 */
public 
class FilledState
extends AbstractState<IOrderState, IFXLimitOrder>
implements IPurgeRequest
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    purge(IFXLimitOrder aFXLimitOrder)
    {
        theLogger.debug("Purging order '" + aFXLimitOrder + "'");
        
        super.next(aFXLimitOrder, IPurgeRequest.class);
    }    

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static Logger theLogger = LoggerFactory.getLogger(FilledState.
        class);
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractState.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.state;

import java.text.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.state.*;
import org.slf4j.*;

/**
 * Abstract base reusable implementation of {@link IState}
 * 
 * @param <State> Concrete State type
 * @param <Context> Concrete Context type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 2, 2009 1:36:30 PM $
 */
public abstract 
class AbstractState<State extends IState<?, ?>, 
    Context extends IContext<State, ?>>
implements IState<State, Context>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public boolean 
    isFinal()
    {
        return false;
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public <Request extends IRequest> State 
    getNext(Class<Request> aRequestClass)
    throws IllegalArgumentException, UnsupportedOperationException
    {
        Validate.notNull(aRequestClass, "'aRequestClass' must not be null");
        
        State myNextState = null;
        if (theNextStateMap.containsKey(aRequestClass))
        {
            myNextState = theNextStateMap.get(aRequestClass);
        }
        else
        {
            String myMessage = MessageFormat.format(EXCEPTION_PATTERN, 
                getClass().getSimpleName(), aRequestClass.getSimpleName());
            
            throw new UnsupportedOperationException(myMessage);
        }
        
        return myNextState;
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public <Request extends IRequest> void
    setNext(Class<Request> aRequestClass, State aState)
    throws IllegalArgumentException
    {
        Validate.notNull(aRequestClass, "'aRequestClass' must not be null");
        Validate.notNull(aState, "'aState' must not be null");
        
        theNextStateMap.put(aRequestClass, aState);
                        
        theLogger.debug(MessageFormat.format(LOG_SET_NEXT_PATTERN, 
            aRequestClass.getSimpleName(), aState));
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public <Request extends IRequest> void
    next(Context aContext, Class<Request> aRequestClass) 
    throws IllegalArgumentException, UnsupportedOperationException
    {
        Validate.notNull(aContext, "'aContext' must not be null");
        
        State myNextState = getNext(aRequestClass);
        
        aContext.setCurrent(myNextState);
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public String 
    toString()
    {
        return getClass().getSimpleName();
    }   
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Map<Class<? extends IRequest>, State> theNextStateMap = 
        new HashMap<Class<? extends IRequest>, State>();
    
    /**
     * Provides logging facilities for this class 
     */
    private final Logger theLogger = LoggerFactory.getLogger(getClass());
    
    /**
     * MessageFormat patterns
     */
    private static final String EXCEPTION_PATTERN = "Next State is not known " +
        "for State ''{0}'' and Request ''{1}''";
    private static final String LOG_SET_NEXT_PATTERN = "Next State for " +
        "Request ''{0}'' is ''{1}''";
}

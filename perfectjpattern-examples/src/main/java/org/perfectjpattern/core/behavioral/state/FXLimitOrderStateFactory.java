//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// FXLimitOrderStateFactory.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.creational.singleton.*;
import org.perfectjpattern.core.behavioral.state.api.*;
import org.perfectjpattern.core.behavioral.state.request.*;
import org.perfectjpattern.core.behavioral.state.state.*;

/**
 * Concrete implementation of {@link AbstractStateFactory}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 19, 2009 11:54:27 AM $
 */
public final
class FXLimitOrderStateFactory
extends AbstractStateFactory<IOrderState>
implements ISingleton
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns Singleton instance of {@link FXLimitOrderStateFactory}
     * 
     * @return Singleton instance of {@link FXLimitOrderStateFactory}
     */
    public static FXLimitOrderStateFactory
    getInstance()
    {
        return INSTANCE;
    }        
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public IOrderState
    getInitial()
    {
        return getCreated();
    }

    //------------------------------------------------------------------------
    /**
     * Returns the canceledState
     *
     * @return the canceledState
     */
    public final IOrderState 
    getCanceled()
    {
        return theCanceledState;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the createdState
     *
     * @return the createdState
     */
    public final IOrderState 
    getCreated()
    {
        return theCreatedState;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the filledState
     *
     * @return the filledState
     */
    public final IOrderState 
    getFilled()
    {
        return theFilledState;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the expiredState
     *
     * @return the expiredState
     */
    public final IOrderState 
    getExpired()
    {
        return theExpiredState;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the pendingState
     *
     * @return the pendingState
     */
    public final IOrderState 
    getPending()
    {
        return thePendingState;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the purgedState
     *
     * @return the purgedState
     */
    public final IOrderState 
    getPurged()
    {
        return thePurgedState;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the rejectedState
     *
     * @return the rejectedState
     */
    public final IOrderState 
    getRejected()
    {
        return theRejectedState;
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    protected void 
    buildMachine()
    {
        // build machine setting up the initial Context-free transitions
        IOrderState myCanceledState = getCanceled();
        getPending().setNext(ICancelRequest.class, myCanceledState);
        
        IOrderState myPurgedState = getPurged();        
        getRejected().setNext(IPurgeRequest.class, myPurgedState);
        getFilled().setNext(IPurgeRequest.class, myPurgedState);
        getCanceled().setNext(IPurgeRequest.class, myPurgedState);
        getExpired().setNext(IPurgeRequest.class, myPurgedState);
    }    
    
    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    /**
     * Default constructor for {@link FXLimitOrderStateFactory}
     */
    private
    FXLimitOrderStateFactory()
    {
        super(IOrderState.class);
        
        // cache the concrete IOrderState instances
        theCanceledState = super.createState(new CanceledState());
        theCreatedState = super.createState(new CreatedState());
        theFilledState = super.createState(new FilledState());
        theExpiredState = super.createState(new ExpiredState());
        thePendingState = super.createState(new PendingState());
        thePurgedState = super.createState(new PurgedState());
        theRejectedState = super.createState(new RejectedState());
        
        // build the machine
        buildMachine();
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final IOrderState theCanceledState;
    private final IOrderState theCreatedState;
    private final IOrderState theFilledState;
    private final IOrderState theExpiredState;
    private final IOrderState thePendingState;
    private final IOrderState thePurgedState;
    private final IOrderState theRejectedState;
    
    /**
     * Singleton instance
     */
    private static final FXLimitOrderStateFactory INSTANCE = 
        new FXLimitOrderStateFactory();    
}

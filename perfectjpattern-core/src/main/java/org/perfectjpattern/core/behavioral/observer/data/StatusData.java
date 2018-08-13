//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// StatusData.java Copyright (c) 2012 Giovanni Azua Garcia
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
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
// MA 02110-1301, USA.
//
//----------------------------------------------------------------------
package org.perfectjpattern.core.behavioral.observer.data;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.observer.data.*;

/**
 * Immutable Observer event data intended to be used in notifying status
 * information.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 17, 2007 2:20:44 PM $
 */
public
class StatusData 
implements IEventData 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Constructor that creates a StatusData instance from a Status and message.
     * 
     * @param aStatus Status of the task
     * @param aMessage Description of the current theStatus
     * @throws IllegalArgumentException "'aStatus' must not be null"
     * @throws IllegalArgumentException "'aMessage' must not be null"
     */
    public
    StatusData(Status aStatus, String aMessage) 
    {
        Validate.notNull(aStatus, "'aStatus' must not be null");
        Validate.notNull(aMessage, "'aMessage' must not be null");
        
        theStatus = aStatus;
        theMessage = aMessage;
    }

    //------------------------------------------------------------------------
    /**
     * Copy constructor.
     * 
     * @param aStatusData Existing StatusData
     * @throws IllegalArgumentException "'aStatus' must not be null"
     * @throws IllegalArgumentException "'aMessage' must not be null"
     */
    public
    StatusData(StatusData aStatusData) 
    {
        this(aStatusData.getStatus(), aStatusData.getMessage());
    }        
    
    //------------------------------------------------------------------------
    /**
     * Returns message associated with this StatusData. Offers a user-friendly
     * description of the status.
     * 
     * @return Message associated with this StatusData. Offers a user-friendly
     * description of the status.
     */
    public String 
    getMessage() 
    {
        return theMessage;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the Status value associated with this StatusData from the set of
     * predefined possible values {@link Status}.
     * 
     * @return Status value associated with this StatusData from the set of
     * predefined possible values {@link Status}.
     */
    public Status 
    getStatus() 
    {
        return theStatus;
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean 
    equals(Object anObject)
    {
        if (this == anObject)
        {
            return true;
        }
        
        if (anObject == null)
        {
            return false;
        }
        
        if (getClass() != anObject.getClass())
        {
            return false;
        }
        
        final StatusData myAnother = (StatusData) anObject;
        if (!theMessage.equals(myAnother.theMessage))
        {
            return false;
        }
        
        if (!theStatus.equals(myAnother.theStatus))
        {
            return false;
        }
        
        return true;
    }

    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public int 
    hashCode()
    {
        final int myPrime = 31;
        int myResult = 1;
        myResult = myPrime * myResult + ((theMessage == null) ? 0 : 
            theMessage.hashCode());
        myResult = myPrime * myResult + ((theStatus == null) ? 0 : 
            theStatus.hashCode());
        
        return myResult;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public String 
    toString() 
    {
        return "StatusData(Status='" + getStatus().toString() + 
            "', Message='" + getMessage() + "')";
    }
        
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Status information.
     */
    private final Status theStatus;

    /**
     * Descriptive information on the task status
     */
    private final String theMessage;
    
    /**
     * Reusable <code>StatusData</code> instance corresponding to a 
     * <b>Started</b> event.
     */
    public static final StatusData STARTED = new StatusData(Status.STARTED, 
        "Task has started.");

    /**
     * Reusable <code>StatusData</code> instance corresponding to a 
     * <b>Failed</b> event.
     */
    public static final StatusData FAILED = new StatusData(Status.FAILED, 
        "Task has failed");

    /**
     * Reusable <code>StatusData</code> instance corresponding to a 
     * <b>Completed</b> state.
     */
    public static final StatusData COMPLETED = new StatusData(Status.COMPLETED, 
        "Task has completed.");    
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ProgressData.java Copyright (c) 2012 Giovanni Azua Garcia
// bravegag@hotmail.com
//  
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 3
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, see <http://www.gnu.org/licenses/>.
//
//----------------------------------------------------------------------
package org.perfectjpattern.core.behavioral.observer.data;

/**
 * Immutable Observer event data intended to be used in notifying progress 
 * information.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 17, 2007 3:00:29 PM $
 */
public 
class ProgressData
extends StatusData
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Creates a progress information with precalculated task percentage
     * completed.
     * 
     * @param aStatus Status of the task
     * @param aMessage Description of the current status
     * @param aProgress Percentage of completion of the task [0 .. 100]
     * @throws IllegalArgumentException "'aStatus' must not be null"
     * @throws IllegalArgumentException "'aMessage' must not be null"
     */
    public 
    ProgressData(Status aStatus, String aMessage, int aProgress) 
    {
        super(aStatus, aMessage);
        
        theProgress = aProgress;
    }
    
    //------------------------------------------------------------------------
    /**
     * Creates a progress information with total and current progress values,
     * this variation of the constructor calculates the percentage based on the
     * two values.
     * 
     * @param aStatus Status of the task
     * @param aMessage Description of the current status
     * @param aTotal Numeric total of the task
     * @param aCurrent Current value of the task in reference to the total
     */
    public 
    ProgressData(Status aStatus, String aMessage, int aTotal, int aCurrent) 
    {
        this(aStatus, aMessage, Math.round((float) aCurrent / 
            (float) aTotal * 100.0f));
    }
    
    //------------------------------------------------------------------------
    /**
     * Copy constructor.
     * 
     * @param aProgressData Existing ProgressData
     */
    public
    ProgressData(ProgressData aProgressData) 
    {
        this(aProgressData.getStatus(), aProgressData.getMessage(), 
            aProgressData.getProgress());
    }    

    //------------------------------------------------------------------------
    /**
     * Returns the progress information as an Integer with value between 0 
     * and 100.
     * 
     * @return Progress information as an Integer with value between 0 
     * and 100.
     */
    public int 
    getProgress() 
    {
        return theProgress;
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
        
        if (!super.equals(anObject))
        {
            return false;
        }
        
        if (getClass() != anObject.getClass())
        {
            return false;            
        }
        
        final ProgressData myAnother = (ProgressData) anObject;
        if (theProgress != myAnother.theProgress)
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
        int myResult = super.hashCode();
        
        myResult = myPrime * myResult + theProgress;
        
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
        return "ProgressData(" + super.toString() + ", Progress='" + 
            getProgress() + "%')";
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
      * Progress completed in percent.
      */
    private final int theProgress;
    
    /**
     * Reusable <code>ProgressData</code> instance corresponding to a 
     * <b>Started</b> event.
     */
    public static final ProgressData STARTED = 
       new ProgressData(Status.STARTED, "Task has started.", 0);

    /**
     * Reusable <code>ProgressData</code> instance corresponding to a 
     * <b>Failed</b> event.
     */
    public static final ProgressData FAILED = 
       new ProgressData(Status.FAILED, "Task has failed", 0);

    /**
     * Reusable <code>ProgressData</code> instance corresponding to a 
     * <b>Completed</b> event.
     */
    public static final ProgressData COMPLETED = 
       new ProgressData(Status.COMPLETED, "Task has completed.", 100);
}

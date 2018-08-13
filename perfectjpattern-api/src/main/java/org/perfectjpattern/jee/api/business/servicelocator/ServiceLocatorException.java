//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ServiceLocatorException.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.business.servicelocator;

/**
 * Service Locator exception
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 6, 2009 9:49:45 PM $
 */
public final 
class ServiceLocatorException
extends RuntimeException
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * Constructs a new ServiceLocatorException with <code>null</code> as its
     * detail message. 
     */
    public 
    ServiceLocatorException()
    {
        super();
    }

    //------------------------------------------------------------------------
    /**
     * Constructs a new ServiceLocatorException with the specified detail 
     * message and cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param  aMessage the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  aCause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public 
    ServiceLocatorException(String aMessage, Throwable aCause)
    {
        super(aMessage, aCause);
    }

    //------------------------------------------------------------------------
    /** 
     * Constructs a new ServiceLocatorException with the specified detail 
     * message.
     * 
     * @param  aMessage the detail message. The detail message is saved for 
     *         later retrieval by the {@link #getMessage()} method.
     */
    public 
    ServiceLocatorException(String aMessage)
    {
        super(aMessage);
    }

    //------------------------------------------------------------------------
    /** 
     * Constructs a new ServiceLocatorException with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param  aCause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public 
    ServiceLocatorException(Throwable aCause)
    {
        super(aCause);
    }                
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Default serial version ID 
     */
    private static final long serialVersionUID = 1L;
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// DaoException.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.integration.dao;

/**
 * Data Access Object (DAO) Exception
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 26, 2007 8:46:05 PM $
 */
public 
class DaoException
extends RuntimeException
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * Constructs a new DaoException with <code>null</code> as its
     * detail message. 
     */
    public 
    DaoException()
    {
        super();
    }

    //------------------------------------------------------------------------
    /**
     * Constructs a new DaoException with the specified detail message and
     * cause.  <p>Note that the detail message associated with
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
    DaoException(String aMessage, Throwable aCause)
    {
        super(aMessage, aCause);
    }

    //------------------------------------------------------------------------
    /** 
     * Constructs a new DaoException with the specified detail message.
     * 
     * @param  aMessage the detail message. The detail message is saved for 
     *         later retrieval by the {@link #getMessage()} method.
     */
    public 
    DaoException(String aMessage)
    {
        super(aMessage);
    }

    //------------------------------------------------------------------------
    /** 
     * Constructs a new DaoException with the specified cause and a
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
    DaoException(Throwable aCause)
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

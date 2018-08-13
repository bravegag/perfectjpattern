//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractTestRuntimeException.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.support.test;

import junit.framework.*;

/**
 * Abstract base reusable {@link TestCase} for testing user-defined subtypes of
 * {@link RuntimeException}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 12, 2009 10:06:38 AM $
 */
public abstract 
class AbstractTestRuntimeException<E extends RuntimeException>
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    AbstractTestRuntimeException(Class<E> aClass)
    {
        assert aClass != null : "'aClass' must not be null";
        
        theClass = aClass;
    }
    
    //------------------------------------------------------------------------
    public void
    testException()
    throws Exception
    {   
        String myClassName = theClass.getSimpleName();
        
        String myCause2 = "Database corrupt";
        Throwable myCause3 = new RuntimeException("Disconnected");        
        String myCause4a = "Illegal Statement";        
        Throwable myCause4b = new RuntimeException(myCause4a);        
        
        RuntimeException myException1 = theClass.newInstance();
        RuntimeException myException2 = theClass.getDeclaredConstructor(String.
            class).newInstance(myCause2);
        RuntimeException myException3 = theClass.getDeclaredConstructor(
            Throwable.class).newInstance(myCause3);
        RuntimeException myException4 = theClass.getDeclaredConstructor(
            String.class, Throwable.class).newInstance(myCause4a, myCause4b);
        
        try
        {
            throw myException1;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException)
        // CHECKSTYLE:ON
        {
            assertNull(myClassName + " did not work as expected",  anException.
                getMessage());
        }

        try
        {
            throw myException2;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException)
        // CHECKSTYLE:ON
        {
            assertEquals(myClassName + " did not work as expected", myCause2, 
                anException.getMessage());
        }

        try
        {
            throw myException3;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException)
        // CHECKSTYLE:ON
        {
            assertEquals(myClassName + " did not work as expected", myCause3, 
                anException.getCause());
        }

        try
        {
            throw myException4;
        }
        // CHECKSTYLE:OFF
        catch (RuntimeException anException)
        // CHECKSTYLE:ON
        {
            assertEquals(myClassName + " did not work as expected", myCause4b, 
                anException.getCause());
        }        
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Class<E> theClass;
}

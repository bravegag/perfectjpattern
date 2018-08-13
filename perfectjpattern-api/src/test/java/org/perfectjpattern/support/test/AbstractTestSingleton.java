//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractTestSingleton.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.lang.reflect.*;

import junit.framework.*;

import org.perfectjpattern.core.api.creational.singleton.*;

/**
 * Fully automated Test Suite for testing instances of {@link ISingleton}
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Mar 1, 2009 12:51:25 PM $
 */
public abstract
class AbstractTestSingleton<S>
extends TestCase
{
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    public 
    AbstractTestSingleton(Class<S> aSingletonClass, S anInstance)
    {
        assert aSingletonClass != null : "'aSingletonClass' must not be null";
        assert anInstance != null : "'anInstance' must not be null";
        
        theSingletonClass = aSingletonClass;
        theInstance = anInstance;
    }
    
    //------------------------------------------------------------------------
    /**
     * Check that the singleton has only private constructors 
     */
    public void
    testPrivateContructor()
    throws Exception
    {
        Constructor<?>[] myConstructors = theSingletonClass.getConstructors();
        for (Constructor<?> myConstructor : myConstructors)
        {
            int myResult = myConstructor.getModifiers() | Modifier.PRIVATE;
            
            assertTrue("All contructors must be private", myResult != 0);
        }
    }
    
    //------------------------------------------------------------------------
    /**
     * Check that the singleton access method is called getInstance
     */
    public void
    testAccessorConvention()
    throws Exception
    {
        Method myAccessorMethod = theSingletonClass.getMethod("getInstance");
        
        assertNotNull("Singletons must define the accessor to be getInstance",
            myAccessorMethod);
    }

    //------------------------------------------------------------------------
    /**
     * Check that the singleton always return the same instance
     */
    @SuppressWarnings("unchecked")
    public void
    testSingleInstance()
    throws Exception
    {
        Method myAccessorMethod = theSingletonClass.getMethod("getInstance");

        // call it several times and check it is the same instance        
        S myInstance = (S) myAccessorMethod.invoke(theInstance);
        for (int i = 0; i < 10; i++)
        {
            assertSame("getInstance must always return the same instance", 
                theInstance, myInstance);
        }
    }

    //------------------------------------------------------------------------
    /**
     * Check that the singleton implements the ISingleton marker interface
     */
    public void
    testImplementsMarker()
    throws Exception
    {
        assertTrue("Does not implement ISingleton marker", ISingleton.class.
            isAssignableFrom(theSingletonClass));
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Class<S> theSingletonClass;
    private final S theInstance;
}

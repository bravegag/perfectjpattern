//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestNullObject.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.structural.nullobject;

import java.util.*;

import org.junit.*;
import org.perfectjpattern.core.api.behavioral.observer.*;

/**
 * Test suite corresponding to the {@link NullObject} implementation.
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Dec 26, 2012 5:23:22 PM $
 */
public class TestNullObject
{
    // ------------------------------------------------------------------------
    // public
    // ------------------------------------------------------------------------
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void
    testCollections()
    {
        // test List
        List<String> myList = new NullObject<List>(List.class).getComponent();
        Assert.assertNotNull("component List must not be null", myList);
        Assert.assertEquals("List get size must be zero", 0, myList.size());
        Assert.assertEquals("List get(i) must be null, since String is not an interface type",
            null, myList.get(0));

        // test Map
        Map<String, String> myMap = new NullObject<Map>(Map.class).getComponent();
        Assert.assertNotNull("component Map must not be null", myMap);
        Assert.assertEquals("Map get size must be zero", 0, myMap.size());
        Assert.assertEquals("Map get(\"abc\") must be null, since String is not an interface type",
            null, myMap.get("abc"));

        // test Set
        Set<String> mySet = new NullObject<Set>(Set.class).getComponent();
        Assert.assertNotNull("component Set must not be null", mySet);
        Assert.assertEquals("Set get size must be zero", 0, mySet.size());
        Assert.assertFalse("Set contains(\"abc\") must be false, since boolean is a primitive type",
            mySet.contains("abc"));
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void
    testUserDefinedType()
    {
        // test IObserver
        IObserver<String> myObserver = new NullObject<IObserver>(IObserver.class).getComponent();
        Assert.assertNotNull("component IObserver must not be null", myObserver);
        Assert.assertEquals("IObserver toString size must be empty", "", myObserver.toString());
        
    }
}

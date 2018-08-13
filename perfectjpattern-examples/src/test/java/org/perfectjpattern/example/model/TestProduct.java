//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestProduct.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.example.model;

import junit.framework.*;

/**
 * Test suite for the {@link Product} implementation
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Jan 11, 2009 12:55:57 PM $
 */
public 
class TestProduct
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public void
    testEquals()
    {
        Product myProduct1 = new Product("Nikon 80-400mm VR f/4.5-5.6", 2000.0);
        Product myProduct2 = new Product("Nikon 80-400mm VR f/4.5-5.6", 2000.0);
        Product myProduct3 = new Product("Nikon 17-55mm VR f/2.8", 1600.0);
        
        assertEquals("Product equals does not work as expected", myProduct1, 
            myProduct1);
        assertEquals("Product equals does not work as expected", myProduct1.
            hashCode(), myProduct1.hashCode());
        
        assertFalse("Product equals does not work as expected", myProduct1.
            equals(null));

        assertFalse("Product equals does not work as expected", myProduct1.
            equals(new Customer()));
        
        assertEquals("Product equals does not work as expected", myProduct1, 
            myProduct2);
        assertEquals("Product equals does not work as expected", myProduct1.
            hashCode(), myProduct2.hashCode());        
        
        assertFalse("Product equals does not work as expected", myProduct2.
            equals(myProduct3));
        assertFalse("Product equals does not work as expected", myProduct2.
            hashCode() == myProduct3.hashCode());
    }
}

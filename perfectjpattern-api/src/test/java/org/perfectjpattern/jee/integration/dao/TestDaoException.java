//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestDaoException.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.integration.dao;

import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.support.test.*;

/**
 * Test suite for the {@link DaoException} implementation 
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Jan 11, 2009 1:02:08 PM $
 */
public 
class TestDaoException
extends AbstractTestRuntimeException<DaoException>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    TestDaoException()
    {
        super(DaoException.class);
    }
}

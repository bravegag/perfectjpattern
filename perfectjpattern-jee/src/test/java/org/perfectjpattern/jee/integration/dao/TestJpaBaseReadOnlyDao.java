//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// TestJpaBaseReadOnlyDao.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.core.api.structural.composite.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.support.test.*;

/**
 * Test Suite for the {@link JpaBaseReadOnlyDao} implementation
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 26, 2007 9:29:34 PM $
 */
public 
class TestJpaBaseReadOnlyDao
extends AbstractTestBaseReadOnlyDao
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Default {@link TestJpaBaseReadOnlyDao} constructor
     */
    public 
    TestJpaBaseReadOnlyDao()
    {
        super(JpaDaoFactory.getInstance());
    }

    //------------------------------------------------------------------------
    public void
    testFindAll()
    {
        super.testFindAll(new Order("name"), new Order("age", 
            AscDescEnum.DESC));
    }
    
    //------------------------------------------------------------------------
    public void
    testFindByPage()
    {
        // create Composite Order
        IComposite<IOrder> myComposite = new CompositeOrder();
        myComposite.addAll(new Order("name"), new Order("age"));

        super.testFindByPage(myComposite.getComponent(), new Order("name"));
    }
}

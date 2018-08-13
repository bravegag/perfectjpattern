//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// NullRequest.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.chainofresponsibility;

import org.perfectjpattern.core.api.behavioral.chainofresponsibility.*;
import org.perfectjpattern.support.test.*;

/**
 * Test Suite for {@link NullRequest} implementation.
 * 
 * @see NullRequest
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 26, 2007 1:01:56 AM $
 */
public 
class TestNullRequest 
extends AbstractTestSingleton<NullRequest> 
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    TestNullRequest()
    {
        super(NullRequest.class, NullRequest.getInstance());
    }
}

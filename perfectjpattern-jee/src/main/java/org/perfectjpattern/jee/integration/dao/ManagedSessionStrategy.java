//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// ManagedDaoSessionStrategy.java Copyright (c) 2012 Giovanni Azua Garcia
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

import javax.persistence.*;

import org.perfectjpattern.core.api.structural.adapter.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Concrete implementation of {@link ISessionStrategy} targeting 
 * Container Managed Persistence (CMP) environment
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 8, 2009 5:24:51 PM $
 */
public final
class ManagedSessionStrategy
implements ISessionStrategy
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public void 
    shutdown()
    {
        throw new UnsupportedOperationException("Managed Session strategy " +
            "must not be shutdown");
    }

    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public ISession
    getSession()
    {
        return theSessionAdapter.getTarget();
    }
    
    //------------------------------------------------------------------------
    public void
    setEntityManager(EntityManager anEntityManager)
    {
        if (theSessionAdapter == null || theSessionAdapter.
            getAdaptee() != anEntityManager)
        {
            theSessionAdapter = new EntityManagerAdapter(anEntityManager);
        }
    }
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private IAdapter<ISession, EntityManager> theSessionAdapter;
}

//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// SongBaseDaoBean.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.business.valuelisthandler;

import javax.ejb.*;
import javax.persistence.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.integration.dao.*;

/**
 * Stateless Session EJB that exposes {@link IBaseDao}. 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 20, 2009 9:53:24 PM $
 */
@Remote(IBaseDao.class)
@Stateless(name = "SongBaseDao")
public 
class SongBaseDaoBean
extends AbstractJpaManagedBaseDao<Long, Song>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    SongBaseDaoBean()
    {
        super(Song.class);
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @PersistenceContext(unitName = "song", type = PersistenceContextType.
        TRANSACTION)
    @Override
    public void
    setEntityManager(EntityManager anEntityManager)
    {
        super.setEntityManager(anEntityManager);
    }
}

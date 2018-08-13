//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// MovieGenericDaoBean.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.integration.dao.jpa;

import java.util.*;

import javax.ejb.*;
import javax.persistence.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.integration.dao.*;

/**
 * Stateless Session EJB that exposes {@link IGenericDao}. 
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 11, 2009 11:31:24 AM $
 */
@Remote(IMovieGenericDao.class)
@Stateless(name = "MovieGenericDao")
public 
class MovieGenericDaoBean
extends AbstractHibernateManagedGenericDao<Long, Movie>
implements IMovieGenericDao
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    MovieGenericDaoBean()
    {
        super(Movie.class);
    }
    
    //------------------------------------------------------------------------
    @PersistenceContext(unitName = "movie", type = PersistenceContextType.
        TRANSACTION)
    @Override
    public void
    setEntityManager(EntityManager anEntityManager)
    {
        super.setEntityManager(anEntityManager);
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    public List<Movie> 
    findByYear(int aYear)
    {
        Movie myExample = new Movie();
        myExample.setYear(aYear);
        
        List<Movie> myMovies = super.findByExample(myExample);
        
        return myMovies;
    }
}

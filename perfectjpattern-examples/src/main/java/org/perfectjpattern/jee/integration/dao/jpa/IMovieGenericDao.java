//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// IMovieGenericDao.java Copyright (c) 2012 Giovanni Azua Garcia
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

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.jee.api.integration.dao.*;

/**
 * Abstract Movie {@link IGenericDao} definition
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 11, 2009 11:30:01 AM $
 */
public 
interface IMovieGenericDao
extends IGenericDao<Long, Movie>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Returns matching {@link Movie} instances found. Searches for movies
     * by year. Example how to implement custom finder methods on top of
     * {@link IGenericReadOnlyDao#findByExample(Object, String...)}
     * 
     * @param aYear Year to search for
     * @return matching {@link Movie} instances found
     */
    public List<Movie>
    findByYear(int aYear);
}

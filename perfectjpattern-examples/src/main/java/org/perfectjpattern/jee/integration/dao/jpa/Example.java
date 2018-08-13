//----------------------------------------------------------------------
// PerfectJPattern: "Design patterns are good but components are better!" 
// Example.java Copyright (c) 2012 Giovanni Azua Garcia
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
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
// MA 02110-1301, USA.
//
//----------------------------------------------------------------------
package org.perfectjpattern.jee.integration.dao.jpa;

import java.util.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.example.model.visitor.*;
import org.perfectjpattern.jee.api.business.servicelocator.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.business.servicelocator.*;
import org.slf4j.*;

/**
 * Startup Main for the JPA Generic Dao example code. Example that demonstrates 
 * a remote client of a possible Data Service Layer implemented on top of 
 * PerfectJPattern's DAO.
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 5, 2008 2:04:37 AM $
 */
// CHECKSTYLE:OFF
public final
class Example
// CHECKSTYLE:ON
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public static void 
    main(String[] anArguments)
    {
        // tweaking OpenEJB logging
        System.setProperty("log4j.category.OpenEJB", "debug"); 
        
        //---------------------------------------------------------------
        // use ServiceLocator to load the Remote IBaseDao EJB
        //---------------------------------------------------------------
        IServiceLocator myServiceLocator = ServiceLocator.getInstance();
        IBaseDao<Long, Movie> myMovieBaseDao = myServiceLocator.locate(
            "MovieBaseDaoRemote");
        
        //---------------------------------------------------------------
        // call IBaseDao methods
        //---------------------------------------------------------------
        myMovieBaseDao.create(new Movie("Fresa y chocolate", 
            "Tomas Gutierrez Alea", 1994));        
        myMovieBaseDao.create(new Movie("Vita e bella, La", "Roberto Benigni",
            1997));
        myMovieBaseDao.create(new Movie("Taken", "Pierre Morel", 2008));
                
        //---------------------------------------------------------------
        // find all Movies
        //---------------------------------------------------------------
        StringBuilder myBuilder = new StringBuilder();
        List<Movie> myMovies = myMovieBaseDao.findAll();
        ToStringVisitor myVisitor = new ToStringVisitor();
        for (Movie myMovie : myMovies)
        {
            myVisitor.visit(myMovie);         
            String myResult = myVisitor.getResult();            
            myBuilder.append(myResult);
        }        
        
        //---------------------------------------------------------------
        // use ServiceLocator to load the Remote IGenericDao EJB
        //---------------------------------------------------------------
        IMovieGenericDao myMovieGenericDao = myServiceLocator.locate(
            "MovieGenericDaoRemote");

        //---------------------------------------------------------------
        // find all Movies produced in 2008
        //---------------------------------------------------------------
        int myYear = 2008;
        myMovies = myMovieGenericDao.findByYear(myYear);
        for (Movie myMovie : myMovies)
        {
            myVisitor.visit(myMovie);         
            String myResult = myVisitor.getResult();            
            myBuilder.append(myResult);
        }        

        theLogger.debug(myBuilder.toString());
        
        //---------------------------------------------------------------
        // right thing to do here would be to shutdown the Service Locator
        // but we can't because will interfere with other examples
        //---------------------------------------------------------------
        myServiceLocator.shutdown();                
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    protected static void
    setLogger(Logger aLogger)
    {
        theLogger = aLogger;
    }    

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    /**
     * Provides logging facilities for this class 
     */
    private static Logger theLogger = LoggerFactory.getLogger(Example.class);
}
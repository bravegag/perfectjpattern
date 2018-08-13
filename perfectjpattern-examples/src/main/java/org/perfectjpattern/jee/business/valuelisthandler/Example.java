//----------------------------------------------------------------------
// 
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
//
//----------------------------------------------------------------------
package org.perfectjpattern.jee.business.valuelisthandler;

import java.util.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.example.model.visitor.*;
import org.perfectjpattern.jee.api.business.servicelocator.*;
import org.perfectjpattern.jee.api.business.valuelisthandler.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.business.servicelocator.*;
import org.perfectjpattern.jee.integration.dao.Order;
import org.slf4j.*;

/**
 * Startup Main for the Value List Handler example code
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 20, 2009 9:46:00 PM $
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
        IBaseDao<Long, Song> mySongBaseDao = myServiceLocator.locate(
            "SongBaseDaoRemote");
        
        //---------------------------------------------------------------
        // populate the Song database "Buena Vista Social Club"
        //---------------------------------------------------------------
        mySongBaseDao.create(new Song("Chan Chan"));
        mySongBaseDao.create(new Song("De Camino a La Vereda"));
        mySongBaseDao.create(new Song("El Cuarto de Tula"));
        mySongBaseDao.create(new Song("Pueblo Nuevo"));
        mySongBaseDao.create(new Song("Dos Gardenias"));
        mySongBaseDao.create(new Song("Y Tu Que Has Hecho ?"));
        mySongBaseDao.create(new Song("Veinte Anos"));
        mySongBaseDao.create(new Song("El Carretero"));
        mySongBaseDao.create(new Song("Candela"));
        mySongBaseDao.create(new Song("Amor de Loca Juventud"));
        mySongBaseDao.create(new Song("Orgullecida"));
        mySongBaseDao.create(new Song("Murmullo"));
        mySongBaseDao.create(new Song("Buena Vista Social Club"));
        mySongBaseDao.create(new Song("La Bayamesa"));
                
        //---------------------------------------------------------------
        // use ServiceLocator to load the Remote IBaseValueListHandler EJB
        //---------------------------------------------------------------
        IBaseValueListHandler<Song> mySongListHandler = 
            myServiceLocator.locate("SongBaseListHandlerRemote");
        
        //---------------------------------------------------------------
        // execute Query All
        //---------------------------------------------------------------
        mySongListHandler.executeQueryAll(new Order("name"));
        
        //---------------------------------------------------------------
        // use the Iterator interface
        //---------------------------------------------------------------
        Iterator<Song> myIterator = mySongListHandler;
        StringBuilder myBuilder = new StringBuilder();        
        ToStringVisitor myVisitor = new ToStringVisitor();        
        while (myIterator.hasNext())
        {
            myVisitor.visit(myIterator.next());
            
            myBuilder.append(myVisitor.getResult());
        }
        
        //---------------------------------------------------------------
        // reset the Iterator index
        //---------------------------------------------------------------
        mySongListHandler.reset();
        
        //---------------------------------------------------------------
        // use the multiple-step iterator
        //---------------------------------------------------------------
        int myNumberOfSongs = 7; 
        while (mySongListHandler.hasNext())
        {
            List<Song> mySongs = mySongListHandler.next(myNumberOfSongs);
            for (Song mySong : mySongs)
            {
                myVisitor.visit(mySong);
                myBuilder.append(myVisitor.getResult());                
            }
        }
        
        //---------------------------------------------------------------
        // get Page-By-Page
        //---------------------------------------------------------------
        int myPageSize = 5;
        int myLastPage = mySongListHandler.size() / myPageSize;
        if (mySongListHandler.size() % myPageSize > 0)
        {
            myLastPage++;
        }
        
        for (int myPageIndex = 1; myPageIndex <= myLastPage; myPageIndex++)
        {
            List<Song> myPage = mySongListHandler.getPage(myPageSize, 
                myPageIndex);
            for (Song mySong : myPage)
            {
                myVisitor.visit(mySong);
                myBuilder.append(myVisitor.getResult());
            }
        }
        
        // dispose the Stateful EJB instance
        mySongListHandler.dispose();
        
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

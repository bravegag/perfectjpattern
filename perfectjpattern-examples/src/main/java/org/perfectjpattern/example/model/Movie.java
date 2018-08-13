//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Movie.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.io.*;

import javax.persistence.*;

/**
 * Movie Model object
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 11, 2009 11:51:40 AM $
 */
@Entity
public 
class Movie
implements Serializable
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    Movie()
    {
        // empty
    }

    //------------------------------------------------------------------------
    public 
    Movie(String aDirector, String aTitle, int aYear)
    {
        theDirector = aDirector;
        theTitle = aTitle;
        theYear = aYear;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the id
     *
     * @return the id
     */
    public final Long 
    getId()
    {
        return theId;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the id
     * 
     * @param anId the id to set
     */
    public final void 
    setId(Long anId)
    {
        theId = anId;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the director
     *
     * @return the director
     */
    public final String 
    getDirector()
    {
        return theDirector;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the director
     * 
     * @param anDirector the director to set
     */
    public final void 
    setDirector(String anDirector)
    {
        theDirector = anDirector;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the title
     *
     * @return the title
     */
    public final String 
    getTitle()
    {
        return theTitle;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the title
     * 
     * @param anTitle the title to set
     */
    public final void 
    setTitle(String anTitle)
    {
        theTitle = anTitle;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the year
     *
     * @return the year
     */
    public final int 
    getYear()
    {
        return theYear;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the year
     * 
     * @param anYear the year to set
     */
    public final void 
    setYear(int anYear)
    {
        theYear = anYear;
    }    
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long theId;
    private String theDirector;
    private String theTitle;
    private int theYear;

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = -6522105563589093507L;
}

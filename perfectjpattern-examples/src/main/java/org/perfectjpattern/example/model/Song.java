//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Song.java Copyright (c) 2012 Giovanni Azua Garcia
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

/**
 * Song model Object
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Apr 20, 2009 9:33:40 PM $
 */
public
class Song
implements Serializable
{
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    public 
    Song()
    {
        // empty
    }

    //------------------------------------------------------------------------
    public 
    Song(String anName)
    {
        theName = anName;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the id
     *
     * @return the id
     */
    public long 
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
    public void 
    setId(long anId)
    {
        theId = anId;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the name
     *
     * @return the name
     */
    public String 
    getName()
    {
        return theName;
    }

    //------------------------------------------------------------------------
    /**
     * Sets the name
     * 
     * @param anName the name to set
     */
    public void 
    setName(String anName)
    {
        theName = anName;
    }        
    
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private long theId;
    private String theName;
    
    /**
     * Serial version ID 
     */
    private static final long serialVersionUID = -6640635376261367526L;
}

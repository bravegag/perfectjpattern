//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Person.java Copyright (c) 2012 Giovanni Azua Garcia
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
 * Person Data Model type
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Nov 30, 2007 6:30:38 PM $
 */
public 
class Person
implements Serializable
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public 
    Person()
    {
        // do nothing
    }

    //------------------------------------------------------------------------
    public 
    Person(String aName, int anAge)
    {
        theName = aName;
        theAge = anAge;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the id
     *
     * @return the id
     */
    public Long 
    getId()
    {
        return theId;
    }
    
    //------------------------------------------------------------------------
    /**
     * Sets the id of the person
     * 
     * @param anId the id to set
     */
    public void 
    setId(Long anId)
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
     * Sets the name of the person
     *
     * @param anName the name to set
     */
    public void 
    setName(String anName)
    {
        theName = anName;
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns the age
     *
     * @return the age
     */
    public int 
    getAge()
    {
        return theAge;
    }
    
    //------------------------------------------------------------------------
    /**
     * Sets the age of the person
     * 
     * @param anAge the age to set
     */
    public void 
    setAge(int anAge)
    {
        theAge = anAge;
    }    
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public int 
    hashCode()
    {
        final int myPrime = 31;
        int myResult = 1;
        myResult = myPrime * myResult + getAge();
        myResult = myPrime * myResult + ((getName() == null) ? 0 : getName().hashCode());
        
        return myResult;
    }
    
    //------------------------------------------------------------------------
    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean 
    equals(Object anObject)
    {
        if (this == anObject)
        {
            return true;
        }
        
        if (anObject == null)
        {
            return false;
        }
        
        if (getClass() != anObject.getClass())
        {
            return false;
        }
        
        final Person myAnother = (Person) anObject;
        
        if (getAge() != myAnother.getAge())
        {
            return false;
        }
        
        if (getName() == null)
        {
            if (myAnother.getName() != null)
            {
                return false;
            }
        }
        else 
        if (!getName().equals(myAnother.getName()))
        {
            return false;
        }
        
        return true;
    }    

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private Long theId;
    private String theName;
    private int theAge;

    /**
     * Serial version ID 
     */
    private static final long serialVersionUID = -593505897195511L;
}

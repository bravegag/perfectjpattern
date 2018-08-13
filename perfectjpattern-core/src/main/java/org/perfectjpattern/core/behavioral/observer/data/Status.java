//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Status.java Copyright (c) 2012 Giovanni Azua Garcia
// bravegag@hotmail.com
//  
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 3
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, see <http://www.gnu.org/licenses/>.
//
//----------------------------------------------------------------------
package org.perfectjpattern.core.behavioral.observer.data;

/**
 * Defines all possible valid Status
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 17, 2007 9:48:45 AM $
 */
public 
enum Status 
{
    /**
     * Not started Status.
     */
    NOT_STARTED 
    {
        //---------------------------------------------------------
        @Override
        public String 
        toString() 
        {
            return "Not started";
        }
    }, 
    /**
     * Started Status.
     */
    STARTED
    {
        //---------------------------------------------------------
        @Override
        public String 
        toString() 
        {
            return "Started";
        }
    }, 
    /**
     * Suspended Status.
     */
    SUSPENDED
    {
        //---------------------------------------------------------
        @Override
        public String 
        toString() 
        {
            return "Suspended";
        }
    }, 
    /**
     * In Progress Status.
     */
    IN_PROGRESS
    {
        //---------------------------------------------------------
        @Override
        public String 
        toString() 
        {
            return "In Progress";
        }
    }, 
    /**
     * Completed Status
     */
    COMPLETED
    {
        //---------------------------------------------------------
        @Override
        public String 
        toString() 
        {
            return "Completed";
        }
    }, 
    /**
     * Failed Status
     */
    FAILED
    {
        //---------------------------------------------------------
        @Override
        public String 
        toString() 
        {
            return "Failed";
        }
    };
}

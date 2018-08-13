//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// Status.java Copyright (c) 2012 Giovanni Azua Garcia
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

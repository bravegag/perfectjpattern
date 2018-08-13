//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// QueryParameters.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.api.integration.dao;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

/**
 * Defines the order and correspondence between interface method arguments and 
 * named query parameters. Named query parameters when retrieved they don't have
 * a predictable order, besides compiled byte code loses the argument names
 * thus without this annotation there would be no predictable way to map the
 * finder method arguments to the query parameters.
 * <br/><br/>
 * For example a named query defined like:
 * <pre><code>
 * &lt;query name="Customer.findByNumberOfOrdersBetween"&gt;
 *   &lt;![CDATA[SELECT c1
 *            FROM Customer c1 
 *            WHERE c1.id IN 
 *                (SELECT c2.id 
 *                 FROM Customer c2 inner join c2.orders o 
 *                 WHERE o.date BETWEEN :Begin AND :End 
 *                 GROUP BY c2.id
 *                 HAVING Count(*) &gt;= :Minimum
 *                )
 *            ]]&gt;
 *   </query>            
 * </code></pre>
 * Would correspond to an interface method definition with 
 * the {@link QueryParameters} in the following way:
 * <pre><code>
 * public interface ICustomerDao
 * extends IGenericDao&lt;Long, Customer&gt;
 * {
 *     &#064;QueryParameters(names = { "Begin", "End", "Minimum" })
 *     public List&lt;Customer&gt; 
 *     findByNumberOfOrdersBetween(Date aBegin, Date anEnd, long aMinimum);
 * }
 * </pre></code>
 * <br/>
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $Date: Feb 12, 2009 11:08:00 PM $
 */
@Target(METHOD)
@Retention(RUNTIME)
public 
@interface QueryParameters
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    public String[] 
    names();
}

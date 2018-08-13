//----------------------------------------------------------------------
//PerfectJPattern: "Design patterns are good but components are better!" 
//Example.java Copyright (c) 2012 Giovanni Azua Garcia
//bravegag@hotmail.com

//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public License
//as published by the Free Software Foundation; either version 3
//of the License, or (at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Lesser General Public License for more details.

//You should have received a copy of the GNU Lesser General Public License
//along with this program; if not, see <http://www.gnu.org/licenses/>.
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
//MA 02110-1301, USA.

//----------------------------------------------------------------------
package org.perfectjpattern.jee.integration.dao.hibernate;

import java.text.*;
import java.util.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.example.model.Order;
import org.perfectjpattern.example.model.visitor.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.perfectjpattern.jee.integration.dao.*;
import org.slf4j.*;

/**
 * Startup Main for the Hibernate Generic Dao example code
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
    throws ParseException
    {
        // ToStringVisitor needs loading a ResourceBundle
        Locale.setDefault(new Locale("en", "US"));        
                
        //---------------------------------------------------------------
        // Create fixture/ business model
        //---------------------------------------------------------------
        Product myProduct1 = new Product("Nikon D300", 2000.0);
        Product myProduct2 = new Product("Nikon D40x", 350.0);
        Product myProduct3 = new Product("Nikon 80-400mm", 1500.0);
        
        Customer myCustomer1 = new Customer("Ernesto");        
        
        Date myDate = DATE_FORMAT.parse("14.03.2007 08:00:00");
        Set<Product> myProducts = new HashSet<Product>();
        myProducts.addAll(Arrays.asList(new Product[] {myProduct1, 
            myProduct2, myProduct3 }));
        Order myOrder1 = new Order(myCustomer1, myDate, myProducts);
        
        myCustomer1.getOrders().add(myOrder1);
        
        try
        {
            //-----------------------------------------------------------
            // Accesses the AbstractFactory singleton HibernateDaoFactory 
            // and persists Customer model in storage. Note how the 
            // HibernateDaoFactory hides all framework-specific complexities. 
            // By default it gets initialized to use:
            //
            //   - HibernateCurrentSessionStrategy: 
            //      a) Creates SessionFactory from Configuration
            //      b) Uses the getCurrentSession Hibernate API that 
            //         looks into the "current_session_context_class" 
            //         configuration, for this example is set to "thread"
            //
            //   - HibernateConfiguredTransactionStrategy:
            //      Simply accesses the Session Strategy and calls 
            //      getTransaction, that will load whatever is configured 
            //      in "hibernate.transaction_factory" configuration
            //-----------------------------------------------------------
            IGenericDao<Long, Customer> myCustomerDao = HibernateDaoFactory.
                getInstance().createDao(Customer.class);
            myCustomerDao.create(myCustomer1);
            
            //-----------------------------------------------------------
            // Update the Customer model/ add new Product and Order
            //-----------------------------------------------------------
            Product myProduct4 = new Product("Nikon Fisheye 10.5mm", 900.0);

            myDate = DATE_FORMAT.parse("19.10.2007 08:00:00");
            myProducts = new HashSet<Product>();
            myProducts.add(myProduct4);
            Order myNewOrder = new Order(myCustomer1, myDate, myProducts);
            
            myCustomer1.getOrders().add(myNewOrder);
            myCustomerDao.update(myCustomer1);

            //-----------------------------------------------------------
            // Commit changes. Note how accessing whatever Transaction 
            // implementation is transparent to the client
            //-----------------------------------------------------------
            myCustomerDao.getTransaction().commit();

            //-----------------------------------------------------------
            // Delete one Product 
            //-----------------------------------------------------------
            myOrder1.getProducts().remove(myProduct2);

            IGenericDao<Long, Order> myOrderDao = HibernateDaoFactory.
                getInstance().createDao(Order.class);                
            myOrderDao.update(myOrder1);
                        
            //-----------------------------------------------------------
            // Commit changes
            //-----------------------------------------------------------
            myCustomerDao.getTransaction().commit();

            //-----------------------------------------------------------
            // Clear the cache
            //-----------------------------------------------------------
            myCustomerDao.getSession().clear();

            //-----------------------------------------------------------
            // Retrieve all Customers 
            //-----------------------------------------------------------
            List<Customer> myCustomers = myCustomerDao.findAll();
            
            ToStringVisitor myVisitor = new ToStringVisitor();
            for (Customer myCustomer : myCustomers)
            {
                myVisitor.visit(myCustomer);         
                String myResult = myVisitor.getResult();
                
                theLogger.debug(myResult);                
            }
        }
        finally
        {
            //-----------------------------------------------------------
            // Final cleanup
            //-----------------------------------------------------------
            HibernateDaoFactory.getInstance().shutdown();
        }
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
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
        "dd.MM.yyyy HH:mm:ss");    
}
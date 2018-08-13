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
package org.perfectjpattern.jee.integration.dao.spring;

import java.text.*;
import java.util.*;

import org.perfectjpattern.example.model.*;
import org.perfectjpattern.example.model.visitor.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.slf4j.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.transaction.*;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.support.*;

/**
 * Startup Main for the Hibernate-Spring Generic DAO Pattern Example code
 * 
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Dec 5, 2008 2:06:29 AM $
 */
@Transactional
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
        
        ConfigurableApplicationContext applicationContext = null;
        try
        {
            // initialize IoC Spring container
            applicationContext = new ClassPathXmlApplicationContext(
                new String[] 
                {
                    "genericDao-applicationContext.xml",
                    "example-applicationContext.xml"
                });

            PlatformTransactionManager platformTransactionManager = applicationContext.getBean(PlatformTransactionManager.class);            
            TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
            
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                public void doInTransactionWithoutResult(TransactionStatus status) { 
                    try
                    {
                        //---------------------------------------------------------------
                        // Create fixture/ business model
                        //---------------------------------------------------------------
                        
                        // available products
                        Product myProduct1 = new Product("Nikon D700", 3200.0);
                        Product myProduct2 = new Product("Nikon D300", 2000.0);
                        Product myProduct3 = new Product("Nikon D40x", 350.0);
                        Product myProduct4 = new Product("Nikon 80-400mm", 1500.0);
                        Product myProduct5 = new Product("Nikon 70-200mm", 1800.0);        
                        Product myProduct6 = new Product("Nikon 17-55mm", 1200.0);        
                        
                        // new customer
                        Customer myCustomer1 = new Customer("Ernesto");        
                        
                        // add customer orders
                        Date myDate = DATE_FORMAT.parse("14.03.2007 08:00:00");
                        Set<Product> myProducts = new HashSet<Product>();
                        myProducts.addAll(Arrays.asList(new Product[] {myProduct1, 
                            myProduct4, myProduct5 }));
                        Order myOrder1 = new Order(myCustomer1, myDate, myProducts);
                        myCustomer1.getOrders().add(myOrder1);

                        //-----------------------------------------------------------
                        // Accesses the AbstractFactory singleton LocalDaoFactory and 
                        // persists Customer model in storage. Note how the 
                        // LocalDaoFactory hides the framework-specific complexities. 
                        // LocalDaoFactory gets constructed from the Spring 
                        // configuration to:
                        //
                        //   - HibernateCurrentSessionStrategy: 
                        //      a) Receives a new SessionFactory via IoC defined in 
                        //         the Spring configuration
                        //      b) Uses the getCurrentSession Hibernate API that 
                        //         looks into the "current_session_context_class" 
                        //         configuration, for this example is set to "thread"
                        //
                        //   - HibernateConfiguredTransactionStrategy:
                        //      Simply accesses the Session Strategy and calls 
                        //      getTransaction, that will load whatever is configured 
                        //      in "hibernate.transaction_factory" configuration
                        //-----------------------------------------------------------
                        ICustomerDao myCustomerDao = LocalDaoFactory.
                            getInstance().createCustomerDao();
                        myCustomerDao.create(myCustomer1);
                        
                        //-----------------------------------------------------------
                        // Commit changes. Note how accessing whatever Transaction 
                        // implementation is transparent to the client
                        //-----------------------------------------------------------
                        // TODO: review how to achieve the same using Spring 3.x
//                      myCustomerDao.getTransaction().commit();

                        //-----------------------------------------------------------
                        // Extend fixture/ business model
                        //-----------------------------------------------------------
                        Customer myCustomer2 = new Customer("Giovanni");        
                        
                        // add customer orders
                        myDate = DATE_FORMAT.parse("17.11.2007 08:00:00");
                        myProducts = new HashSet<Product>();
                        myProducts.addAll(Arrays.asList(new Product[] {myProduct1, 
                            myProduct2, myProduct3 }));
                        myOrder1 = new Order(myCustomer2, myDate, myProducts);
                        myCustomer2.getOrders().add(myOrder1);
                        
                        myDate = DATE_FORMAT.parse("15.01.2008 17:35:00");
                        myProducts = new HashSet<Product>();
                        myProducts.addAll(Arrays.asList(new Product[] {myProduct4, 
                            myProduct5, myProduct6 }));
                        Order myOrder2 = new Order(myCustomer2, myDate, myProducts);
                        myCustomer2.getOrders().add(myOrder2);
                        
                        //-----------------------------------------------------------
                        // Persist Customer models in storage
                        //-----------------------------------------------------------
                        myCustomerDao.create(myCustomer2);
                        
                        //-----------------------------------------------------------
                        // Commit changes. 
                        //-----------------------------------------------------------
                        // TODO: review how to achieve the same using Spring 3.x
//                      myCustomerDao.getTransaction().commit();

                        //-----------------------------------------------------------
                        // Now we can try our new search capabilities
                        //-----------------------------------------------------------
                        long myMinimum = 2;
                        Date myBegin = DATE_FORMAT.parse("01.01.2007 00:00:00");
                        Date myEnd = DATE_FORMAT.parse("01.01.2009 00:00:00");
                        List<Customer> myCustomers = myCustomerDao.
                            findByNumberOfOrdersBetween(myBegin, myEnd, myMinimum);
                        
                        ToStringVisitor myVisitor = new ToStringVisitor();
                        for (Customer myCustomer : myCustomers)
                        {
                            myVisitor.visit(myCustomer);
                            String myResult = myVisitor.getResult();
                            
                            theLogger.debug(myResult);                
                        }
                    }
                    catch (ParseException exception)
                    {
                        throw new RuntimeException(exception);
                    }
                }                
            });
        }
        finally
        {
            //-----------------------------------------------------------
            // Final cleanup
            //-----------------------------------------------------------
            LocalDaoFactory.getInstance().shutdown();            
            if (applicationContext != null) {
                applicationContext.close();
            }
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
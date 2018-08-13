//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// TestVisitorPattern.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.behavioral.visitor;

import java.util.*;

import junit.framework.*;

import org.perfectjpattern.core.api.behavioral.visitor.*;
import org.slf4j.*;

/**
 * Test Suite for {@link AbstractVisitor} implementation.
 *
 * @see AbstractVisitor
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jul 1, 2007 12:06:36 PM $
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public
class TestVisitor
extends TestCase
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * Test simple Visitor usage
     */
    public void
    testSimpleUsage()
    {
        IVisitor<BlackNode> mySimpleVisitor = new SimpleVisitor();

        BlackNode myBlackNode = new BlackNode();

        // visit two times
        mySimpleVisitor.visit(myBlackNode);
        mySimpleVisitor.visit(myBlackNode);

        theLogger.debug("Running assertions ... ");

        assertEquals("Simple visit implementation did not work as expected.",
            BLACK_NODE_NAME, ((SimpleVisitor) mySimpleVisitor).
                getElementName());

        assertEquals("The two calls were not executed.",  2,
            ((SimpleVisitor) mySimpleVisitor).getCallsCounter());

        theLogger.debug("Completed");
    }

    //------------------------------------------------------------------------
    /**
     * Test simple Visitor usage
     */
    public void
    testArgumentCovariance()
    {
        SimpleVisitor mySimpleVisitor = new SimpleVisitor();
        DarkerVisitor myDarkerVisitor = new DarkerVisitor();

        BlackNode myBlackNode = new BlackNode();
        DarkerThanBlackNode myDarkerThanBlackNode = new DarkerThanBlackNode();

        // demonstrates how the Override inheritance type is also supported.
        // DarkerThanBlackNode inherits from BlackNode but is also visited
        // by the SimpleVisitor.
        mySimpleVisitor.visit(myDarkerThanBlackNode);

        // visit two times
        myDarkerVisitor.visit(myBlackNode);
        myDarkerVisitor.visit(myDarkerThanBlackNode);

        theLogger.debug("Running assertions ... ");

        assertEquals("Simple visit implementation did not work as expected.",
            DARKER_THAN_BLACK_NODE_NAME, mySimpleVisitor.getElementName());
        assertEquals("The calls were not executed.",  1, mySimpleVisitor.
            getCallsCounter());

        assertEquals("Simple visit implementation did not work as expected.",
            BLACK_NODE_NAME, myDarkerVisitor.getElementName());
        assertEquals("The calls were not executed.",  1, myDarkerVisitor.
            getCallsCounter());

        assertEquals("Simple visit implementation did not work as expected.",
            DARKER_THAN_BLACK_NODE_NAME, myDarkerVisitor.
                getDarkerElementName());
        assertEquals("The calls were not executed.",  1,
            ((SimpleVisitor) myDarkerVisitor).getCallsCounter());


        theLogger.debug("Completed");
    }

    //------------------------------------------------------------------------
    /**
     * Test simple Visitor usage
     */
    public void
    testSubVisitors()
    {
        VisitorD myVisitor = new VisitorD();

        BlackNode myBlackNode = new BlackNode();

        // test Overriding visit methods in subtypes of concrete Visitors
        myVisitor.visit(myBlackNode);

        theLogger.debug("Running assertions ... ");

        assertEquals("VisitorD visit implementation did not work as expected.",
            BLACK_NODE_NAME, myVisitor.getElementNameD());
        assertTrue("The calls were not executed.",  myVisitor.isCalledD());

        theLogger.debug("Completed");
    }

    //------------------------------------------------------------------------
    /**
     * Test that VisitorA properly visited the required nodes.
     */
    public void
    testVisitorA()
    {
        theLogger.debug("Creating Visitor pattern test fixture ... ");

        // fixture for the usage of Visitor Pattern
        fixture();

        theLogger.debug("Completed Visitor pattern test fixture.");

        theLogger.debug("Running assertions ... ");

        assertTrue("VisitorA visitRedNode was not successfully called.",
                theVisitorA.isCalled());

        assertEquals("VisitorA visited incorrect node.", RED_NODE_NAME,
                theVisitorA.getElementName());

        theLogger.debug("Completed");
    }

    //------------------------------------------------------------------------
    /**
     * Test that VisitorB properly visited the required nodes.
     */
    public void
    testVisitorB()
    {
        theLogger.debug("Creating Visitor pattern test fixture ... ");

        // fixture for the usage of Visitor Pattern
        fixture();

        theLogger.debug("Completed Visitor pattern test fixture.");

        theLogger.debug("Running assertions ... ");

        assertTrue("VisitorB's visitBlackNode was not successfully called.",
                theVisitorB.isCalled());

        assertEquals("VisitorB visited incorrect node.", BLACK_NODE_NAME,
                theVisitorB.getElementName());

        theLogger.debug("Completed");
    }

    //------------------------------------------------------------------------
    /**
     * Test that VisitorC properly visited the required nodes.
     */
    public void
    testVisitorC()
    {
        theLogger.debug("Creating Visitor pattern test fixture ... ");

        // fixture for the usage of Visitor Pattern
        fixture();

        theLogger.debug("Completed Visitor pattern test fixture.");

        theLogger.debug("Running assertions ... ");

        assertTrue("VisitorC visitBlackNode was not successfully called.",
                theVisitorC.isCalledBlack());

        assertTrue("VisitorC visitRedNode was not successfully called.",
                theVisitorC.isCalledRed());

        assertEquals("VisitorC visited incorrect node.", BLACK_NODE_NAME,
                theVisitorC.getBlackElementName());

        assertEquals("VisitorC visited incorrect node.", RED_NODE_NAME,
                theVisitorC.getRedElementName());

        theLogger.debug("Completed");
    }

    //------------------------------------------------------------------------
    /**
     * Test that Invalid Visitor visit method will be spotted.
     */
    public void
    testInvalidVisitor()
    {
        IVisitor<IElement> myVisitor = new InvalidVisitor<IElement>();

        try
        {
            myVisitor.visit(theRedNode);

            fail("Return type mismatch check that expects always void failed");
        }
        catch (IllegalArgumentException anException)
        {
            // ok
        }
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Provides example usage of the Visitor Pattern.
     */
    private void
    fixture()
    {
        theLogger.debug("Step #1 := Create a Collection of Visitors");

        Collection<IVisitor> myVisitors = Arrays.asList(new IVisitor[]
            {
                theVisitorA, theVisitorB
            });

        theLogger.debug("Step #2 := Have every visitor visit every element.");
        for (IVisitor myVisitor : myVisitors)
        {
            AbstractVisitor.reusableVisit(myVisitor, theBlackNode, theRedNode);
        }

        theLogger.debug("Step #3 := Have visitor C visit every element.");
        theVisitorC.visit(theBlackNode);
        theVisitorC.visit(theRedNode);
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Test Element that extends the AbstractElement base class.
     */
    public static
    class RedNode
    implements IElement
    {
        //--------------------------------------------------------------------
        /**
         * @return the myName
         */
        public final String
        getName()
        {
            return RED_NODE_NAME;
        }
    }

    //------------------------------------------------------------------------
    /**
     * Test Element that extends the AbstractElement base class.
     */
    public static
    class BlackNode
    implements IElement
    {
        //--------------------------------------------------------------------
        /**
         * @return the myName
         */
        public String
        getName()
        {
            return BLACK_NODE_NAME;
        }
    }

    //------------------------------------------------------------------------
    /**
     * Test Element that extends the AbstractElement base class.
     */
    public static
    class DarkerThanBlackNode
    extends BlackNode
    {
        //--------------------------------------------------------------------
        /**
         * @return the myName
         */
        @Override
        public final String
        getName()
        {
            return DARKER_THAN_BLACK_NODE_NAME;
        }
    }

    //-------------------------------------------------------------------------
    public static
    class SimpleVisitor
    extends AbstractVisitor<BlackNode>
    {
        //---------------------------------------------------------------------
        public void
        visitBlackNode(BlackNode aNode)
        {
            theCallsCounter++;

            theElementName = aNode.getName();
        }

        //---------------------------------------------------------------------
        /**
         * @return the Calls Counter
         */
        public int
        getCallsCounter()
        {
            return theCallsCounter;
        }

        //---------------------------------------------------------------------
        /**
         * @return the elementName
         */
        public final String
        getElementName()
        {
            return theElementName;
        }

        //---------------------------------------------------------------------
        // members
        //---------------------------------------------------------------------
        private int theCallsCounter = 0;
        private String theElementName = null;
    }

    //-------------------------------------------------------------------------
    public static
    class DarkerVisitor
    extends SimpleVisitor
    {
        //---------------------------------------------------------------------
        public void
        visitDarketThanBlackNode(DarkerThanBlackNode aNode)
        {
            theDarkerCallsCounter++;

            theDarkerElementName = aNode.getName();
        }

        //---------------------------------------------------------------------
        /**
         * @return the Calls Counter
         */
        public int
        getDarkerCallsCounter()
        {
            return theDarkerCallsCounter;
        }

        //---------------------------------------------------------------------
        /**
         * @return the elementName
         */
        public final String
        getDarkerElementName()
        {
            return theDarkerElementName;
        }

        //---------------------------------------------------------------------
        // members
        //---------------------------------------------------------------------
        private int theDarkerCallsCounter = 0;
        private String theDarkerElementName = null;
    }

    //-------------------------------------------------------------------------
    /**
     * Test Visitor that extends the AbstractVisitor base class.
     */
    public static
    class VisitorA<E extends IElement>
    extends AbstractVisitor<E>
    {
        //---------------------------------------------------------------------
        public void
        visitRedNode(RedNode aNode)
        {
            theCalled = true;
            theElementName = aNode.getName();
        }

        //---------------------------------------------------------------------
        /**
         * @return the Called
         */
        public final boolean
        isCalled()
        {
            return theCalled;
        }

        //---------------------------------------------------------------------
        /**
         * @return the elementName
         */
        public final String
        getElementName()
        {
            return theElementName;
        }

        //---------------------------------------------------------------------
        // members
        //---------------------------------------------------------------------
        private boolean theCalled = false;
        private String theElementName = null;
    }

    //------------------------------------------------------------------------
    /**
     * Test Visitor that extends the AbstractVisitor base class.
     */
    // CHECKSTYLE:OFF
    public static
    class VisitorB<E extends IElement>
    extends AbstractVisitor<E>
    {
        //--------------------------------------------------------------------
        public void
        visitBlackNode(BlackNode aNode)
        {
            theCalled = true;
            theElementName = aNode.getName();
        }

        //--------------------------------------------------------------------
        /**
         * @return the Called
         */
        public final boolean
        isCalled()
        {
            return theCalled;
        }

        //--------------------------------------------------------------------
        /**
         * @return the elementName
         */
        public final String
        getElementName()
        {
            return theElementName;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private boolean theCalled = false;
        private String theElementName = null;
    }

    //------------------------------------------------------------------------
    /**
     * Test Visitor that implements the IVisitor interface, and delegates core
     * visit method to the AbstractVisitor's static implementation.
     */
    public static
    class VisitorC<E extends IElement>
    implements IVisitor<E>
    {
        //--------------------------------------------------------------------
        public void
        visit(E anElement)
        {
            AbstractVisitor.reusableVisit(this, anElement);
        }

        //--------------------------------------------------------------------
        public void
        visitRedNode(RedNode aNode)
        {
            theCalledRed = true;
            theRedElementName = aNode.getName();
        }

        //--------------------------------------------------------------------
        public void
        visitBlackNode(BlackNode aNode)
        {
            theCalledBlack = true;
            theBlackElementName = aNode.getName();
        }

        //--------------------------------------------------------------------
        /**
         * @return the blackElementName
         */
        public final String
        getBlackElementName()
        {
            return theBlackElementName;
        }

        //--------------------------------------------------------------------
        /**
         * @return the CalledBlack
         */
        public final boolean
        isCalledBlack()
        {
            return theCalledBlack;
        }

        //--------------------------------------------------------------------
        /**
         * @return the CalledRed
         */
        public final boolean
        isCalledRed()
        {
            return theCalledRed;
        }

        //--------------------------------------------------------------------
        /**
         * @return the redElementName
         */
        public final String
        getRedElementName()
        {
            return theRedElementName;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private boolean theCalledRed = false;
        private boolean theCalledBlack = false;
        private String theRedElementName = null;
        private String theBlackElementName = null;
    }

    //------------------------------------------------------------------------
    /**
     * Invalid Visitor visit method implementation
     */
    public static
    class InvalidVisitor<E extends IElement>
    extends AbstractVisitor<E>
    {
        //--------------------------------------------------------------------
        public RedNode
        visitRedNode(RedNode aNode)
        {
            return aNode;
        }

        //--------------------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        protected void
        rejected(IElement anElement)
        {
            throw new IllegalArgumentException("Unsupported Element type: '" +
                anElement + "'");
        }
    }

    //------------------------------------------------------------------------
    /**
     * Test Visitor that extends the AbstractVisitor base class.
     */
    public static
    class VisitorD
    extends VisitorB
    {
        //--------------------------------------------------------------------
        @Override
        public void
        visitBlackNode(BlackNode aNode)
        {
            theCalled = true;
            theElementName = aNode.getName();
        }

        //--------------------------------------------------------------------
        /**
         * @return the Called
         */
        public final boolean
        isCalledD()
        {
            return theCalled;
        }

        //--------------------------------------------------------------------
        /**
         * @return the elementName
         */
        public final String
        getElementNameD()
        {
            return theElementName;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        protected boolean theCalled = false;
        protected String theElementName = null;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final String BLACK_NODE_NAME = "Black Node";
    private static final String DARKER_THAN_BLACK_NODE_NAME =
        "Darker than Black Node";
    private static final String RED_NODE_NAME = "Red Node";

    /**
     * Element components
     */
    private final BlackNode theBlackNode = new BlackNode();
    private final RedNode theRedNode = new RedNode();

    /**
     * Concrete Visitor implementations
     */
    private final VisitorA theVisitorA = new VisitorA();
    private final VisitorB theVisitorB = new VisitorB();
    private final VisitorC theVisitorC = new VisitorC();

    /**
     * Provides logging services for this class.
     */
    private final Logger theLogger = LoggerFactory.getLogger(this.getClass());
}

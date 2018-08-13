//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// AbstractVisitor.java Copyright (c) 2012 Giovanni Azua Garcia
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

import java.lang.reflect.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.behavioral.visitor.*;
import org.perfectjpattern.core.api.extras.delegate.*;
import org.perfectjpattern.core.extras.delegate.*;

/**
 * Reusable abstract base implementation of <code>IVisitor</code> interface.
 * <br/>
 *
 * @see IVisitor
 *
 * @param <E> Element type that this <code>IVisitor</code> can visit.
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jul 1, 2007 7:02:55 AM $
 */
public abstract
class AbstractVisitor<E>
implements IVisitor<E>
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public final void
    visit(E anElement)
    {
        Validate.notNull(anElement, "'anElement' must not be null");

        Class<?> myElementClass = anElement.getClass();
        String myElementClassName = myElementClass.getName();

        // do we have it already?
        IVisitor<E> myVisitor = theLookup.get(myElementClassName);
        if (myVisitor == null)
        {
            // lookup the right implementation
            Class<?> myElementType = myElementClass;
            Class<?> myVisitorType = theVisitorClass;

            Method myMethod = findVisitorMethod(myElementType, myVisitorType,
                theDelegator);

            // any matching methods found?
            if (myMethod != null)
            {
                myVisitor = theDelegator.build(this, myMethod);

                theLookup.put(myElementClassName, myVisitor);
            }
        }

        // if a matching visit method was found then visit
        if (myVisitor != null)
        {
            // do the visiting
            myVisitor.visit(anElement);
        }
        else
        {
            rejected(anElement);
        }
    }

    //------------------------------------------------------------------------
    /**
     * Reusable implementation of the visit method that may be reused by
     * implementors of <code>IVisitor</code> that for any reason can not
     * subclass <code>AbstractVisitor</code>.
     *
     * @param <E> Type of Element to visit.
     *
     * @param aVisitor Concrete IVisitor instance.
     * @param anElements Concrete IElement instances to visit.
     * @throws IllegalArgumentException 'aVisitor' must not be null.
     * @throws IllegalArgumentException 'anElements' must not be null.
     * @throws IllegalArgumentException 'anElements' must not be empty.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <E> void
    reusableVisit(IVisitor<E> aVisitor, E... anElements)
    throws IllegalArgumentException
    {
        Validate.notNull(aVisitor, "'aVisitor' must not be null");
        Validate.notNull(anElements, "'anElements' must not be null");
        Validate.notEmpty(anElements, "'anElements' must not be empty.");

        for (E myElement : anElements)
        {
            // lookup the right implementation
            Class<?> myElementType = myElement.getClass();
            Class<?> myVisitorType = aVisitor.getClass();

            VisitorDelegator<E> myDelegator = new VisitorDelegator(
                IVisitor.class);

            Method myMethod = findVisitorMethod(myElementType, myVisitorType,
                myDelegator);

            // any matching methods found?
            if (myMethod != null)
            {
                IVisitor<E> myRealVisitor = myDelegator.build(aVisitor,
                    myMethod);

                // do the visiting
                myRealVisitor.visit(myElement);
            }
        }
    }

    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Provides a means for concrete {@link IVisitor} implementations to control
     * rejected Elements. That is, rejection of Elements for which there isn't
     * a matching visit implementation.
     *
     * @param anElementClass The Element class that was rejected
     */
    protected void
    rejected(E anElement)
    {
        // do nothing
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    /**
     * Subclass implementation of Delegator redefining specific methods.
     */
    private static
    class VisitorDelegator<E>
    extends Delegator<IVisitor<E>>
    {
        //--------------------------------------------------------------------
        /**
         * Constructs a <code>VisitorDelegator</code> instance from a Visitor
         * that visits Elements of Type E.
         *
         * @param aClass Class type of Visitor.
         */
        public
        VisitorDelegator(Class<IVisitor<E>> aClass)
        {
            super(aClass);
        }

        //--------------------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        public IVisitor<E>
        build(Object aTarget, Method aMethod)
        throws IllegalArgumentException, UnsupportedOperationException
        {
            DelegateProxy myProxy = new DelegateProxy(aTarget,
                aTarget.getClass(), aMethod, this);

            Class<?> myInterface = IVisitor.class;

            // build dynamic proxy
            Class<?>[] myInterfaces = {myInterface, IDelegate.class };

            IVisitor<E> myDelegate = null;
            try
            {
                // try with the target
                myDelegate = (IVisitor<E>) java.lang.reflect.Proxy.
                    newProxyInstance(aTarget.getClass().getClassLoader(),
                        myInterfaces, myProxy);
            }
            catch (IllegalArgumentException anException)
            {
                // retry with the Interface class
                myDelegate = (IVisitor<E>) java.lang.reflect.Proxy.
                    newProxyInstance(myInterface.getClassLoader(),
                        myInterfaces, myProxy);
            }

            return myDelegate;
        }

        //--------------------------------------------------------------------
        /**
         * Returns true if the <code>aTestMethod</code> is a valid visit
         * method, false otherwise.
         * <br/><br/>
         * A valid visit method:
         * <ul>
         * <li>MUST have exactly one argument</li>
         * <li>MUST not be the <code>IVisitor.visit</code> method (this is
         * also statically enforced at compile time because it is final)</li>
         * <li>MUST be prefixed with "visit". This constraint was introduced
         * to ensure that there is no conflict or confusion as to which method
         * is visitor and which not. Another alternative is using annotations
         * but I think this solution is simpler, more elegant and readable.</li>
         * <li>MUST be public</li>
         * <li>MUST be the same or a supertype of the Element type</li>
         * <li>MUST match the return type of <code>IVisitor.visit</code>
         * i.e. void.class</li>
         * </ul>
         * @param aTestMethod The method being tested for a valid visitor match
         * @param aReturnClass The return class of the <code>IVisitor.
         *                     visit</code> method
         * @param anArgumentTypes The Element type
         * @return true if the <code>aTestMethod</code> is a valid visit
         *         method, false otherwise
         */
        @Override
        protected boolean
        isSuitableMethod(Method aTestMethod, Class<?> aReturnClass,
            Class<?>... anArgumentTypes)
        {
            boolean mySuitableMethod = false;

            String myMethodName = aTestMethod.getName();
            Class<?>[] myArgumentTypes = aTestMethod.getParameterTypes();

            // must have exactly one argument
            if (myArgumentTypes.length == 1)
            {
                Class<?> myArgumentType = myArgumentTypes[0];

                // must not be the IVisitor.visit method
                if (!("visit".equals(myMethodName)))
                {
                    Class<?> myElementClass = anArgumentTypes[0];

                    // must be prefixed with "visit"
                    // must be public
                    // must be the same or a supertype of the Element type
                    // must match the return type of IVisitor.visit i.e. void
                    if (myMethodName.startsWith("visit") &&
                        Modifier.isPublic(aTestMethod.getModifiers()) &&
                        myArgumentType.isAssignableFrom(myElementClass) &&
                        isValidReturn(aTestMethod, aReturnClass))
                    {
                        mySuitableMethod = true;
                    }
                }
            }

            return mySuitableMethod;
        }

        //--------------------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean
        isValidReturn(Method aTestMethod, Class<?> aReturnClass)
        {
            // visit method should not return anything but Void
            return void.class.equals(aTestMethod.getReturnType());
        }

        //--------------------------------------------------------------------
        /**
         * Offers less restrictive access modifier than the super class. The
         * superclass defines this method as protected, this subclass offer
         * it as public to clients.
         */
        @Override
        public Class<?>
        getReturn()
        {
            return super.getReturn();
        }
    }

    //------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------
    /**
     * Returns Visitor method matching the specified Element argument type,
     * null if the requested Method is not found.
     *
     * @param anElementType Class that corresponds to the concrete Element type.
     * @param aVisitorType Class that corresponds to the concrete Visitor type.
     * @param aDelegator Delegator implementation.
     * @return Visitor method matching the specified Element argument type,
     * null if the requested Method is not found.
     */
    private static Method
    findVisitorMethod(Class<?> anElementType, Class<?> aVisitorType,
        VisitorDelegator<?> aDelegator)
    {
        Class<?> myReturnClass = aDelegator.getReturn();
        List<Method> myCandidates = new ArrayList<Method>();
        Method[] myVisitorMethods = aVisitorType.getMethods();
        for (Method myTestMethod : myVisitorMethods)
        {
            if (aDelegator.isSuitableMethod(myTestMethod, myReturnClass,
                anElementType))
            {
                myCandidates.add(myTestMethod);
            }
        }

        Method myMethod = null;
        if (myCandidates.size() > 0)
        {
            myMethod = Collections.min(myCandidates, COVARIANCE_COMPARATOR);
        }

        return myMethod;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private final Class<?> theVisitorClass = this.getClass();

    /**
     * {@link Comparator} implementation for ordering Methods based on the
     * position of the first argument type within the same hierarchy.
     */
    private static final Comparator<Method> COVARIANCE_COMPARATOR =
        new Comparator<Method>()
    {
        //----------------------------------------------------------------
        /**
         * Returns -1 if parameter of <code>aMethodOne</code> is a supertype
         * of parameter of <code>aMethodTwo</code>, +1 otherwise.
         *
         * @param aMethodOne
         * @param aMethodTwo
         * @return
         */
        public int
        compare(Method aMethodOne, Method aMethodTwo)
        {
            Class<?> myClassOne = aMethodOne.getParameterTypes()[0];
            Class<?> myClassTwo = aMethodTwo.getParameterTypes()[0];

            int myResult = 0;
            if (myClassTwo.isAssignableFrom(myClassOne))
            {
                myResult = -1;
            }
            else
            {
                myResult = +1;
            }

            return myResult;
        }
    };

    /**
     * <code>Map</code> of <code>IVisitor</code> instances keyed by
     * <code>IElement</code>-based Class names.
     */
    private final Map<String, IVisitor<E>> theLookup = new HashMap<String,
        IVisitor<E>>();

    /**
     * Special subtype of <code>Delegator<code> that delegates only to
     * subtypes of the <code>IVisitor</code> interface.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private final VisitorDelegator<E> theDelegator = new VisitorDelegator(
        IVisitor.class);
}

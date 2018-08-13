//----------------------------------------------------------------------
// 
// PerfectJPattern: "Design patterns are good but components are better!" 
// AbstractDelegator.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.core.extras.delegate;

import java.lang.reflect.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.perfectjpattern.core.api.extras.delegate.*;


/**
 * Abstract base implementation of <code>IDelegator</code> interface.
 * <br/><br/>
 * <b>Notes</b>: Base source code implemented by Steve Lewis and Wilhelm 
 * Fitzpatrick and adapted to fit PerfectJPattern componentization 
 * criteria and code conventions.
 *
 * @see IDelegator
 * 
 * @param <I> Interface type built by this <code>Delegator</code> instance.
 * 
 * @author <a href="mailto:smlewis@lordjoe.com">Steve Lewis</a>
 * @author <a href="mailto:wilhelmf@agileinformatics.com">Wilhelm 
 * Fitzpatrick</a>
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $Revision: 1.0 $ $Date: Jun 25, 2007 7:01:04 AM $
 */
public abstract
class AbstractDelegator<I>
implements IDelegator<I> 
{
    //------------------------------------------------------------------------
    // protected
    //------------------------------------------------------------------------
    /**
     * Constructs <code>Delegator</code> instance using a method
     * signature. <br/><br/>
     * 
     * @param aReturnClass Return class type.
     * @param aParameters Parameter class types.
     */
    protected
    AbstractDelegator(Class<?> aReturnClass, Class<?>... aParameters) 
    {
        theReturn = aReturnClass;
        theArguments = aParameters;
    }    
    
    //------------------------------------------------------------------------
    /**
     * Constructs <code>Delegator</code> instance using an interface type 
     * that implements exactly one method.
     * 
     * @param anInterface Interface with EXACTLY one method.
     * @throws IllegalArgumentException DelegateTemplate must be constructed 
     * with an interface implementing exactly one method!
     */
    protected
    AbstractDelegator(Class<I> anInterface) 
    {
        Method myMethod = findMethod(anInterface);
        
        theReturn = myMethod.getReturnType();
        theArguments = myMethod.getParameterTypes();
    }

    //------------------------------------------------------------------------
    /**
     * Returns true if the Method matches the given signature specified by 
     * Arguments and Return, false otherwise.
     * 
     * @param aTestMethod
     * @param anArguments
     * @param aReturnClass
     * @return true if the Method matches the given signature specified by 
     * Arguments and Return, false otherwise.
     */
    protected boolean 
    isSuitableMethod(Method aTestMethod, Class<?> aReturnClass, 
        Class<?>... anArguments)
    {
        Class<?>[] myMethodArgs = aTestMethod.getParameterTypes();

        for (int i = 0; i < myMethodArgs.length; i++) 
        {
            Class<?> myArgument = myMethodArgs[i];

            if (!myArgument.isAssignableFrom(anArguments[i])) 
            {
                return false;
            }
        }

        return isValidReturn(aTestMethod, aReturnClass);
    }

    //------------------------------------------------------------------------
    /**
     * Returns true if return type matches the expected type, false otherwise.
     * 
     * @param aTestMethod
     * @param aReturnClass
     * @return true if return type matches the expected type, false otherwise.
     */
    protected boolean 
    isValidReturn(Method aTestMethod, Class<?> aReturnClass) 
    {
        return (aReturnClass == null             
             || aTestMethod.getReturnType() == aReturnClass
             || aTestMethod.getReturnType().equals(aReturnClass)
             || aReturnClass.isAssignableFrom(aTestMethod.getReturnType()));
    }

    //------------------------------------------------------------------------
    /**
     * Returns all candidate methods within a class that match the given 
     * signature.
     * 
     * @param aTargetClass
     * @param aMethodName
     * @param aNumberOfArguments
     * @return all candidate methods within a class that match the given 
     *         signature.
     */
    protected static Method[] 
    findMethod(Class<?> aTargetClass, String aMethodName, 
        int aNumberOfArguments) 
    {
        Method[] myPossibilities = aTargetClass.getMethods();
        List<Method> myHolder = new ArrayList<Method>();

        for (int i = 0; i < myPossibilities.length; i++) 
        {
            Method myPossibility = myPossibilities[i];

            if (myPossibility.getName().equals(aMethodName) &&
                myPossibility.getParameterTypes().length == 
                    aNumberOfArguments && Modifier.isPublic(myPossibility.
                        getModifiers())) 
            {
                myHolder.add(myPossibility);
            }
        }

        return myHolder.toArray(EMPTY_METHOD_ARRAY);
    }

    //------------------------------------------------------------------------
    /**
     * Returns suitable Method within class type that matches the given 
     * Delegator signature.
     * 
     * @param aTargetClass Class type to search for matching method.
     * @param aMethodName Method name to search for.
     * @param aDelegator Delegate specification.
     * @return suitable Method within class type that matches the given 
     * Delegate specification.
     * @throws IllegalArgumentException Requested method returns wrong type.
     * @throws IllegalArgumentException Requested method is not public.
     * @throws UnsupportedOperationException No suitable method found.
     */
    protected Method 
    findMethod(Class<?> aTargetClass, String aMethodName, 
        AbstractDelegator<?> aDelegator)
    throws IllegalArgumentException, UnsupportedOperationException
    {
        Class<?>[] myArguments = aDelegator.getArguments();
        Class<?> myReturnClass = aDelegator.getReturn();

        try
        {
            Method myReturn = aTargetClass.getMethod(aMethodName, myArguments);

            Validate.isTrue(isValidReturn(myReturn, myReturnClass), 
                "Requested method returns wrong type.");
            
            Validate.isTrue(Modifier.isPublic(myReturn.getModifiers()), 
                "Requested method is not public.");

            return myReturn;
        }
        catch (SecurityException anException)
        {
            // try next possibility
        }
        catch (NoSuchMethodException anException)
        {
            // try next possibility
        }

        Method[] myPossibilities = findMethod(aTargetClass, aMethodName, 
            myArguments.length);

        for (int i = 0; i < myPossibilities.length; i++) 
        {
            Method myPossibility = myPossibilities[i];

            if (isSuitableMethod(myPossibility, myReturnClass, myArguments)) 
            {
                return myPossibility;
            }
        }

        throw new UnsupportedOperationException("No suitable method found.");
    }

    //------------------------------------------------------------------------
    /**
     * Returns suitable <code>Method</code> found for the given interface type.
     * 
     * @param anInterface
     * @return The suitable <code>Method</code>
     * @throws IllegalArgumentException 'anInterface' must not be null.
     * @throws IllegalArgumentException DelegateTemplate must be constructed 
     * with an interface implementing exactly one method!
     */
    protected static Method 
    findMethod(Class<?> anInterface) 
    {
        Validate.notNull(anInterface, "'anInterface' must not be null");
        Validate.isTrue(anInterface.isInterface(), 
            "DelegateTemplate must be constructed with an interface");

        Method[] myMethods = anInterface.getMethods();
        Method myReturn = null;

        for (int i = 0; i < myMethods.length; i++) 
        {
            Method myTest = myMethods[i];

            if (Modifier.isAbstract(myTest.getModifiers())) 
            {
                Validate.isTrue(myReturn == null, "DelegateTemplate must be " +
                    "constructed with an interface implementing exactly one " +
                    "method!");

                myReturn = myTest;
            }
        }

        Validate.notNull(myReturn, "DelegateTemplate must be constructed " +
            "with an interface implementing exactly one method!");

        return myReturn;
    }
    
    //------------------------------------------------------------------------
    /**
     * Returns the Return class type.
     * 
     * @return the Return class type.
     */
    protected Class<?>
    getReturn() 
    {
        return theReturn;
    }

    //------------------------------------------------------------------------
    /**
     * Returns the Arguments class types.
     * 
     * @return Arguments class types.
     */
    protected Class<?>[] 
    getArguments() 
    {
        return theArguments;
    }

    //------------------------------------------------------------------------
    // inner classes
    //------------------------------------------------------------------------
    protected
    class DelegateProxy 
    implements IDelegate, InvocationHandler 
    {
        //--------------------------------------------------------------------
        /**
         * Constructs a DelegateProxy supplying a Class passing in types not 
         * template.
         */
        public 
        DelegateProxy(Object aTarget, Class<?> aTargetClass, String aMethodName,
            AbstractDelegator<?> aTemplate) 
        throws UnsupportedOperationException
        {
            theTarget = aTarget;

            Method myMethod = findMethod(aTargetClass, aMethodName, aTemplate);
            theMethod = myMethod;
        }

        //--------------------------------------------------------------------
        /**
         * Constructs a DelegateProxy supplying a Class passing in types not 
         * template.
         */
        public 
        DelegateProxy(Object aTarget, Class<?> aTargetClass, Method aMethod,
            AbstractDelegator<?> aTemplate) 
        throws UnsupportedOperationException
        {
            theTarget = aTarget;
            theMethod = aMethod;
        }

        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        public Object 
        invoke(Object aProxy, Method aMethod, Object[] anArguments) 
        {
            return invoke(anArguments);
        }

        //--------------------------------------------------------------------
        /** 
         * {@inheritDoc}
         */
        public Object 
        invoke(Object... anArguments)
        throws IllegalArgumentException, UnsupportedOperationException
        {
            validateArgs(anArguments, getArguments());
            
            try 
            {
                Object myReturn = getMethod().invoke(getTarget(), anArguments);

                return myReturn;
            } 
            catch (IllegalAccessException anException) 
            {
                throw new UnsupportedOperationException(
                    anException.getMessage()); 
            } 
            catch (InvocationTargetException anException) 
            {
                throw new UnsupportedOperationException(anException.getCause());
            }
        }

        //--------------------------------------------------------------------
        /**
         * Validate actual arguments to match those expected from the Delegate 
         * type.
         * 
         * @param anArguments Arguments to validate
         * @param anExpectedArguments Expected arguments signature
         * @throws IllegalArgumentException 'anExpectedArguments' must not be 
         *         null
         * @throws IllegalArgumentException Delegator required 'N' arguments
         * @throws IllegalArgumentException Argument 'i' must be of type 'X'
         */
        protected void 
        validateArgs(Object[] anArguments, Class<?>[] anExpectedArguments)
        throws IllegalArgumentException 
        {
            Validate.notNull(anExpectedArguments, 
                "'anExpectedArguments' must not be null");
            
            Validate.isTrue((anArguments == null && anExpectedArguments.length 
                == 0) || (anArguments != null && anArguments.length == 
                    anExpectedArguments.length), "Delegator required '" + 
                        anExpectedArguments.length + "' arguments");

            if (anArguments != null)
            {
                for (int i = 0; i < anArguments.length; i++) 
                {
                    Validate.isTrue(anExpectedArguments[i].isInstance(
                        anArguments[i]), "Argument '" + i + "' must be of " +
                            "type '" + anExpectedArguments[i].getName() + "'");
                }
            }
        }

        //--------------------------------------------------------------------
        /**
         * Returns the method.
         * 
         * @return Method.
         */
        protected Method 
        getMethod() 
        {
            return theMethod;
        }

        //--------------------------------------------------------------------
        /**
         * Returns the target Object.
         * 
         * @return Target Object.
         */
        protected Object 
        getTarget() 
        {
            return theTarget;
        }

        //--------------------------------------------------------------------
        // members
        //--------------------------------------------------------------------
        private final Method theMethod;
        private final Object theTarget;        
    }
            
    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final Method[] EMPTY_METHOD_ARRAY = {};

    private final Class<?> theReturn;
    private final Class<?>[] theArguments;    
}

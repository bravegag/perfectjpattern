//----------------------------------------------------------------------
//
// PerfectJPattern: "Design patterns are good but components are better!"
// FinderIntroductionInterceptor.java Copyright (c) 2012 Giovanni Azua Garcia
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
package org.perfectjpattern.jee.integration.dao;

import java.util.*;

import org.aopalliance.intercept.*;
import org.perfectjpattern.jee.api.integration.dao.*;
import org.springframework.aop.*;


/**
 * Concrete implementation of {@link IntroductionInterceptor} that connects
 * Spring AOP with the Hibernate's based generic DAO. For any method
 * beginning with <code>findBy</code> this interceptor will use the
 * {@link IFinderExecutor} to call the corresponding Hibernate named query
 *
 * @author <a href="mailto:bravegag@hotmail.com">Giovanni Azua</a>
 * @version $ $Date: Nov 6, 2008 1:53:25 PM $
 */
class FinderIntroductionInterceptor<Element>
implements IntroductionInterceptor
{
    //------------------------------------------------------------------------
    // public
    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object
    invoke(MethodInvocation anArgument) throws Throwable
    {
        IFinderExecutor<Element> myExecutor = (IFinderExecutor<Element>)
            anArgument.getThis();

        Object myResults = null;

        String myMethodName = anArgument.getMethod().getName();
        if (myMethodName.startsWith("findBy") && !EXCLUDE_METHODS.contains(
            myMethodName))
        {
            Object[] myArguments = anArgument.getArguments();

            myResults = myExecutor.execute(anArgument.getMethod(), myArguments);
        }
        else
        {
            myResults = anArgument.proceed();
        }

        return myResults;
    }

    //------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("rawtypes")
    public boolean
    implementsInterface(Class anInterface)
    {
        boolean myResult = anInterface.isInterface() && IFinderExecutor.class.
            isAssignableFrom(anInterface);

        return myResult;
    }

    //------------------------------------------------------------------------
    // members
    //------------------------------------------------------------------------
    private static final SortedSet<String> EXCLUDE_METHODS =
        new TreeSet<String>();

    //------------------------------------------------------------------------
    // static initializers
    //------------------------------------------------------------------------
    static
    {
        EXCLUDE_METHODS.add("findById");
        EXCLUDE_METHODS.add("findByExample");
    }
}

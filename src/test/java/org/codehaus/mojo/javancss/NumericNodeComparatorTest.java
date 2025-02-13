package org.codehaus.mojo.javancss;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.dom4j.Node;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for NumericNodeComparator class.
 *
 * @author <a href="jeanlaurent@gmail.com">Jean-Laurent de Morlhon</a>
 */
public class NumericNodeComparatorTest
{
    private static final String NODE_PROPERTY = "foobar";

    private static final Integer SMALL_VALUE = 10;

    private static final Integer BIG_VALUE = 42;

    private Node bigNodeMock;

    private Node smallNodeMock;

    private NumericNodeComparator nnc;

    @Before
    public void setUp()
    {
        nnc = new NumericNodeComparator(NODE_PROPERTY);
        bigNodeMock = createMock(Node.class);
        smallNodeMock = createMock(Node.class);
    }
    
    @Test
    public void testComparePositive()
    {
        expect(bigNodeMock.numberValueOf(NODE_PROPERTY)).andReturn(BIG_VALUE);
        expect(smallNodeMock.numberValueOf(NODE_PROPERTY)).andReturn(SMALL_VALUE);

        replay(bigNodeMock, smallNodeMock);

        assertTrue(nnc.compare(smallNodeMock, bigNodeMock) > 0);

        verify(bigNodeMock, smallNodeMock);
    }
    
    @Test
    public void testCompareNegative()
    {
        expect(bigNodeMock.numberValueOf(NODE_PROPERTY)).andReturn(SMALL_VALUE);
        expect(smallNodeMock.numberValueOf(NODE_PROPERTY)).andReturn(BIG_VALUE);

        replay(bigNodeMock, smallNodeMock);

        assertTrue(nnc.compare(smallNodeMock, bigNodeMock) < 0);

        verify(bigNodeMock, smallNodeMock);
    }
    
    @Test
    public void testCompareEqual()
    {
        expect(bigNodeMock.numberValueOf(NODE_PROPERTY)).andReturn(SMALL_VALUE);
        expect(smallNodeMock.numberValueOf(NODE_PROPERTY)).andReturn(SMALL_VALUE);

        replay(bigNodeMock, smallNodeMock);

        assertEquals(0, nnc.compare(smallNodeMock, bigNodeMock));

        verify(bigNodeMock, smallNodeMock);
    }

    // should throw npe whenever one of the node is null
    @Test(expected = NullPointerException.class)
    public void testCompareWithBigNull()
    {
        expect(smallNodeMock.numberValueOf(NODE_PROPERTY)).andReturn(SMALL_VALUE);
        replay(smallNodeMock);

        nnc.compare(null, smallNodeMock);
    }
    
    @Test(expected = NullPointerException.class)
    public void testCompareWithSmallNull()
    {
        expect(bigNodeMock.numberValueOf(NODE_PROPERTY)).andReturn(BIG_VALUE);
        replay(bigNodeMock);

        nnc.compare(bigNodeMock, null);
    }

}

package org.codehaus.mojo.javancss;

import org.dom4j.Node;
import org.easymock.MockControl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for NumericNodeComparator class.
 *
 * @author <a href="jeanlaurent@gmail.com">Jean-Laurent de Morlhon</a>
 */
class NumericNodeComparatorTest
{
    private static final String NODE_PROPERTY = "foobar";

    private static final Integer SMALL_VALUE = new Integer( 10 );

    private static final Integer BIG_VALUE = new Integer( 42 );

    private MockControl control;

    private Node bigNodeMock;

    private Node smallNodeMock;

    private NumericNodeComparator nnc;

    @BeforeEach
    void setUp()
    {
        control = MockControl.createControl( Node.class );
        nnc = new NumericNodeComparator( NODE_PROPERTY );
        bigNodeMock = (Node) control.getMock();
        smallNodeMock = (Node) control.getMock();
    }

    @Test
    void comparePositive()
    {
        bigNodeMock.numberValueOf( NODE_PROPERTY );
        control.setReturnValue( BIG_VALUE );
        smallNodeMock.numberValueOf( NODE_PROPERTY );
        control.setReturnValue( SMALL_VALUE );
        control.replay();
        assertTrue( nnc.compare( smallNodeMock, bigNodeMock ) > 0 );
        control.verify();
    }

    @Test
    void compareNegative()
    {
        bigNodeMock.numberValueOf( NODE_PROPERTY );
        control.setReturnValue( SMALL_VALUE );
        smallNodeMock.numberValueOf( NODE_PROPERTY );
        control.setReturnValue( BIG_VALUE );
        control.replay();
        assertTrue( nnc.compare( smallNodeMock, bigNodeMock ) < 0 );
        control.verify();
    }

    @Test
    void compareEqual()
    {
        bigNodeMock.numberValueOf( NODE_PROPERTY );
        control.setReturnValue( SMALL_VALUE );
        smallNodeMock.numberValueOf( NODE_PROPERTY );
        control.setReturnValue( SMALL_VALUE );
        control.replay();
        assertEquals( 0, nnc.compare( smallNodeMock, bigNodeMock ) );
        control.verify();
    }

    // should throw npe whenever one of the node is null
    @Test
    void compareWithBigNull()
    {
        smallNodeMock.numberValueOf( NODE_PROPERTY );
        control.setReturnValue( SMALL_VALUE );
        control.replay();
        boolean caught = false;
        try
        {
            nnc.compare( null, smallNodeMock );
        }
        catch ( NullPointerException npe )
        {
            caught = true;
        }
        assertTrue( caught );
        control.verify();
    }

    @Test
    void compareWithSmallNull()
    {
        bigNodeMock.numberValueOf( NODE_PROPERTY );
        control.setReturnValue( BIG_VALUE, MockControl.ZERO_OR_MORE );
        control.replay();
        boolean caught = false;
        try
        {
            nnc.compare( bigNodeMock, null );
        }
        catch ( NullPointerException npe )
        {
            caught = true;
        }
        assertTrue( caught );
        control.verify();
    }

}

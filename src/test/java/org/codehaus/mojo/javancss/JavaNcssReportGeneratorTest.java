package org.codehaus.mojo.javancss;

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.codehaus.mojo.javancss.NcssExecuter;

import junit.framework.TestCase;

/**
 * @author Jean-Laurent de Morlhon
 */
public class JavaNcssReportGeneratorTest
    extends TestCase
{

    /**
     * Test method for 'net.morlhon.JavaNcssReportGenerator.transformArgs(String)'
     */
    public void testTransformArgs()
    {
        assertNull( new NcssExecuter().transformArgs( null ) );
        checkArray( new NcssExecuter().transformArgs( "" ), new String[0] );
        String input = "AAA BBB CCC";
        String[] output = { "AAA", "BBB", "CCC" };
        checkArray( new NcssExecuter().transformArgs( input ), output );
    }

    private void checkArray( String[] result, String[] output )
    {
        assertNotNull( result );
        assertEquals( result.length, output.length );
        int max = result.length;
        for ( int i = 0; i < max; i++ )
        {
            assertEquals( result[i], output[i] );
        }
    }

}

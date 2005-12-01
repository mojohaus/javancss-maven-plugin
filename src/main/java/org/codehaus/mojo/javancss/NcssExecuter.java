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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javancss.Javancss;
import javancss.Main;

/**
 * The NcssExecuter is able to call javaNcss to produce a code analysis.<br>
 * The results are produced into a raw xml file. 
 * 
 * @author <a href="jeanlaurent@gmail.com">Jean-Laurent de Morlhon</a>
 */
public class NcssExecuter
{
    private static final String WHAT_OPTION = "-package -object -function ";

    private static final String DEFAULT_OPTION = "-xml -recursive -out ";

    private File sourceDirectory;

    private String outputFilename;

    /**
     * Construct a NcssExecuter with no arguments.<br>
     * Used for testing.
     */
    /*package*/NcssExecuter()
    {
        this.sourceDirectory = null;
        this.outputFilename = null;
    }

    /**
     * Construct a NcssExecuter.
     * 
     * @param sourceDirectory the directory where the source to analyse are.
     * @param outputFilename the output file where the result will be written. 
     */
    public NcssExecuter( File sourceDirectory, String outputFilename )
    {
        this.sourceDirectory = sourceDirectory;
        this.outputFilename = outputFilename;
    }

    /**
     * Call the javaNcss code analysis tool to produce the result to a temporary file name.<br>
     */
    public void execute()
    {
        final String ncssCmdLine = WHAT_OPTION + DEFAULT_OPTION + outputFilename + " " + sourceDirectory;
        new Javancss( transformArgs( ncssCmdLine ), Main.S_RCS_HEADER );
    }

    /**
     * Convert a space separated arguments String into an array of String.<br>
     * package scope for testing purpose.
     * 
     * @param argString
     *            A String of space separated arguments
     * @return An array of String holding each arguments. returns null if input string is null. return an empty string array if there is no arguments.
     */
    /* package */String[] transformArgs( String argString )
    {
        if ( argString == null )
        {
            return null;
        }
        StringTokenizer strok = new StringTokenizer( argString, " " );
        List list = new ArrayList();
        while ( strok.hasMoreTokens() )
        {
            list.add( strok.nextToken() );
        }
        return (String[]) list.toArray( new String[list.size()] );
    }

}

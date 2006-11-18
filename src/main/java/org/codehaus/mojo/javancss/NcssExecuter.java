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

import org.apache.maven.reporting.MavenReportException;

import javancss.Javancss;
import javancss.Main;

/**
 * The NcssExecuter is able to call javaNCSS to produce a code analysis.<br>
 * The results are produced into a raw xml file.
 * 
 * @author <a href="jeanlaurent@gmail.com">Jean-Laurent de Morlhon</a>
 * 
 * @version $Id$
 */
public class NcssExecuter
{
    // the full path to the directory holding the sources to point JavaNCSS to.
    // Or the location of a file holding the path towards all files. (javancss style *sigh* :)
    private File sourceLocation;

    // JavaNCSS will write an xml output into this file.
    private String outputFilename;

    /**
     * Construct a NcssExecuter with no arguments.<br>
     * Used for testing.
     */
    /* package */NcssExecuter()
    {
        this.sourceLocation = null;
        this.outputFilename = null;
    }

    /**
     * Construct a NcssExecuter.
     * 
     * @param sourceDirectory
     *            the directory where the source to analyse are.
     * @param outputFilename
     *            the output file where the result will be written.
     */
    public NcssExecuter( File sourceDirectory, String outputFilename )
    {
        this.sourceLocation = sourceDirectory;
        this.outputFilename = outputFilename;
    }

    /**
     * Call the javaNCSS code analysis tool to produce the result to a temporary file name.<br>
     * 
     * @throws MavenReportException
     *             if somethings goes bad during the execution
     */
    public void execute() throws MavenReportException
    {
        Javancss javancss = new Javancss( getCommandLineArgument(), Main.S_RCS_HEADER );
        Throwable ncssException = javancss.getLastError();
        if ( ncssException != null )
        {
            throw new MavenReportException( "Error while JavaNCSS was executing", new Exception( ncssException ) );
        }
    }

    private String[] getCommandLineArgument()
    {
        List argumentList = new ArrayList( 8 );
        argumentList.add( "-package" );
        argumentList.add( "-object" );
        argumentList.add( "-function" );
        argumentList.add( "-xml" );
        argumentList.add( "-recursive" );
        argumentList.add( "-out" );
        argumentList.add( outputFilename );
        // If the sourcelocation is a directory it means we can pass it straight to
        // javancss. If it's a file, we assume it's containing the file list to parse
        // so we pass it to javancss the way it expects it.
        // (check javancss cmd line doc for more information)
        if ( sourceLocation.isDirectory() )
        {
            argumentList.add( sourceLocation.getAbsolutePath() );
        }
        else
        {
            argumentList.add( '@' + sourceLocation.getAbsolutePath() );
        }
        return (String[]) argumentList.toArray( new String[argumentList.size()] );
    }

}

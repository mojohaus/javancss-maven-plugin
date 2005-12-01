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
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.site.renderer.SiteRenderer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * Maven2 Plugin Mojo which generates JavaNCSS code metrics.
 * 
 * @goal javancss-report
 * 
 * @author <a href="jeanlaurent@gmail.com">Jean-Laurent de Morlhon</a>
 */
public class NcssReportMojo
    extends AbstractMavenReport
{
    /**
     * Specifies the directory where the report will be generated
     * 
     * @parameter default-value="${project.reporting.outputDirectory}"
     * @required
     */
    private File outputDirectory;

    /**
     * Specifies the location of the source files to be used for Checkstyle
     * 
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     */
    private File sourceDirectory;

    /**
     * Specifies the maximum number of lines to take into account into the reports.
     * 
     * @parameter default-value="30"
     * @required
     */
    private int lineThreshold;

    /**
     * @parameter default-value="javancss-raw-report.xml"
     * @required
     */
    private String tempFileName;

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter expression="${component.org.codehaus.doxia.site.renderer.SiteRenderer}"
     * @required
     * @readonly
     */
    private SiteRenderer siteRenderer;

    /**
     * @see org.apache.maven.reporting.MavenReport#execute(java.util.Locale)
     */
    public void executeReport( Locale locale )
        throws MavenReportException
    {
        // run javaNCss and produce an temp xml file
        NcssExecuter ncssExecuter = new NcssExecuter( sourceDirectory, buildOutputFileName() );
        ncssExecuter.execute();
        // parse the freshly generated file and write the report
        NcssReportGenerator reportGenerator = new NcssReportGenerator( getSink(), getBundle( locale ) );
        reportGenerator.doReport( locale, loadDocument(), lineThreshold );
    }

    // load the raw javancss xml report
    private Document loadDocument()
        throws MavenReportException
    {
        SAXReader reader = new SAXReader();
        Document document;
        try
        {
            document = reader.read( buildOutputFileName() );
        }
        catch ( DocumentException de )
        {
            de.printStackTrace();
            throw new MavenReportException( "Error while loading javancss raw generated report.", de );
        }
        return document;
    }

    /**
     * Build a path for the output filename.
     * 
     * @return A String representation of the output filename.
     */
    /* package */String buildOutputFileName()
    {
        return getOutputDirectory() + File.separator + tempFileName;
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getName(java.util.Locale)
     */
    public String getName( Locale locale )
    {
        return getBundle( locale ).getString( "report.javancss.name" );
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getDescription(java.util.Locale)
     */
    public String getDescription( Locale locale )
    {
        return getBundle( locale ).getString( "report.javancss.description" );
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getOutputDirectory()
     */
    protected String getOutputDirectory()
    {
        return outputDirectory.getAbsolutePath();
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getProject()
     */
    protected MavenProject getProject()
    {
        return project;
    }

    /**
     * @see org.apache.maven.reporting.AbstractMavenReport#getSiteRenderer()
     */
    protected SiteRenderer getSiteRenderer()
    {
        return siteRenderer;
    }

    /**
     * @see org.apache.maven.reporting.MavenReport#getOutputName()
     */
    public String getOutputName()
    {
        return "javancss";
    }

    /**
     *  Getter for the source directory
     *  
     *  @return the source directory as a File object.
     */
    protected File getSourceDirectory()
    {
        return sourceDirectory;
    }

    // helper to retrive the right bundle
    private static ResourceBundle getBundle( Locale locale )
    {
        return ResourceBundle.getBundle( "javancss-report", locale, NcssReportMojo.class.getClassLoader() );
    }
}

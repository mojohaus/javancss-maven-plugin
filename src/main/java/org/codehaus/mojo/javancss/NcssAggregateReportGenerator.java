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

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.doxia.sink.Sink;
import org.dom4j.Document;
import org.dom4j.Node;

/**
 * Generates the JavaNCSS aggregate report.
 * 
 * @author <a href="mperham AT gmail.com">Mike Perham</a>
 */
public class NcssAggregateReportGenerator extends AbstractNcssReportGenerator
{

    /**
     * @param sink
     *            the sink which will be used for reporting.
     * @param bundle
     *            the correct RessourceBundle to be used for reporting.
     * @param log
     */
    public NcssAggregateReportGenerator( Sink sink, ResourceBundle bundle, Log log )
    {
        super( sink, bundle, log );
    }

    /**
     * Generates the JavaNCSS report.
     * 
     * @param locale
     *            the Locale used for this report.
     * @param moduleReports
     *            the javancss raw reports to aggregate, List of ModuleReport.
     * @param lineThreshold
     *            the maximum number of lines to keep in major reports.
     */
    public void doReport( Locale locale, List moduleReports, int lineThreshold )
    {
        // HEADER
        sink.head();
        sink.title();
        sink.text( bundle.getString( "report.javancss.title" ) );
        sink.title_();
        sink.head_();
        // BODY
        sink.body();
        doIntro();
        // packages
        startSection( "report.javancss.module.link", "report.javancss.module.title" );
        doModuleAnalysis( moduleReports );
        endSection();
        sink.body_();
        sink.close();
    }

    private void doIntro()
    {
        sink.section1();
        sink.sectionTitle1();
        sink.text( bundle.getString( "report.javancss.main.title" ) );
        sink.sectionTitle1_();
        sink.paragraph();
        sink.text( bundle.getString( "report.javancss.main.text" ) + " " );
        sink.lineBreak();
        sink.link( "http://www.kclee.de/clemens/java/javancss/" );
        sink.text( "JavaNCSS web site." );
        sink.link_();
        sink.paragraph_();
        sink.section1_();
    }

    private void doModuleAnalysis( List reports )
    {
        sink.table();
        sink.tableRow();
        headerCellHelper( bundle.getString( "report.javancss.header.module" ) );
        headerCellHelper( bundle.getString( "report.javancss.header.packages" ) );
        headerCellHelper( bundle.getString( "report.javancss.header.classetotal" ) );
        headerCellHelper( bundle.getString( "report.javancss.header.functiontotal" ) );
        headerCellHelper( bundle.getString( "report.javancss.header.ncsstotal" ) );
        headerCellHelper( bundle.getString( "report.javancss.header.javadoc" ) );
        headerCellHelper( bundle.getString( "report.javancss.header.javadoc_line" ) );
        headerCellHelper( bundle.getString( "report.javancss.header.single_comment" ) );
        headerCellHelper( bundle.getString( "report.javancss.header.multi_comment" ) );
        sink.tableRow_();

        int packages = 0;
        int classes = 0;
        int methods = 0;
        int ncss = 0;
        int javadocs = 0;
        int jdlines = 0;
        int single = 0;
        int multi = 0;
        for ( Iterator it = reports.iterator(); it.hasNext(); )
        {
            ModuleReport report = (ModuleReport) it.next();
            Document document = report.getJavancssDocument();
            sink.tableRow();
            log.debug( "Aggregating " + report.getModule().getArtifactId() );
            tableCellHelper( report.getModule().getArtifactId() );
            int packageSize = document.selectNodes( "//javancss/packages/package" ).size();
            packages += packageSize;
            tableCellHelper( String.valueOf( packageSize ) );

            Node node = document.selectSingleNode( "//javancss/packages/total" );

            String classSize = node.valueOf( "classes" );
            tableCellHelper( classSize );
            classes += Integer.parseInt( classSize );

            String methodSize = node.valueOf( "functions" );
            tableCellHelper( methodSize );
            methods += Integer.parseInt( methodSize );

            String ncssSize = node.valueOf( "ncss" );
            tableCellHelper( ncssSize );
            ncss += Integer.parseInt( ncssSize );

            String javadocSize = node.valueOf( "javadocs" );
            tableCellHelper( javadocSize );
            javadocs += Integer.parseInt( javadocSize );

            String jdlineSize = node.valueOf( "javadoc_lines" );
            tableCellHelper( jdlineSize );
            jdlines += Integer.parseInt( jdlineSize );

            String singleSize = node.valueOf( "single_comment_lines" );
            tableCellHelper( singleSize );
            single += Integer.parseInt( singleSize );

            String multiSize = node.valueOf( "multi_comment_lines" );
            tableCellHelper( multiSize );
            multi += Integer.parseInt( multiSize );

            sink.tableRow_();
        }

        // Totals row
        sink.tableRow();
        tableCellHelper( bundle.getString( "report.javancss.header.totals" ) );
        tableCellHelper( String.valueOf( packages ) );
        tableCellHelper( String.valueOf( classes ) );
        tableCellHelper( String.valueOf( methods ) );
        tableCellHelper( String.valueOf( ncss ) );
        tableCellHelper( String.valueOf( javadocs ) );
        tableCellHelper( String.valueOf( jdlines ) );
        tableCellHelper( String.valueOf( single ) );
        tableCellHelper( String.valueOf( multi ) );
        sink.tableRow_();

        sink.table_();
    }

}

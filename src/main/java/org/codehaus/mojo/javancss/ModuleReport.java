package org.codehaus.mojo.javancss;

import org.apache.maven.project.MavenProject;
import org.dom4j.Document;

/**
 * A bean which holds a child project and its associated JavaNCSS raw report.
 * 
 * @author mperham
 * @version $Id: $
 */
public class ModuleReport
{
    private MavenProject module;
    private Document report;
    
    public ModuleReport( MavenProject project, Document document )
    {
        module = project;
        report = document;
    }
    public Document getJavancssDocument()
    {
        return report;
    }
    public void setReport( Document javancssDocument )
    {
        this.report = javancssDocument;
    }
    public MavenProject getModule()
    {
        return module;
    }
    public void setModule( MavenProject module )
    {
        this.module = module;
    }
}

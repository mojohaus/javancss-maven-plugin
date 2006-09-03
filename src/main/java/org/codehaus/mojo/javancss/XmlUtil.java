package org.codehaus.mojo.javancss;

import java.io.File;

import org.apache.maven.reporting.MavenReportException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

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

/**
 * Utiliy class holding code used by all mojos.
 * 
 * @author <a href="jeanlaurentATgmail.com">Jean-Laurent de Morlhon</a>
 */

public class XmlUtil
{
    // FIXME: MavenReportException is really the right one to use here ?
    public static Document loadDocument( File file ) throws MavenReportException
    {
        try
        {
            return new SAXReader().read( file );
        }
        catch ( DocumentException de )
        {
            throw new MavenReportException( "Error while loading javancss raw generated report.", de );
        }
    }

}

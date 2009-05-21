package hello;

import java.util.Vector;

public class Hello
{
    public String hello( String name )
    {
        @SuppressWarnings("unused")
        int notUsedVariable = 2;

        Enumeration enum = new Vector().elements();

        final String myName = name == null ? "world" : name;
        return "Hello " + myName + "!";
    }
}

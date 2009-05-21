package hello;

public class Hello
{
    public String hello( String name )
    {
        @SuppressWarnings("unused")
        int notUsedVariable = 2;

        final String myName = name == null ? "world" : name;
        return "Hello " + myName + "!";
    }
}

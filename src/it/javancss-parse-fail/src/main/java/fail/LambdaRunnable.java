package fail;

/**
 * As of javancss-maven-plugin 2.1/JavaNCSS 33.54, this Java 8 new Lambda syntax is not supported.
 * lambda code sample taken from http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html
 */
public class RunnableTest
{
  public static void main( String[] args )
  {

    System.out.println( "=== RunnableTest ===" );

    // Anonymous Runnable
    Runnable r1 = new Runnable()
    {
      @Override
      public void run()
      {
        System.out.println( "Hello world one!" );
      }
    };

    // Lambda Runnable
    Runnable r2 = () -> System.out.println( "Hello world two!" );

    // Run em!
    r1.run();
    r2.run();

  }
}
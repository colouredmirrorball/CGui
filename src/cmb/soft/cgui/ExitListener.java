package cmb.soft.cgui;

/**
 * Functional interface that can be registered to the CGUI main class. Called right before the application is exiting,
 * allowing resources to be closed gracefully.
 */

public interface ExitListener
{
    void stop();
}

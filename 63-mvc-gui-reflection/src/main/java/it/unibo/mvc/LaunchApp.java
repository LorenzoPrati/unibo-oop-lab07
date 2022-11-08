package it.unibo.mvc;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;
import it.unibo.mvc.view.DrawNumberStdoutView;
import it.unibo.mvc.view.DrawNumberSwingView;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() {
    }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException    if the fetches class does not exist
     * @throws NoSuchMethodException     if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException    if the constructor throws exceptions
     * @throws IllegalAccessException    in case of reflection issues
     * @throws IllegalArgumentException  in case of reflection issues
     */
    public static void main(final String... args)
        throws
        ClassNotFoundException,
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException,
        IllegalArgumentException {
            final var model = new DrawNumberImpl();
            final DrawNumberController app = new DrawNumberControllerImpl(model);
            /*app.addView(new DrawNumberSwingView());
            app.addView(new DrawNumberStdoutView());*/
            final var swingViewName = "it.unibo.mvc.view.DrawNumberSwingView";
            final var stdoutViewName = "it.unibo.mvc.view.DrawNumberStdoutView";
            final var viewNames = List.of(swingViewName, stdoutViewName);
            for (final var viewName : viewNames) {
                final var viewClass = Class.forName(viewName);
                final var cst = viewClass.getConstructor();
                for (int i = 0; i < 3; i++) {
                    app.addView((DrawNumberView) cst.newInstance());
                }
            }
    }
}

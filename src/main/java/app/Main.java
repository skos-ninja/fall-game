package app;

import app.engine.Engine;
import app.render.Display;

public class Main {
    private static Display display;
    private static Engine engine;

    public static void main(String[] args) {
        engine = new Engine();
        engine.init();

        display = new Display(900, 500, "Fall game");

        display.setFrameRenderer((window) -> {
            // Frame render called
            System.out.println("Frame render called");

            engine.render(window);
        });

        display.setKeyboardHandler((key, scancode, action, mods) -> {
            // Keyboard input detected
            System.out.println("Keyboard press called: " + key);

            engine.inputHandler(key, scancode, action, mods);
        });

        display.init();
        display.render();
    }

}
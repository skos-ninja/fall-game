package app;

import app.engine.Engine;
import app.render.Display;

public class Main {
    private static Display display;
    private static Engine engine;

    private static final int width = 500;
    private static final int height = 900;

    public static void main(String[] args) {
        engine = new Engine();
        engine.init(width, height);

        display = new Display(width, height, "Don't hit the block");

        display.setFrameRenderer((window) -> {
            engine.tick();

            engine.render(window);
        });

        display.setKeyboardHandler((key, scancode, action, mods) -> {
            engine.inputHandler(key, action);
        });

        display.init();

        // Display render holds until the game loop is finished
        display.render();

        display.destroy();
    }

}
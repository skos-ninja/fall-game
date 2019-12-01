package app;

import app.engine.Engine;
import app.render.Display;

public class Main {
    private static Display display;
    private static Engine engine;

    private static final int width = 500;
    private static final int height = 900;

    private static int score = 0;

    public static void main(String[] args) {
        engine = new Engine();
        engine.init(width, height);

        display = new Display(width, height, "Don't hit the block");

        display.setFrameRenderer((window) -> {
            engine.render(window);


            if (engine.detectCollision()) {
                display.setShouldRender(false);
            } else {
                // Increase our final score
                score++;
            }
        });

        display.setKeyboardHandler((key, scancode, action, mods) -> {
            engine.inputHandler(key, action);
        });

        display.init();

        // Display render holds until the game loop is finished
        display.render();

        display.destroy();

        System.out.println("Final score: " + score);
    }

}
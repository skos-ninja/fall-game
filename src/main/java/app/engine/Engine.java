package app.engine;

import java.util.List;

public class Engine {
    private List<Entity> entities;

    public void tick() {
        // Run our entity ticker here
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void init() {
    }

    public void render(long window) {
        // Run a tick loop now
        // TODO: cap this ticker to now allow duplicate frames
        this.tick();

        // TODO: render visuals here
    }

    public void inputHandler(int key, int scancode, int action, int mods) {
        // TODO: handle keyboard input
    }
}
